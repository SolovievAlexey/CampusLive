package ru.campus.live.discussion.domain.usecase

import ru.campus.live.R
import ru.campus.live.core.data.source.ResourceManager
import javax.inject.Inject

class DiscussionTitleUseCase @Inject constructor(private val resourceManager: ResourceManager) {

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