package hr.algebra.spacex.common.util

import android.content.Context

object StoragePathFinder {
    private var internalStoragePath: String? = null

    fun initilize(context: Context) {
        if (internalStoragePath == null) {
            internalStoragePath = context.filesDir.absolutePath
        }
    }

    fun getInternalStoragePath(): String? {
        return internalStoragePath
    }
}