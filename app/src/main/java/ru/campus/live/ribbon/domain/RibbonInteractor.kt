package ru.campus.live.ribbon.domain

import ru.campus.live.core.data.model.ErrorModel
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.core.data.source.DisplayMetrics
import ru.campus.live.core.data.source.IUserDataSource
import ru.campus.live.discussion.data.model.DiscussionModel
import ru.campus.live.discussion.data.model.DiscussionViewType
import ru.campus.live.discussion.domain.usecase.DiscussionTitleUseCase
import ru.campus.live.gallery.data.model.GalleryDataModel
import ru.campus.live.gallery.data.model.UploadMediaObject
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonPostModel
import ru.campus.live.ribbon.data.model.RibbonViewType
import ru.campus.live.ribbon.data.repository.IRibbonRepository
import ru.campus.live.ribbon.domain.usecase.RibbonVoteUseCase
import javax.inject.Inject

class RibbonInteractor @Inject constructor(
    private val repository: IRibbonRepository,
    private val uploadRepository: IUploadMediaRepository,
    private val displayMetrics: DisplayMetrics,
    private val titleUseCase: DiscussionTitleUseCase,
    private val userDataSource: IUserDataSource
) {

    fun get(model: ArrayList<RibbonModel>, offset: Int): ArrayList<RibbonModel> {
        when (val result = repository.get(offset = offset)) {
            is ResponseObject.Success -> return result.data
            is ResponseObject.Failure -> {
                if (offset == 0) {
                    val response = ArrayList<RibbonModel>()
                    response.add(0, getErrorItem(result.error))
                    return response
                }
                return model
            }
        }
    }

    fun lazyDownloadFeed(model: ArrayList<RibbonModel>): Boolean {
        return model.size == 25
    }

    fun getOffset(model: ArrayList<RibbonModel>): Int {
        return model.count { it.viewType == RibbonViewType.PUBLICATION }
    }

    fun map(model: ArrayList<RibbonModel>): ArrayList<RibbonModel> {
        return model.preparation().location()
    }

    fun remove(model: ArrayList<RibbonModel>, id: Int): ArrayList<RibbonModel> {
        val index = model.indexOfFirst { it.id == id }
        model.removeAt(index)
        return model
    }

    fun sendComplaintOnServer(item: RibbonModel) {
        repository.complaint(item.id)
    }

    fun vote(model: ArrayList<RibbonModel>, params: VoteModel): ArrayList<RibbonModel> {
        return RibbonVoteUseCase().execute(model, params)
    }

    fun sendVoteOnServer(params: VoteModel) {
        repository.vote(params)
    }

    fun post(params: RibbonPostModel): ResponseObject<RibbonModel> {
        return repository.post(params)
    }

    fun upload(params: GalleryDataModel): UploadMediaObject {
        val result = uploadRepository.upload(params)
        val error = result !is ResponseObject.Success
        val id = if (result is ResponseObject.Success) result.data.id else 0
        return UploadMediaObject(
            id = id,
            fullPath = params.fullPath,
            upload = false,
            error = error,
            animation = false
        )
    }

    private fun getErrorItem(params: ErrorModel): RibbonModel {
        return RibbonModel(viewType = RibbonViewType.ERROR, message = params.message)
    }

    private fun ArrayList<RibbonModel>.preparation(): ArrayList<RibbonModel> {
        val model = this
        model.forEachIndexed { index, item ->
            if (item.commentsString.isEmpty())
                model[index].commentsString = titleUseCase.execute(item.comments)
            if (item.viewType == RibbonViewType.UNKNOWN) {
                model[index].viewType = RibbonViewType.PUBLICATION
                if (item.attachment != null) {
                    val params = displayMetrics.get(item.attachment.width, item.attachment.height)
                    model[index].mediaWidth = params[0]
                    model[index].mediaHeight = params[1]
                }
            }
        }
        return model
    }

    private fun ArrayList<RibbonModel>.location(): ArrayList<RibbonModel> {
        val model = this
        if (model.size != 0 && model[0].viewType != RibbonViewType.LOCATION) {
            model.add(0,
                RibbonModel(viewType = RibbonViewType.LOCATION,
                    location = userDataSource.location()))
        }
        return model
    }

    fun convertToDiscussionModel(item: RibbonModel): DiscussionModel {
        return DiscussionModel(
            type = DiscussionViewType.PUBLICATION,
            id = item.id,
            mediaWidth = item.mediaWidth,
            mediaHeight = item.mediaHeight,
            message = item.message,
            attachment = item.attachment,
            rating = item.rating,
            vote = item.vote,
            relativeTime = item.relativeTime
        )
    }

}
