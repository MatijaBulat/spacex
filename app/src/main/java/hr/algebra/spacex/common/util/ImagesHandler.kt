package hr.algebra.spacex.common.util

import android.util.Log
import java.io.File
import java.net.HttpURLConnection
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.Paths

fun downloadImageAndStore(directoryPath: String, url: String): String? {
    val ext = url.substring(url.lastIndexOf(".")) //.jpg
    val filename = url.substring(url.lastIndexOf("/") + 1)
    val file: File = createFile(directoryPath, filename, ".jpg")
    try {
        val con: HttpURLConnection = createGetHttpUrlConnection(url)
        Files.copy(con.inputStream, Paths.get(file.absolutePath))
         return file.absolutePath
    } catch (e: Exception) {
        Log.e("DOWNLOAD IMAGE ${e.message} $e", "Error")
    }
    return null
}

fun createFile(directoryPath: String, fileName: String, ext: String): File {
    val file = File(directoryPath, File.separator + fileName + ext)
    if (file.exists()) {
        file.delete()
    }
    return file
}