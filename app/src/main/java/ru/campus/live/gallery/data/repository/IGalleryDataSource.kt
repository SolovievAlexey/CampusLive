package ru.campus.live.gallery.data.repository

import ru.campus.live.gallery.data.model.GalleryDataModel

interface IGalleryDataSource {
    fun execute(offset: Int): ArrayList<GalleryDataModel>
}