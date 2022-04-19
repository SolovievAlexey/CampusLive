package ru.campus.live.gallery.data.repository

import ru.campus.live.gallery.data.model.GalleryDataObject

interface IGalleryDataSource {
    fun execute(offset: Int): ArrayList<GalleryDataObject>
}