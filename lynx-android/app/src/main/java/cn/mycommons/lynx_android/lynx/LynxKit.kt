package cn.mycommons.lynx_android.lynx


import android.content.Context
import java.io.*
import java.net.URL
import java.security.MessageDigest
import java.util.zip.ZipInputStream

fun downloadAndUnzip(context: Context, url: String): File {
    // 1. 生成 URL 的 MD5 作为文件名
    val md5Name = md5(url)

    // 2. 创建临时目录和 zip 文件路径
    val tempDir = File(context.cacheDir, "lynx_$md5Name").apply { mkdirs() }
    if (tempDir.exists()) {
        val list = tempDir.listFiles() ?: emptyArray<File>()
        // 如果目录已存在且不为空，直接返回该目录
        if (list.isNotEmpty()) {
            return tempDir
        }
    }

    val zipFile = File.createTempFile(md5Name, ".zip", context.cacheDir)

    // 3. 下载 zip 到临时文件
    URL(url).openStream().use { input ->
        FileOutputStream(zipFile).use { output ->
            input.copyTo(output)
        }
    }

    // 4. 解压 zip 到临时目录
    ZipInputStream(FileInputStream(zipFile)).use { zis ->
        var entry = zis.nextEntry
        while (entry != null) {
            val newFile = File(tempDir, entry.name)
            if (entry.isDirectory) {
                newFile.mkdirs()
            } else {
                // 创建父目录
                newFile.parentFile?.mkdirs()
                FileOutputStream(newFile).use { fos ->
                    zis.copyTo(fos)
                }
            }
            zis.closeEntry()
            entry = zis.nextEntry
        }
    }

    return tempDir // 返回解压后的目录
}

private fun md5(input: String): String {
    val digest = MessageDigest.getInstance("MD5").digest(input.toByteArray())
    return digest.joinToString("") { "%02x".format(it) }
}
