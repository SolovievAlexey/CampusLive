package ru.campus.feature_discussion.data.repository

import ru.campus.feature_discussion.data.model.DiscussionModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 22:48
 */

interface UserAvatarStore {
    fun execute(model: ArrayList<DiscussionModel>?)
}