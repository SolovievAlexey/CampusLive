package ru.campus.feature_news.domain

import ru.campus.core.data.ResourceManager
import ru.campus.feature_news.R
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 19:29
 */

class TitleCommentsUseCase @Inject constructor(private val resourceManager: ResourceManager) {

    fun execute(count: Int): String {
        val decline = arrayOf(
            resourceManager.get(R.string.s_comment_one),
            resourceManager.get(R.string.s_comments_two),
            resourceManager.get(R.string.s_comments_3),
            resourceManager.get(R.string.s_comments_4),
            resourceManager.get(R.string.s_comments_5)
        )

        val commentsCount = count % 10
        return if (commentsCount > 4) {
            "$count " + resourceManager.get(R.string.s_comment_one)
        } else {
            if (count in 12..19) {
                "$count " + resourceManager.get(R.string.s_comment_one)
            } else {
                if (count == 0) {
                    resourceManager.get(R.string.none_comments)
                } else {
                    "$count " + decline[commentsCount]
                }
            }
        }
    }

}