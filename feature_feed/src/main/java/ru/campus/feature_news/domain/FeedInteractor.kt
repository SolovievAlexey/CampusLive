package ru.campus.feature_news.domain

import ru.campus.core.data.DisplayMetrics
import ru.campus.core.data.ErrorMessageHandler
import ru.campus.core.data.ResponseObject
import ru.campus.feature_news.data.model.FeedModel
import ru.campus.feature_news.data.model.FeedViewType
import ru.campus.feature_news.data.repository.NewsRepository
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 18:47
 */

private const val RESPONSE_API_SIZE = 25

class FeedInteractor @Inject constructor(
    private val repository: NewsRepository,
    private val titleCommentsUseCase: TitleCommentsUseCase,
    private val displayMetrics: DisplayMetrics,
    private val errorMessageHandler: ErrorMessageHandler,
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

    fun save(model: ArrayList<FeedModel>) {
        repository.save(model)
    }

    fun cache(): ArrayList<FeedModel> {
        return repository.cache()
    }

    fun insert(list: ArrayList<FeedModel>, publication: FeedModel): ArrayList<FeedModel> {
        list.add(0, publication)
        return list
    }

    fun footer(model: ArrayList<FeedModel>): ArrayList<FeedModel> {
        if (model.size == RESPONSE_API_SIZE) {
            model.add(FeedModel(viewType = FeedViewType.FOOTER))
        }
        return model
    }

    fun error(statusCode: Int): String {
        return errorMessageHandler.get(statusCode = statusCode)
    }

}