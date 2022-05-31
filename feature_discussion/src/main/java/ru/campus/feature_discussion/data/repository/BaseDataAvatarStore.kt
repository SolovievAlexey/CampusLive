package ru.campus.feature_discussion.data.repository

import ru.campus.core.data.UserDataStore
import ru.campus.feature_discussion.data.model.DiscussionModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 22:49
 */

class BaseDataAvatarStore @Inject constructor(
    private val userDataStore: UserDataStore
) : UserAvatarStore {

    override fun execute(model: ArrayList<DiscussionModel>?) {
        var avatar = search(model)
        if (avatar == 0) avatar = generate(model)
        userDataStore.avatarSave(avatar)
    }

    private fun search(model: ArrayList<DiscussionModel>?): Int {
        val uid = userDataStore.uid()
        model?.forEach { item -> if (item.author == uid) return item.icon_id }
        return 0
    }

    private fun generate(model: ArrayList<DiscussionModel>?): Int {
        try {
            val icon = generateArrayListIcon()
            model?.forEach { item ->
                val index = icon.indexOfLast { it == item.icon_id }
                if (index != -1) icon.removeAt(index)
            }

            if (icon.size != 0) {
                val index = icon.random()
                return icon[index]
            }
            return 0
        } catch (e: Exception) {
            return 0
        }
    }

    private fun generateArrayListIcon(): ArrayList<Int> {
        val model = ArrayList<Int>()
        for (i in 1..40) {
            model.add(i)
        }
        return model
    }

}