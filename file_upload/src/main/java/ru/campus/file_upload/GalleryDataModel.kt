package ru.campus.file_upload

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 22:29
 */

@Parcelize
data class GalleryDataModel(
    val id: Int,
    val fullPath: String,
    val realOrientation: Int
) : Parcelable