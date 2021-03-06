package ru.campus.feature_gallery.data

import ru.campus.core.data.GalleryDataModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 23:05
 */

interface GalleryRepository {
    fun execute(offset: Int): ArrayList<GalleryDataModel>
}