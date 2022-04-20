package ru.campus.live.core.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.UploadResultModel
import ru.campus.live.gallery.data.model.GalleryDataModel

interface IUploadMediaRepository {
    fun upload(params: GalleryDataModel): ResponseObject<UploadResultModel>
}