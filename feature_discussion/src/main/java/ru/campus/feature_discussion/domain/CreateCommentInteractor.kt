package ru.campus.feature_discussion.domain

import ru.campus.core.data.ResponseObject
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionPostModel
import ru.campus.feature_discussion.data.repository.DiscussionRepository
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 19:09
 */

class CreateCommentInteractor @Inject constructor(private val repository: DiscussionRepository) {

    fun post(params: DiscussionPostModel): ResponseObject<DiscussionModel> {
        return repository.post(params = params)
    }

}