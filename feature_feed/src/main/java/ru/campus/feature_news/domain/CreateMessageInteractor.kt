package ru.campus.feature_news.domain

import ru.campus.core.data.GalleryDataModel
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UploadMediaModel
import ru.campus.feature_news.data.FeedModel
import ru.campus.feature_news.data.FeedPostModel
import ru.campus.feature_news.data.NewsRepository
import ru.campus.file_upload.data.UploadMediaRepository
import ru.campus.file_upload.data.UploadResultModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 21:51
 */

class CreateMessageInteractor @Inject constructor(
    private val repository: NewsRepository,
    private val uploadRepository: UploadMediaRepository
) {

    fun post(params: FeedPostModel): ResponseObject<FeedModel> {
        return repository.post(params)
    }

    fun upload(params: GalleryDataModel): ResponseObject<UploadResultModel> {
        return uploadRepository.upload(params = params)
    }

    fun uploadMediaMap(item: GalleryDataModel): UploadMediaModel {
        return UploadMediaModel(
            id = 0,
            fullPath = item.fullPath,
            upload = true,
            error = false,
            animation = false
        )
    }

}