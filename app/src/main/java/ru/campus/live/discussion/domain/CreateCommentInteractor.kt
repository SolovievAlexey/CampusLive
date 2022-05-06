package ru.campus.live.discussion.domain

import ru.campus.live.core.data.source.IUserDataStore
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.discussion.data.model.CommentCreateModel
import ru.campus.live.discussion.data.model.DiscussionModel
import ru.campus.live.discussion.data.repository.IDiscussionRepository
import ru.campus.live.gallery.data.model.GalleryDataModel
import ru.campus.live.gallery.data.model.UploadMediaObject
import javax.inject.Inject

class CreateCommentInteractor @Inject constructor(
    private val repository: IDiscussionRepository,
    private val uploadRepository: IUploadMediaRepository,
    private val userDataStore: IUserDataStore,
) {

    fun post(params: CommentCreateModel): ResponseObject<DiscussionModel> {
        params.icon = userDataStore.getUserAvatarIcon()
        return repository.post(params = params)
    }

    fun upload(params: GalleryDataModel): UploadMediaObject {
        val result = uploadRepository.upload(params)
        val mediaServerId = if (result is ResponseObject.Success) result.data.id else 0
        return UploadMediaObject(
            id = mediaServerId,
            fullPath = params.fullPath,
            upload = false,
            error = mediaServerId == 0,
            animation = false
        )
    }

    fun uploadMediaMapper(params: GalleryDataModel): UploadMediaObject {
        return UploadMediaObject(
            id = 0,
            fullPath = params.fullPath,
            upload = true,
            error = false,
            animation = false
        )
    }

    fun clearUploadMedia(): ArrayList<UploadMediaObject> {
        return ArrayList()
    }

}