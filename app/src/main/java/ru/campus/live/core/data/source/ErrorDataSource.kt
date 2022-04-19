package ru.campus.live.core.data.source

import ru.campus.live.R
import ru.campus.live.core.data.model.ErrorObject
import javax.inject.Inject

class ErrorDataSource @Inject constructor(private val resourceManager: ResourceManager) {

    fun get(code: Int = 0): ErrorObject {
        return when (code) {
            0 -> ErrorObject(code, R.drawable.neural, resourceManager.get(R.string.error_network))
            else -> ErrorObject(code, R.drawable.error, resourceManager.get(R.string.error_unknown))
        }
    }

}