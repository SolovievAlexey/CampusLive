package ru.campus.live.start.data.repository

import ru.campus.live.R
import ru.campus.live.core.data.source.ResourceManager
import ru.campus.live.start.data.model.StartModel
import javax.inject.Inject

class StartDataSource @Inject constructor(
    private val resourceManager: ResourceManager
) {

    fun execute(): ArrayList<StartModel> {
        val model = ArrayList<StartModel>()
        model.add(
            StartModel(
                id = 1,
                title = resourceManager.get(R.string.start_title_two),
                message = resourceManager.get(R.string.start_message_two),
                icon = R.drawable.college
            )
        )
        model.add(
            StartModel(
                id = 1,
                title = resourceManager.get(R.string.start_title_three),
                message = resourceManager.get(R.string.start_message_three),
                icon = R.drawable.alarm
            )
        )
        model.add(
            StartModel(
                id = 1,
                title = resourceManager.get(R.string.start_title_four),
                message = resourceManager.get(R.string.start_message_four),
                icon = R.drawable.forum
            )
        )
        return model
    }

}