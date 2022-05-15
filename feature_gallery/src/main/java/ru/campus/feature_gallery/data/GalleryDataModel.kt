package ru.campus.feature_gallery.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 23:00
 */

@Parcelize
data class GalleryDataModel(
    val id: Int,
    val fullPath: String,
    val realOrientation: Int
) : Parcelable
