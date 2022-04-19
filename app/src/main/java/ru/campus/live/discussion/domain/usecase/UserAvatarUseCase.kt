package ru.campus.live.discussion.domain.usecase

import ru.campus.live.discussion.data.model.DiscussionModel

class UserAvatarUseCase() {

    fun execute(model: ArrayList<DiscussionModel>, uid: Int): Int {
        val icon = search(model, uid)
        if (icon == 0) return generate(model)
        return icon
    }

    private fun search(model: ArrayList<DiscussionModel>?, uid: Int): Int {
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