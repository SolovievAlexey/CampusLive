package ru.campus.live.ribbon.domain

import android.util.Log
import ru.campus.live.core.data.source.DisplayMetrics
import ru.campus.live.core.data.source.IUserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.discussion.domain.usecase.DiscussionTitleUseCase
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.data.model.UploadMediaObject
import ru.campus.live.ribbon.data.model.RibbonPostModel
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonViewType
import ru.campus.live.ribbon.data.repository.IRibbonRepository
import ru.campus.live.ribbon.domain.usecase.RibbonVoteUseCase
import javax.inject.Inject

class RibbonInteractor @Inject constructor(
    private val repository: IRibbonRepository,
    private val uploadRepository: IUploadMediaRepository,
    displayMetrics: DisplayMetrics,
    titleUseCase: DiscussionTitleUseCase,
    userDataSource: IUserDataSource,
) : BaseRibbonInteractor(userDataSource, displayMetrics, titleUseCase) {

    fun get(offset: Int): ArrayList<RibbonModel> {
        when(val result = repository.get(offset = offset)) {
            is ResponseObject.Success -> return result.data
            is ResponseObject.Failure -> {
                val model = ArrayList<RibbonModel>()
                if (offset == 0 && result.error.code == 404)
                    model.add(0, getErrorItem(result.error))
                return model
            }
        }
    }

    fun getOffset(model: ArrayList<RibbonModel>): Int {
        return model.count { it.viewType == RibbonViewType.PUBLICATION }
    }

    fun map(model: ArrayList<RibbonModel>): ArrayList<RibbonModel> {
        return model.preparation().header()
    }

    fun complaint(model: ArrayList<RibbonModel>, id: Int): ArrayList<RibbonModel> {
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

    fun upload(params: GalleryDataObject): UploadMediaObject {
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

}
