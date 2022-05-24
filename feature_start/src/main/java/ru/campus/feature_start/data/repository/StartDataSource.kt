package ru.campus.feature_start.data.repository

import ru.campus.core.data.ResourceManager
import ru.campus.feature_start.R
import ru.campus.feature_start.data.model.StartModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 19:46
 */

class StartDataSource @Inject constructor(
    private val resourceManager: ResourceManager
) {

    fun execute(): List<StartModel> {
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