package ru.campus.feature_gallery.data

import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 23:05
 */

class BaseGalleryDataSource @Inject constructor(
    private val dataSource: GalleryDataSource,
) : GalleryRepository {

    override fun execute(offset: Int): ArrayList<GalleryDataModel> {
        return dataSource.execute(offset = offset)
    }

}