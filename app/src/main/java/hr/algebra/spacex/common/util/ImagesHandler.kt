package hr.algebra.spacex.common.util

import android.util.Log
import java.io.File
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths


fun downloadImageAndStore(url: String): String? {
    val filename = url.substring(url.lastIndexOf("/") + 1)
    val file = createFile(filename)
    try{
        val con : HttpURLConnection = createGetHttpUrlConnection(url)
        Files.copy(con.inputStream, Paths.get(file.toURI()))
        return file.absolutePath
    } catch (e: Exception) {
        Log.e("IMAGESHANDLER", e.toString(), e)
    }

    return null
}

private fun createFile(filename: String): File {
    val dir = StoragePathFinder.getInternalStoragePath()
    val file = File(dir, filename)
    if (file.exists()) file.delete()
    return file
}
