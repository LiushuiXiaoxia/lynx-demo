package cn.mycommons.lynx_android.util

import android.content.Context
import androidx.core.net.toUri
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URL


fun String.uriInputStream(context: Context): InputStream? {
    return openInputStreamFromUriString(context, this)
}

fun openInputStreamFromUriString(context: Context, uriString: String): InputStream? {
    val uri = uriString.toUri()

    return when (uri.scheme) {
        "file" -> {
            FileInputStream(File(uri.path!!))
        }

        "content" -> {
            context.contentResolver.openInputStream(uri)
        }

        "assets" -> {
            val path = uri.schemeSpecificPart.removePrefix("//")
            context.assets.open(path)
        }

        "http", "https" -> {
            // 下载文件到缓存
            val url = URL(uriString)
            val connection = url.openConnection()
            connection.connect()

            val input = connection.getInputStream()
            val cacheFile = File.createTempFile("download_", null, context.cacheDir)
            cacheFile.outputStream().use { input.copyTo(it) }

            FileInputStream(cacheFile)
        }

        null -> {
            // 处理没有 scheme 的路径，如 "/sdcard/file.txt"
            FileInputStream(File(uriString))
        }

        else -> {
            throw IllegalArgumentException("Unsupported URI scheme: ${uri.scheme}")
        }
    }
}
