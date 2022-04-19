package ru.campus.live.gallery.data.repository

import ru.campus.live.gallery.data.model.GalleryDataObject
import javax.inject.Inject

class GalleryRepository @Inject constructor(
    private val dataSource: IGalleryDataSource
) : IGalleryRepository {

    override fun get(offset: Int): ArrayList<GalleryDataObject> {
        return dataSource.execute(offset)
    }

}