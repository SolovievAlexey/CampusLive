package ru.campus.feature_discussion.domain

import ru.campus.core.data.GalleryDataModel
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UploadMediaModel
import ru.campus.core.data.UserDataStore
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionPostModel
import ru.campus.feature_discussion.data.repository.DiscussionRepository
import ru.campus.feature_discussion.data.repository.ErrorDataSource
import ru.campus.file_upload.data.UploadMediaRepository
import ru.campus.file_upload.data.UploadResultModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 19:09
 */

class CreateCommentInteractor @Inject constructor(
    private val repository: DiscussionRepository,
    private val uploadMediaRepository: UploadMediaRepository,
    private val userDataStore: UserDataStore,
    private val errorDataSource: ErrorDataSource
) {

    fun post(params: DiscussionPostModel): ResponseObject<DiscussionModel> {
        return if (params.icon != 0)
            repository.post(params = params)
        else
            ResponseObject.Failure(code = 409)
    }

    fun avatar(): Int {
        return userDataStore.avatar()
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

    fun upload(params: GalleryDataModel): ResponseObject<UploadResultModel> {
        return uploadMediaRepository.upload(params = params)
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
        return errorDataSource.get(statusCode = statusCode)
    }

}