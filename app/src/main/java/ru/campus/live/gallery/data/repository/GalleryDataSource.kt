package ru.campus.live.gallery.data.repository

import android.content.Context
import android.database.Cursor
import android.media.ExifInterface
import android.provider.MediaStore
import ru.campus.live.gallery.data.model.GalleryDataModel
import java.io.IOException
import javax.inject.Inject

class GalleryDataSource @Inject constructor(private val context: Context): IGalleryDataSource {

    override fun execute(offset: Int): ArrayList<GalleryDataModel> {
        val cursor = cursorCreate(offset)
        val model = modelCreate(cursor)
        cursor?.close()
        return model
    }

    private fun cursorCreate(offset: Int): Cursor? {
        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
        return context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            columns, null, null, "_ID DESC LIMIT $offset,50"
        )
    }

    private fun modelCreate(cursor: Cursor?): ArrayList<GalleryDataModel> {
        val dataModel: ArrayList<GalleryDataModel> = ArrayList()
        if (cursor != null) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                val columnIndexPath = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                val fullPath = cursor.getString(columnIndexPath)
                dataModel.add(GalleryDataModel(0, fullPath, realOrientation(fullPath)))
            }
        }
        return dataModel
    }

    private fun realOrientation(fullPath: String): Int {
        var realOrientation = 0
        try {
            val exif = ExifInterface(fullPath)
            realOrientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return realOrientation
    }


}