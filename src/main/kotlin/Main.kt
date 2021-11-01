import cmd.HTMLToEpubException
import org.apache.commons.cli.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val opts = Options()
    val author = Option("author", "author", true, "set epub author").also { opts.addOption(it) }
    val name = Option("name", "name", true, "set epub name").also { opts.addOption(it) }
    val cover = Option("cover", "cover", true, "set epub cover").also { opts.addOption(it) }
    val output = Option("o", "output", true, "output file").also { opts.addOption(it) }

    try {
        val parsed = DefaultParser().parse(opts, args)

        cmd.HTMLToEpub(
            cover = cover.getValue(""),
            author = author.getValue("HTML to Epub"),
            title = name.getValue("HTML"),
            output = output.getValue("output.epub")
        ).run(parsed.argList)

        exitProcess(0)
    } catch (e: ParseException) {
        println(e.message)
        HelpFormatter().printHelp("html-to-epub", opts)
    } catch (e: HTMLToEpubException) {
        println(e.message)
        HelpFormatter().printHelp("html-to-epub", opts)
    }
}
