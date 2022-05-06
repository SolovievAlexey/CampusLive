package ru.campus.live.ribbon.domain

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
import ru.campus.live.ribbon.data.model.*
import ru.campus.live.ribbon.data.repository.IRibbonRepository
import ru.campus.live.ribbon.data.repository.RibbonStatusRepository
import ru.campus.live.ribbon.domain.usecase.RibbonVoteUseCase
import javax.inject.Inject

class RibbonInteractor @Inject constructor(
    private val repository: IRibbonRepository,
    private val uploadRepository: IUploadMediaRepository,
    private val displayMetrics: DisplayMetrics,
    private val titleUseCase: DiscussionTitleUseCase,
    private val statusRepository: RibbonStatusRepository
) {

    fun status(): RibbonStatusModel {
        return statusRepository.get()
    }

    fun getModel(oldModel: ArrayList<RibbonModel>?): ArrayList<RibbonModel> {
        val model = ArrayList<RibbonModel>()
        oldModel?.let { model.addAll(it) }
        return model
    }

    fun cash(): ArrayList<RibbonModel> {
        val result = repository.getCash()
        return map(result)
    }

    fun get(offset: Int): ResponseObject<ArrayList<RibbonModel>> {
        return repository.get(offset = offset)
    }

    fun render(model: ArrayList<RibbonModel>): ArrayList<RibbonModel> {
        return model.preparation()
    }

    fun render(
        oldModel: ArrayList<RibbonModel>, response: ResponseRibbon, offset: Int,
    ): ArrayList<RibbonModel> {
        return if (response.statusCode == 200) onRibbonSuccess(oldModel, response, offset)
        else onRibbonFailure(oldModel, response, offset)
    }

    private fun onRibbonSuccess(
        oldModel: ArrayList<RibbonModel>, response: ResponseRibbon, offset: Int,
    ): ArrayList<RibbonModel> {
        if (offset == 0) return map(response.data)
        oldModel.addAll(response.data)
        return map(oldModel)
    }

    private fun onRibbonFailure(
        oldMode: ArrayList<RibbonModel>, response: ResponseRibbon, offset: Int,
    ): ArrayList<RibbonModel> {
        return oldMode
    }

    fun getOffset(refresh: Boolean, model: ArrayList<RibbonModel>): Int {
        return if (refresh) 0 else model.count { it.viewType == RibbonViewType.PUBLICATION }
    }

    fun lazyDownloadFeed(statusCode: Int): Boolean {
        return statusCode == 200
    }

    fun map(model: ArrayList<RibbonModel>): ArrayList<RibbonModel> {
        return model.preparation()
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
