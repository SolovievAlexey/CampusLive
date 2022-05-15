package ru.campus.feature_news.domain

import ru.campus.core.data.DisplayMetrics
import ru.campus.core.data.ResponseObject
import ru.campus.feature_news.data.FeedModel
import ru.campus.feature_news.data.FeedViewType
import ru.campus.feature_news.data.NewsRepository
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 18:47
 */

class FeedInteractor @Inject constructor(
    private val repository: NewsRepository,
    private val titleCommentsUseCase: TitleCommentsUseCase,
    private val displayMetrics: DisplayMetrics
) {

    fun get(offset: Int): ResponseObject<ArrayList<FeedModel>> {
        return repository.get(offset = offset)
    }

    fun preparation(model: ArrayList<FeedModel>): ArrayList<FeedModel> {
        model.forEachIndexed { index, item ->
            if (item.commentsString.isEmpty())
                model[index].commentsString = titleCommentsUseCase.execute(item.comments)
            if (item.viewType == FeedViewType.UNKNOWN) {
                model[index].viewType = FeedViewType.PUBLICATION
                if (item.attachment != null) {
                    val params = displayMetrics.get(
                        item.attachment.width,
                        item.attachment.height
                    )
                    model[index].mediaWidth = params[0]
                    model[index].mediaHeight = params[1]
                }
            }
        }
        return model
    }


}