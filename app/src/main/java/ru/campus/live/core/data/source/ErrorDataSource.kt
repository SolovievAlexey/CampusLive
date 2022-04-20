package ru.campus.live.core.data.source

import ru.campus.live.R
import ru.campus.live.core.data.model.ErrorModel
import javax.inject.Inject

class ErrorDataSource @Inject constructor(private val resourceManager: ResourceManager) {

    fun get(code: Int = 0): ErrorModel {
        return when (code) {
            0 -> ErrorModel(code, R.drawable.neural, resourceManager.get(R.string.error_network))
            404 -> ErrorModel(code, R.drawable.error, resourceManager.get(R.string.not_publication))
            else -> ErrorModel(code, R.drawable.error, resourceManager.get(R.string.error_unknown))
        }
    }

}