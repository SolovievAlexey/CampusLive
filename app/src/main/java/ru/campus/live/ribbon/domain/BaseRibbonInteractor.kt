package ru.campus.live.ribbon.domain

import ru.campus.live.core.data.source.DisplayMetrics
import ru.campus.live.core.data.source.IUserDataSource
import ru.campus.live.core.data.model.ErrorObject
import ru.campus.live.discussion.domain.usecase.DiscussionTitleUseCase
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonViewType
import javax.inject.Inject

open class BaseRibbonInteractor @Inject constructor(
    val userDataSource: IUserDataSource,
    private val displayMetrics: DisplayMetrics,
    private val titleUseCase: DiscussionTitleUseCase,
) {

    fun getErrorItem(params: ErrorObject): RibbonModel {
        return RibbonModel(viewType = RibbonViewType.ERROR, message = params.message)
    }

    fun ArrayList<RibbonModel>.preparation(): ArrayList<RibbonModel> {
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

    fun ArrayList<RibbonModel>.header(): ArrayList<RibbonModel> {
        val model = this
        if (model.size != 0 && model[0].viewType != RibbonViewType.HEADING) {
            val location = LocationModel(
                id = userDataSource.location().id,
                name = userDataSource.location().name,
                address = userDataSource.location().name,
                type = userDataSource.location().type
            )
            model.add(0, RibbonModel(viewType = RibbonViewType.HEADING, location = location))
        }
        return model
    }

}