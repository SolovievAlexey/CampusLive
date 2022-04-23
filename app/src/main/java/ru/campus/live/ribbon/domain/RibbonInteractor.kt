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
    private val userDataSource: IUserDataSource,
) {

    fun getCash(): ArrayList<RibbonModel> {
        val result = repository.getCash()
        return map(result)
    }

    fun postCash(model: ArrayList<RibbonModel>) {
        if (!isErrorView(model)) repository.postCash(model = model)
    }

    fun get(model: ArrayList<RibbonModel>, offset: Int): ArrayList<RibbonModel> {
        return when (val result = repository.get(offset = offset)) {
            is ResponseObject.Success -> resultSuccess(
                oldModel = model,
                newModel = result.data,
                offset = offset
            )
            is ResponseObject.Failure -> getFailure(
                oldModel = model,
                offset = offset,
                error = result.error
            )
        }
    }

    private fun resultSuccess(
        oldModel: ArrayList<RibbonModel>, newModel: ArrayList<RibbonModel>, offset: Int
    ): ArrayList<RibbonModel> {
        if (offset == 0) {
            if(oldModel.size != 0) newModel.add(0, oldModel[0])
            return newModel
        }
        oldModel.addAll(newModel)
        return oldModel
    }

    private fun getFailure(
        oldModel: ArrayList<RibbonModel>, offset: Int, error: ErrorModel
    ): ArrayList<RibbonModel> {
        if (oldModel.size != 0 && offset == 0) {
            if(isErrorView(oldModel)) oldModel.removeAt(1)
            oldModel.add(1, getErrorItem(error))
        }
        return oldModel
    }

    fun isErrorView(model: ArrayList<RibbonModel>): Boolean {
        val count = model.count { it.viewType == RibbonViewType.ERROR }
        return count != 0
    }

    fun getOffset(refresh: Boolean, model: ArrayList<RibbonModel>): Int {
        return if (refresh) 0 else model.count { it.viewType == RibbonViewType.PUBLICATION }
    }

    fun lazyDownloadFeed(model: ArrayList<RibbonModel>): Boolean {
        return model.size == 25
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
                    val params = displayMetrics.get(
                        item.attachment.attachmentWidth,
                        item.attachment.attachmentHeight
                    )
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
            model.add(
                0, RibbonModel(
                    viewType = RibbonViewType.LOCATION,
                    location = userDataSource.location()
                )
            )
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
