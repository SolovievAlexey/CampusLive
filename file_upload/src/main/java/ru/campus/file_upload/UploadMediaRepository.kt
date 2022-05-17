package ru.campus.file_upload

import ru.campus.core.data.ResponseObject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 22:29
 */

interface UploadMediaRepository {
    fun upload(params: GalleryDataModel): ResponseObject<UploadResultModel>
}