import java.util.*
import kotlin.collections.HashMap

/**
 * Map file extensions to MIME types. Based on the Apache mime.types file.
 * http://www.iana.org/assignments/media-types/
 */

typealias MIME = String

object MimeTypes {
    const val MIME_APPLICATION_ANDREW_INSET: MIME = "application/andrew-inset"
    const val MIME_APPLICATION_JSON: MIME = "application/json"
    const val MIME_APPLICATION_ZIP: MIME = "application/zip"
    const val MIME_APPLICATION_X_GZIP: MIME = "application/x-gzip"
    const val MIME_APPLICATION_TGZ: MIME = "application/tgz"
    const val MIME_APPLICATION_MSWORD: MIME = "application/msword"
    const val MIME_APPLICATION_POSTSCRIPT: MIME = "application/postscript"
    const val MIME_APPLICATION_PDF: MIME = "application/pdf"
    const val MIME_APPLICATION_JNLP: MIME = "application/jnlp"
    const val MIME_APPLICATION_MAC_BINHEX40: MIME = "application/mac-binhex40"
    const val MIME_APPLICATION_MAC_COMPACTPRO: MIME = "application/mac-compactpro"
    const val MIME_APPLICATION_MATHML_XML: MIME = "application/mathml+xml"
    const val MIME_APPLICATION_OCTET_STREAM: MIME = "application/octet-stream"
    const val MIME_APPLICATION_ODA: MIME = "application/oda"
    const val MIME_APPLICATION_RDF_XML: MIME = "application/rdf+xml"
    const val MIME_APPLICATION_JAVA_ARCHIVE: MIME = "application/java-archive"
    const val MIME_APPLICATION_RDF_SMIL: MIME = "application/smil"
    const val MIME_APPLICATION_SRGS: MIME = "application/srgs"
    const val MIME_APPLICATION_SRGS_XML: MIME = "application/srgs+xml"
    const val MIME_APPLICATION_VND_MIF: MIME = "application/vnd.mif"
    const val MIME_APPLICATION_VND_MSEXCEL: MIME = "application/vnd.ms-excel"
    const val MIME_APPLICATION_VND_MSPOWERPOINT: MIME = "application/vnd.ms-powerpoint"
    const val MIME_APPLICATION_VND_RNREALMEDIA: MIME = "application/vnd.rn-realmedia"
    const val MIME_APPLICATION_X_BCPIO: MIME = "application/x-bcpio"
    const val MIME_APPLICATION_X_CDLINK: MIME = "application/x-cdlink"
    const val MIME_APPLICATION_X_CHESS_PGN: MIME = "application/x-chess-pgn"
    const val MIME_APPLICATION_X_CPIO: MIME = "application/x-cpio"
    const val MIME_APPLICATION_X_CSH: MIME = "application/x-csh"
    const val MIME_APPLICATION_X_DIRECTOR: MIME = "application/x-director"
    const val MIME_APPLICATION_X_DVI: MIME = "application/x-dvi"
    const val MIME_APPLICATION_X_FUTURESPLASH: MIME = "application/x-futuresplash"
    const val MIME_APPLICATION_X_GTAR: MIME = "application/x-gtar"
    const val MIME_APPLICATION_X_HDF: MIME = "application/x-hdf"
    const val MIME_APPLICATION_X_JAVASCRIPT: MIME = "application/x-javascript"
    const val MIME_APPLICATION_X_KOAN: MIME = "application/x-koan"
    const val MIME_APPLICATION_X_LATEX: MIME = "application/x-latex"
    const val MIME_APPLICATION_X_NETCDF: MIME = "application/x-netcdf"
    const val MIME_APPLICATION_X_OGG: MIME = "application/x-ogg"
    const val MIME_APPLICATION_X_SH: MIME = "application/x-sh"
    const val MIME_APPLICATION_X_SHAR: MIME = "application/x-shar"
    const val MIME_APPLICATION_X_SHOCKWAVE_FLASH: MIME = "application/x-shockwave-flash"
    const val MIME_APPLICATION_X_STUFFIT: MIME = "application/x-stuffit"
    const val MIME_APPLICATION_X_SV4CPIO: MIME = "application/x-sv4cpio"
    const val MIME_APPLICATION_X_SV4CRC: MIME = "application/x-sv4crc"
    const val MIME_APPLICATION_X_TAR: MIME = "application/x-tar"
    const val MIME_APPLICATION_X_RAR_COMPRESSED: MIME = "application/x-rar-compressed"
    const val MIME_APPLICATION_X_TCL: MIME = "application/x-tcl"
    const val MIME_APPLICATION_X_TEX: MIME = "application/x-tex"
    const val MIME_APPLICATION_X_TEXINFO: MIME = "application/x-texinfo"
    const val MIME_APPLICATION_X_TROFF: MIME = "application/x-troff"
    const val MIME_APPLICATION_X_TROFF_MAN: MIME = "application/x-troff-man"
    const val MIME_APPLICATION_X_TROFF_ME: MIME = "application/x-troff-me"
    const val MIME_APPLICATION_X_TROFF_MS: MIME = "application/x-troff-ms"
    const val MIME_APPLICATION_X_USTAR: MIME = "application/x-ustar"
    const val MIME_APPLICATION_X_WAIS_SOURCE: MIME = "application/x-wais-source"
    const val MIME_APPLICATION_VND_MOZZILLA_XUL_XML: MIME = "application/vnd.mozilla.xul+xml"
    const val MIME_APPLICATION_XHTML_XML: MIME = "application/xhtml+xml"
    const val MIME_APPLICATION_XSLT_XML: MIME = "application/xslt+xml"
    const val MIME_APPLICATION_XML: MIME = "application/xml"
    const val MIME_APPLICATION_XML_DTD: MIME = "application/xml-dtd"
    const val MIME_IMAGE_BMP: MIME = "image/bmp"
    const val MIME_IMAGE_CGM: MIME = "image/cgm"
    const val MIME_IMAGE_GIF: MIME = "image/gif"
    const val MIME_IMAGE_IEF: MIME = "image/ief"
    const val MIME_IMAGE_JPEG: MIME = "image/jpeg"
    const val MIME_IMAGE_TIFF: MIME = "image/tiff"
    const val MIME_IMAGE_PNG: MIME = "image/png"
    const val MIME_IMAGE_SVG_XML: MIME = "image/svg+xml"
    const val MIME_IMAGE_VND_DJVU: MIME = "image/vnd.djvu"
    const val MIME_IMAGE_WAP_WBMP: MIME = "image/vnd.wap.wbmp"
    const val MIME_IMAGE_X_CMU_RASTER: MIME = "image/x-cmu-raster"
    const val MIME_IMAGE_X_ICON: MIME = "image/x-icon"
    const val MIME_IMAGE_X_PORTABLE_ANYMAP: MIME = "image/x-portable-anymap"
    const val MIME_IMAGE_X_PORTABLE_BITMAP: MIME = "image/x-portable-bitmap"
    const val MIME_IMAGE_X_PORTABLE_GRAYMAP: MIME = "image/x-portable-graymap"
    const val MIME_IMAGE_X_PORTABLE_PIXMAP: MIME = "image/x-portable-pixmap"
    const val MIME_IMAGE_X_RGB: MIME = "image/x-rgb"
    const val MIME_AUDIO_BASIC: MIME = "audio/basic"
    const val MIME_AUDIO_MIDI: MIME = "audio/midi"
    const val MIME_AUDIO_MPEG: MIME = "audio/mpeg"
    const val MIME_AUDIO_X_AIFF: MIME = "audio/x-aiff"
    const val MIME_AUDIO_X_MPEGURL: MIME = "audio/x-mpegurl"
    const val MIME_AUDIO_X_PN_REALAUDIO: MIME = "audio/x-pn-realaudio"
    const val MIME_AUDIO_X_WAV: MIME = "audio/x-wav"
    const val MIME_CHEMICAL_X_PDB: MIME = "chemical/x-pdb"
    const val MIME_CHEMICAL_X_XYZ: MIME = "chemical/x-xyz"
    const val MIME_MODEL_IGES: MIME = "model/iges"
    const val MIME_MODEL_MESH: MIME = "model/mesh"
    const val MIME_MODEL_VRLM: MIME = "model/vrml"
    const val MIME_TEXT_PLAIN: MIME = "text/plain"
    const val MIME_TEXT_RICHTEXT: MIME = "text/richtext"
    const val MIME_TEXT_RTF: MIME = "text/rtf"
    const val MIME_TEXT_HTML: MIME = "text/html"
    const val MIME_TEXT_CALENDAR: MIME = "text/calendar"
    const val MIME_TEXT_CSS: MIME = "text/css"
    const val MIME_TEXT_SGML: MIME = "text/sgml"
    const val MIME_TEXT_TAB_SEPARATED_VALUES: MIME = "text/tab-separated-values"
    const val MIME_TEXT_VND_WAP_XML: MIME = "text/vnd.wap.wml"
    const val MIME_TEXT_VND_WAP_WMLSCRIPT: MIME = "text/vnd.wap.wmlscript"
    const val MIME_TEXT_X_SETEXT: MIME = "text/x-setext"
    const val MIME_TEXT_X_COMPONENT: MIME = "text/x-component"
    const val MIME_VIDEO_QUICKTIME: MIME = "video/quicktime"
    const val MIME_VIDEO_MPEG: MIME = "video/mpeg"
    const val MIME_VIDEO_VND_MPEGURL: MIME = "video/vnd.mpegurl"
    const val MIME_VIDEO_X_MSVIDEO: MIME = "video/x-msvideo"
    const val MIME_VIDEO_X_MS_WMV: MIME = "video/x-ms-wmv"
    const val MIME_VIDEO_X_SGI_MOVIE: MIME = "video/x-sgi-movie"
    const val MIME_X_CONFERENCE_X_COOLTALK: MIME = "x-conference/x-cooltalk"
    private var mimeTypeMapping = mutableMapOf(
        "xul" to MIME_APPLICATION_VND_MOZZILLA_XUL_XML,
        "json" to MIME_APPLICATION_JSON,
        "ice" to MIME_X_CONFERENCE_X_COOLTALK,
        "movie" to MIME_VIDEO_X_SGI_MOVIE,
        "avi" to MIME_VIDEO_X_MSVIDEO,
        "wmv" to MIME_VIDEO_X_MS_WMV,
        "m4u" to MIME_VIDEO_VND_MPEGURL,
        "mxu" to MIME_VIDEO_VND_MPEGURL,
        "htc" to MIME_TEXT_X_COMPONENT,
        "etx" to MIME_TEXT_X_SETEXT,
        "wmls" to MIME_TEXT_VND_WAP_WMLSCRIPT,
        "wml" to MIME_TEXT_VND_WAP_XML,
        "tsv" to MIME_TEXT_TAB_SEPARATED_VALUES,
        "sgm" to MIME_TEXT_SGML,
        "sgml" to MIME_TEXT_SGML,
        "css" to MIME_TEXT_CSS,
        "ifb" to MIME_TEXT_CALENDAR,
        "ics" to MIME_TEXT_CALENDAR,
        "wrl" to MIME_MODEL_VRLM,
        "vrlm" to MIME_MODEL_VRLM,
        "silo" to MIME_MODEL_MESH,
        "mesh" to MIME_MODEL_MESH,
        "msh" to MIME_MODEL_MESH,
        "iges" to MIME_MODEL_IGES,
        "igs" to MIME_MODEL_IGES,
        "rgb" to MIME_IMAGE_X_RGB,
        "ppm" to MIME_IMAGE_X_PORTABLE_PIXMAP,
        "pgm" to MIME_IMAGE_X_PORTABLE_GRAYMAP,
        "pbm" to MIME_IMAGE_X_PORTABLE_BITMAP,
        "pnm" to MIME_IMAGE_X_PORTABLE_ANYMAP,
        "ico" to MIME_IMAGE_X_ICON,
        "ras" to MIME_IMAGE_X_CMU_RASTER,
        "wbmp" to MIME_IMAGE_WAP_WBMP,
        "djv" to MIME_IMAGE_VND_DJVU,
        "djvu" to MIME_IMAGE_VND_DJVU,
        "svg" to MIME_IMAGE_SVG_XML,
        "ief" to MIME_IMAGE_IEF,
        "cgm" to MIME_IMAGE_CGM,
        "bmp" to MIME_IMAGE_BMP,
        "xyz" to MIME_CHEMICAL_X_XYZ,
        "pdb" to MIME_CHEMICAL_X_PDB,
        "ra" to MIME_AUDIO_X_PN_REALAUDIO,
        "ram" to MIME_AUDIO_X_PN_REALAUDIO,
        "m3u" to MIME_AUDIO_X_MPEGURL,
        "aifc" to MIME_AUDIO_X_AIFF,
        "aif" to MIME_AUDIO_X_AIFF,
        "aiff" to MIME_AUDIO_X_AIFF,
        "mp3" to MIME_AUDIO_MPEG,
        "mp2" to MIME_AUDIO_MPEG,
        "mp1" to MIME_AUDIO_MPEG,
        "mpga" to MIME_AUDIO_MPEG,
        "kar" to MIME_AUDIO_MIDI,
        "mid" to MIME_AUDIO_MIDI,
        "midi" to MIME_AUDIO_MIDI,
        "dtd" to MIME_APPLICATION_XML_DTD,
        "xsl" to MIME_APPLICATION_XML,
        "xml" to MIME_APPLICATION_XML,
        "xslt" to MIME_APPLICATION_XSLT_XML,
        "xht" to MIME_APPLICATION_XHTML_XML,
        "xhtml" to MIME_APPLICATION_XHTML_XML,
        "src" to MIME_APPLICATION_X_WAIS_SOURCE,
        "ustar" to MIME_APPLICATION_X_USTAR,
        "ms" to MIME_APPLICATION_X_TROFF_MS,
        "me" to MIME_APPLICATION_X_TROFF_ME,
        "man" to MIME_APPLICATION_X_TROFF_MAN,
        "roff" to MIME_APPLICATION_X_TROFF,
        "tr" to MIME_APPLICATION_X_TROFF,
        "t" to MIME_APPLICATION_X_TROFF,
        "texi" to MIME_APPLICATION_X_TEXINFO,
        "texinfo" to MIME_APPLICATION_X_TEXINFO,
        "tex" to MIME_APPLICATION_X_TEX,
        "tcl" to MIME_APPLICATION_X_TCL,
        "sv4crc" to MIME_APPLICATION_X_SV4CRC,
        "sv4cpio" to MIME_APPLICATION_X_SV4CPIO,
        "sit" to MIME_APPLICATION_X_STUFFIT,
        "swf" to MIME_APPLICATION_X_SHOCKWAVE_FLASH,
        "shar" to MIME_APPLICATION_X_SHAR,
        "sh" to MIME_APPLICATION_X_SH,
        "cdf" to MIME_APPLICATION_X_NETCDF,
        "nc" to MIME_APPLICATION_X_NETCDF,
        "latex" to MIME_APPLICATION_X_LATEX,
        "skm" to MIME_APPLICATION_X_KOAN,
        "skt" to MIME_APPLICATION_X_KOAN,
        "skd" to MIME_APPLICATION_X_KOAN,
        "skp" to MIME_APPLICATION_X_KOAN,
        "js" to MIME_APPLICATION_X_JAVASCRIPT,
        "hdf" to MIME_APPLICATION_X_HDF,
        "gtar" to MIME_APPLICATION_X_GTAR,
        "spl" to MIME_APPLICATION_X_FUTURESPLASH,
        "dvi" to MIME_APPLICATION_X_DVI,
        "dxr" to MIME_APPLICATION_X_DIRECTOR,
        "dir" to MIME_APPLICATION_X_DIRECTOR,
        "dcr" to MIME_APPLICATION_X_DIRECTOR,
        "csh" to MIME_APPLICATION_X_CSH,
        "cpio" to MIME_APPLICATION_X_CPIO,
        "pgn" to MIME_APPLICATION_X_CHESS_PGN,
        "vcd" to MIME_APPLICATION_X_CDLINK,
        "bcpio" to MIME_APPLICATION_X_BCPIO,
        "rm" to MIME_APPLICATION_VND_RNREALMEDIA,
        "ppt" to MIME_APPLICATION_VND_MSPOWERPOINT,
        "mif" to MIME_APPLICATION_VND_MIF,
        "grxml" to MIME_APPLICATION_SRGS_XML,
        "gram" to MIME_APPLICATION_SRGS,
        "smil" to MIME_APPLICATION_RDF_SMIL,
        "smi" to MIME_APPLICATION_RDF_SMIL,
        "rdf" to MIME_APPLICATION_RDF_XML,
        "ogg" to MIME_APPLICATION_X_OGG,
        "oda" to MIME_APPLICATION_ODA,
        "dmg" to MIME_APPLICATION_OCTET_STREAM,
        "lzh" to MIME_APPLICATION_OCTET_STREAM,
        "so" to MIME_APPLICATION_OCTET_STREAM,
        "lha" to MIME_APPLICATION_OCTET_STREAM,
        "dms" to MIME_APPLICATION_OCTET_STREAM,
        "bin" to MIME_APPLICATION_OCTET_STREAM,
        "mathml" to MIME_APPLICATION_MATHML_XML,
        "cpt" to MIME_APPLICATION_MAC_COMPACTPRO,
        "hqx" to MIME_APPLICATION_MAC_BINHEX40,
        "jnlp" to MIME_APPLICATION_JNLP,
        "ez" to MIME_APPLICATION_ANDREW_INSET,
        "txt" to MIME_TEXT_PLAIN,
        "ini" to MIME_TEXT_PLAIN,
        "c" to MIME_TEXT_PLAIN,
        "h" to MIME_TEXT_PLAIN,
        "cpp" to MIME_TEXT_PLAIN,
        "cxx" to MIME_TEXT_PLAIN,
        "cc" to MIME_TEXT_PLAIN,
        "chh" to MIME_TEXT_PLAIN,
        "java" to MIME_TEXT_PLAIN,
        "csv" to MIME_TEXT_PLAIN,
        "bat" to MIME_TEXT_PLAIN,
        "cmd" to MIME_TEXT_PLAIN,
        "asc" to MIME_TEXT_PLAIN,
        "rtf" to MIME_TEXT_RTF,
        "rtx" to MIME_TEXT_RICHTEXT,
        "html" to MIME_TEXT_HTML,
        "htm" to MIME_TEXT_HTML,
        "zip" to MIME_APPLICATION_ZIP,
        "rar" to MIME_APPLICATION_X_RAR_COMPRESSED,
        "gzip" to MIME_APPLICATION_X_GZIP,
        "gz" to MIME_APPLICATION_X_GZIP,
        "tgz" to MIME_APPLICATION_TGZ,
        "tar" to MIME_APPLICATION_X_TAR,
        "gif" to MIME_IMAGE_GIF,
        "jpeg" to MIME_IMAGE_JPEG,
        "jpg" to MIME_IMAGE_JPEG,
        "jpe" to MIME_IMAGE_JPEG,
        "tiff" to MIME_IMAGE_TIFF,
        "tif" to MIME_IMAGE_TIFF,
        "png" to MIME_IMAGE_PNG,
        "au" to MIME_AUDIO_BASIC,
        "snd" to MIME_AUDIO_BASIC,
        "wav" to MIME_AUDIO_X_WAV,
        "mov" to MIME_VIDEO_QUICKTIME,
        "qt" to MIME_VIDEO_QUICKTIME,
        "mpeg" to MIME_VIDEO_MPEG,
        "mpg" to MIME_VIDEO_MPEG,
        "mpe" to MIME_VIDEO_MPEG,
        "abs" to MIME_VIDEO_MPEG,
        "doc" to MIME_APPLICATION_MSWORD,
        "xls" to MIME_APPLICATION_VND_MSEXCEL,
        "eps" to MIME_APPLICATION_POSTSCRIPT,
        "ai" to MIME_APPLICATION_POSTSCRIPT,
        "ps" to MIME_APPLICATION_POSTSCRIPT,
        "pdf" to MIME_APPLICATION_PDF,
        "exe" to MIME_APPLICATION_OCTET_STREAM,
        "dll" to MIME_APPLICATION_OCTET_STREAM,
        "class" to MIME_APPLICATION_OCTET_STREAM,
        "jar" to MIME_APPLICATION_JAVA_ARCHIVE,
    )
    private val mimeTypeMappginRev = mutableMapOf<String, String>().apply {
        mimeTypeMapping.forEach { (k, v) -> this[v] = k }
    }

    fun registerMimeType(ext: String, mimeType: String) {
        mimeTypeMapping[ext] = mimeType
    }
    fun getMimeType(ext: String): String {
        var mimeType = lookupMimeType(ext)
        if (mimeType == null) {
            mimeType = MIME_APPLICATION_OCTET_STREAM
        }
        return mimeType
    }
    fun lookupMimeType(ext: String): String? {
        return mimeTypeMapping[ext.lowercase(Locale.getDefault())]
    }

    fun MIME.ext() = mimeTypeMappginRev.get(this)
}
