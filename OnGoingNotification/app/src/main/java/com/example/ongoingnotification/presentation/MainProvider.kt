package com.example.ongoingnotification.presentation

import android.content.ContentProvider
import android.content.ContentValues
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.example.ongoingnotification.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainProvider: ContentProvider() {
    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?,
    ): Cursor? {
        return null
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun openAssetFile(uri: Uri, mode: String): AssetFileDescriptor? {
        Log.d("DEBUG_ANKIT", "openAssetFile: is called uri : $uri")

        val drawable = context?.getDrawable(R.drawable.walk)
        Log.d("DEBUG_ANKIT", "openAssetFile: inputStream : $drawable")
        val bitmap = drawable?.toBitmap()
        Log.d("DEBUG_ANKIT", "openAssetFile: bitmap : $bitmap")

        try {
            // Create a temporary file to save the bitmap
            val tempFile = File(context?.cacheDir, "temp_image.png")
            val fileOutputStream = FileOutputStream(tempFile)

            // Compress the bitmap to the temporary file
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()

            // Obtain an AssetFileDescriptor for the temporary file
            val parcelFileDescriptor = ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
            return AssetFileDescriptor(parcelFileDescriptor, 0, tempFile.length())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}