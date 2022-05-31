package ru.campus.feature_news.domain

import ru.campus.core.data.ErrorMessageHandler
import ru.campus.core.data.GalleryDataModel
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UploadMediaModel
import ru.campus.feature_news.data.model.FeedModel
import ru.campus.feature_news.data.model.FeedPostModel
import ru.campus.feature_news.data.repository.NewsRepository
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
    private val uploadRepository: UploadMediaRepository,
    private val errorMessageHandler: ErrorMessageHandler
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

    fun newUploadModel(
        model: ArrayList<UploadMediaModel>,
        result: ResponseObject<UploadResultModel>,
    ): ArrayList<UploadMediaModel> {
        val item = UploadMediaModel(
            id = model[0].id,
            fullPath = model[0].fullPath,
            upload = false,
            error = model[0].error,
            animation = model[0].animation
        )

        when (result) {
            is ResponseObject.Success -> {
                item.id = result.data.id
            }
            is ResponseObject.Failure -> {
                item.error = true
            }
        }
        val response = ArrayList<UploadMediaModel>()
        response.add(item)
        return response
    }

    fun error(statusCode: Int): String {
        return errorMessageHandler.get(statusCode)
    }

}