package ru.campus.live.core.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.gallery.data.model.GalleryDataModel
import ru.campus.live.gallery.data.model.UploadMediaObject
import javax.inject.Inject

class UploadMediaInteractor @Inject constructor(
    private val uploadRepository: IUploadMediaRepository
) {

    fun addList(params: GalleryDataModel): UploadMediaObject {
        return UploadMediaObject(
            id = 0,
            fullPath = params.fullPath,
            upload = true,
            error = false,
            animation = false
        )
    }

    fun upload(params: GalleryDataModel): UploadMediaObject {
        val result = uploadRepository.upload(params)
        val id = if(result is ResponseObject.Success) result.data.id else 0
        val error = result !is ResponseObject.Success
        return UploadMediaObject(
            id = id,
            fullPath = params.fullPath,
            upload = false,
            error = error,
            animation = false
        )
    }

    fun mediaRemove(): ArrayList<UploadMediaObject> {
        return ArrayList()
    }

}