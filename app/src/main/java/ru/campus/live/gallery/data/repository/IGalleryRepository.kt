package ru.campus.live.gallery.data.repository

import ru.campus.live.gallery.data.model.GalleryDataObject

interface IGalleryRepository {
    fun get(offset: Int): ArrayList<GalleryDataObject>
}