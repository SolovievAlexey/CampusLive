package ru.campus.file_upload.data

import ru.campus.core.data.GalleryDataModel
import ru.campus.core.data.ResponseObject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 18.05.2022 21:26
 */

interface UploadMediaRepository {
    fun upload(params: GalleryDataModel): ResponseObject<UploadResultModel>
}