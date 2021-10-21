package cmd

import coza.opencollab.epub.creator.model.Content
import coza.opencollab.epub.creator.model.EpubBook
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import net.sf.jmimemagic.Magic
import okhttp3.*
import org.apache.tika.mime.MimeTypes
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Entities
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest
import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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
    private val client = OkHttpClient.Builder().apply {
        callTimeout(Duration.of(3, ChronoUnit.MINUTES))
        System.getenv("http_proxy")?.also { p ->
            URI(p).also {
                val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(it.host, it.port))
                println("using $proxy")
                this.proxy(proxy)
            }
        }
    }.build()
    private val header =
        Headers.headersOf(
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

        if (cover.isBlank()) defaultCover else FileInputStream(cover).use {
            epub.addCoverImage(it.readAllBytes(), "image/jpeg", "cover.png")
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
                    val match = Magic.getMagicMatch(File(path), false)
                    val extension = MimeTypes.getDefaultMimeTypes().forName(match.mimeType).extension

                    FileInputStream(path).use {
                        val name = Path.of(path).fileName.toString() + extension
                        val content = epub.addContent(it, match.mimeType, name, false, false)
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
        val semaphore = Semaphore(3)
        val saves = mutableMapOf<String, String>()

        runBlocking {
            list.forEach { url ->
                semaphore.withPermit {
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
            println("download $url with ${Thread.currentThread().name} start")

            val req = Request.Builder().url(url).headers(header).build()
            val resp = suspendCoroutine<Response> { ct ->
                client.newCall(req).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        ct.resumeWithException(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        ct.resume(response)
                    }
                })
            }

            File(media).mkdirs()
            val file = Paths.get(media, md5(url).toHex()).toFile()

            resp.body?.byteStream().use { i ->
                file.outputStream().use { o ->
                    i?.copyTo(o)
                }
            }

            println("download $url with ${Thread.currentThread().name} done")

            return file.absolutePath
        } catch (e: Exception) {
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
