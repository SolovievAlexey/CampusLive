package ru.campus.live.gallery.data.repository

import ru.campus.live.gallery.data.model.GalleryDataModel

interface IGalleryRepository {
    fun get(offset: Int): ArrayList<GalleryDataModel>
}