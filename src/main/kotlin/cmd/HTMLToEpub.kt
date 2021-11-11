package cmd

import MimeTypes.ext
import MimeTypes.getMimeType
import coza.opencollab.epub.creator.model.Content
import coza.opencollab.epub.creator.model.EpubBook
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Entities
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.InetSocketAddress
import java.net.ProxySelector
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.time.Duration
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.io.path.extension

class HTMLToEpubException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}
class HTMLToEpub(
    val cover: String,
    val author: String,
    val title: String,
    val output: String
) {
    private val defaultCover = this.javaClass.classLoader.getResourceAsStream("cover.png")
    private val client = HttpClient.newBuilder().apply {
        connectTimeout(Duration.ofSeconds(10))
        System.getenv("http_proxy")?.also { p ->
            URI(p).also { u ->
                proxy(ProxySelector.of(InetSocketAddress(u.host, u.port)))
                println("using proxy $p")
            }
        }
    }.build()
    private val header = listOf(
        "user-agent",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:93.0) Gecko/20100101 Firefox/93.0"
    )
    private val resources = mutableMapOf<String, Content>()
    private val epub = EpubBook()
    private val media = "./media"

    fun run(args: List<String>) {
        var htmlList = args.toList()

        if (htmlList.isEmpty() || htmlList.contains("*.html")) {
            htmlList = glob("*.html")
        }

        if (htmlList.isEmpty()) {
            throw HTMLToEpubException("no .html given")
        }

        epub.id = "epub"
        epub.author = author
        epub.title = title

        val image = if (cover.isNotBlank()) FileInputStream(cover) else defaultCover
        image.use {
            epub.addCoverImage(it.readBytes(), "image/jpeg", "cover.png")
        }

        htmlList.forEach {
            appendHTML(it)
        }

        FileOutputStream(File(output)).use { file ->
            epub.writeToStream(file)
        }
    }

    private fun appendHTML(html: String) {
        val doc = Jsoup.parse(File(html), Charsets.UTF_8.toString()).also { cleanDoc(it) }.apply {
            outputSettings().syntax(Document.OutputSettings.Syntax.xml)
            outputSettings().escapeMode(Entities.EscapeMode.xhtml)
        }
        val refs =
            doc.select("img")
                .map { it.attr("src") }
                .filter { it.startsWith("http://") || it.startsWith("https://") }
                .toSet()

        if (refs.isNotEmpty()) {
            val saves = download(refs)

            doc.select("img").forEach { img ->
                val src = img.attr("src")

                if (src.isBlank()) {
                    return
                }
                if (saves.containsKey(src) && !resources.containsKey(src)) {
                    val path = saves[src]!!
                    val mime = getMimeType(File(path).extension)
                    FileInputStream(path).use {
                        val name = Path.of(path).fileName.toString() + mime.ext()
                        val content = epub.addContent(it, mime, name, false, false)
                        resources[src] = content
                    }
                }
                if (resources.containsKey(src)) {
                    val rs = resources[src]!!
                    img.attr("src", rs.href)
                }
            }
        }

        doc.select("html").apply {
            attr("xmlns", "http://www.w3.org/1999/xhtml")
            attr("xmlns:epub", "http://www.idpf.org/2007/ops")
        }

        val xhtml = """
            <?xml version="1.0" encoding="utf-8"?>
            <!DOCTYPE html>
        """.trimIndent() + doc.outerHtml()

        epub.addContent(xhtml.byteInputStream(), "application/xhtml+xml", "doc.xhtml", true, true)
    }

    private fun cleanDoc(doc: Document) {
        // remove inoreader ads
        doc.select("body").select("""div:contains("ads from inoreader")""").parents().select("center").firstOrNull()
            ?.remove()
        // remove solidot.org ads
        doc.select("img[src='https://img.solidot.org//0/446/liiLIZF8Uh6yM.jpg']").remove()
    }

    private fun download(list: Set<String>): Map<String, String> {
        val semap = Semaphore(3)
        val saves = mutableMapOf<String, String>()

        runBlocking {
            list.forEach { url ->
                semap.withPermit {
                    launch {
                        downloadOne(url)?.also { saves[url] = it }
                    }
                }
            }
        }

        return saves
    }

    private suspend fun downloadOne(url: String): String? {
        try {
            println("download $url with Thread#${Thread.currentThread().name} start")

            val req = HttpRequest.newBuilder().GET().apply {
                uri(URI.create(url))
                headers(*header.toTypedArray())
                timeout(Duration.ofSeconds(120))
            }.build()

            val resp = suspendCoroutine<HttpResponse<InputStream>> { c ->
                val f = client.sendAsync(req, HttpResponse.BodyHandlers.ofInputStream())
                f.whenCompleteAsync { resp, exp ->
                    if (exp != null) {
                        c.resumeWithException(exp)
                    } else {
                        c.resume(resp)
                    }
                };
            }

            val mime = resp.headers().firstValue("content-type")
            val f = let {
                if (mime.isEmpty) {
                    val name = md5(url).toHex() + "." + Path.of(url).extension
                    Path.of(media, name).toFile()
                } else {
                    val ext = getMimeType(mime.get()).ext()
                    val name = md5(url).toHex() + ext
                    Path.of(media, mime.get(), name).toFile()
                }
            }
            f.parentFile.mkdirs()

            resp.body().use { i ->
                f.outputStream().use { o ->
                    i.copyTo(o)
                }
            }

            println("download $url with Thread#${Thread.currentThread().name} done")

            return f.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            println("download $url failed: ${e.message}")

            return null
        }
    }
}

fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))
fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
fun glob(glob: String): List<String> {
    val matcher = FileSystems.getDefault().getPathMatcher("glob:$glob")
    return Files.walk(Path.of(".")).filter { matcher.matches(it.fileName) }.map { it.toString() }.toList()
}
