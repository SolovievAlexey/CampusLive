package ru.campus.live.gallery.data.repository

import ru.campus.live.gallery.data.model.GalleryDataModel
import javax.inject.Inject

class GalleryRepository @Inject constructor(
    private val dataSource: IGalleryDataSource
) : IGalleryRepository {

    override fun get(offset: Int): ArrayList<GalleryDataModel> {
        return dataSource.execute(offset)
    }

}