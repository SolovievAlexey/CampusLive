package ru.campus.live.core.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject

class PreparationMediaUseCase @Inject constructor(private val context: Context) {

    fun execute(path: String, realOrientation: Int): File? {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmapFull = BitmapFactory.decodeFile(path, options)
        val imageHeight = options.outHeight
        val imageWidth = options.outWidth
        val bitmapRotate: Bitmap = getBitmap(bitmapFull, realOrientation)
        val maxSize = 960
        val newWidth: Int
        val newHeight: Int

        //У ИЗОБРАЖЕНИЕ С ОРИЕНТАЦИЕ БОЛЬШЕ 6 ПОМЕНЕНЫ МЕСТАМИ ВЫСОТА И ШИРИНА
        //http://sylvana.net/jpegcrop/exif_orientation.html
        if (realOrientation <= 4) {
            newWidth = 960
            val k = imageWidth.toFloat() / newWidth.toFloat()
            val newHeightFloat = imageHeight.toFloat() / k
            newHeight = newHeightFloat.toInt()
        } else {
            /* При неправильной ориентации для поиска коэффициента сжатия делим ВЫСОТУ
            на новую ширину!! а не старую ширину */
            newWidth = 960
            val k = imageHeight.toFloat() / newWidth.toFloat()
            val newHeightFloat = imageWidth.toFloat() / k
            newHeight = newHeightFloat.toInt()
        }

        return createFile(bitmapFull, bitmapRotate, imageWidth, newWidth, newHeight)
    }

    private fun createFile(
        bitmapFull: Bitmap,
        bitmapRotate: Bitmap,
        imageWidth: Int,
        newWidth: Int,
        newHeight: Int
    ): File? {
        val myFileUpload = File(context.cacheDir, Math.random().toString() + ".png")
        if (imageWidth > 960) {
            val bitmapMini = Bitmap.createScaledBitmap(bitmapRotate, newWidth, newHeight, false)
            bitmapFull.recycle()
            bitmapRotate.recycle()
            val os: OutputStream
            try {
                os = FileOutputStream(myFileUpload)
                bitmapMini.compress(Bitmap.CompressFormat.JPEG, 100, os)
                os.flush()
                os.close()
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            bitmapMini.recycle()
        } else {
            val os: OutputStream
            try {
                os = FileOutputStream(myFileUpload)
                bitmapFull.compress(Bitmap.CompressFormat.JPEG, 100, os)
                os.flush()
                os.close()
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            bitmapFull.recycle()
            bitmapRotate.recycle()
        }
        return myFileUpload
    }


    private fun getBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            2 -> matrix.setScale(-1F, 1F)
            3 -> matrix.setRotate(180F)
            4 -> {
                matrix.setRotate(180F)
                matrix.postScale(-1F, 1F)
            }
            5 -> {
                matrix.setRotate(90F)
                matrix.postScale(-1F, 1F)
            }
            6 -> matrix.setRotate(90F)
            7 -> {
                matrix.setRotate(-90F)
                matrix.postScale(-1F, 1F)
            }
            8 -> matrix.setRotate(-90F)
            else -> return bitmap
        }
        val bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width,
            bitmap.height, matrix, true)
        bitmap.recycle()
        return bmRotated
    }


}