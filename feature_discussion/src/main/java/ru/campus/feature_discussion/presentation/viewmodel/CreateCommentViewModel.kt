package ru.campus.feature_discussion.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.ResponseObject
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.core.presentation.SingleLiveEvent
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionPostModel
import ru.campus.feature_discussion.domain.CreateCommentInteractor
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 19:08
 */

class CreateCommentViewModel @Inject constructor(
    private val interactor: CreateCommentInteractor,
    private val dispatchers: CoroutineDispatchers,
) : ViewModel() {

    private val mutableSuccessLiveData = SingleLiveEvent<DiscussionModel>()
    val successLiveData: LiveData<DiscussionModel>
        get() = mutableSuccessLiveData

    private val mutableFailureLiveData = SingleLiveEvent<String>()
    val failureLiveData: LiveData<String>
        get() = mutableFailureLiveData

    fun post(params: DiscussionPostModel) {
        viewModelScope.launch(dispatchers.io) {
            when (val result = interactor.post(params = params)) {
                is ResponseObject.Success -> {
                    withContext(dispatchers.main) {
                        mutableSuccessLiveData.value = result.data
                    }
                }
                is ResponseObject.Failure -> {
                    Log.d("MyLog", "Код ошибки = "+result.code)
                    withContext(dispatchers.main) {
                        mutableFailureLiveData.value =
                            "Произошла какая-то ошибка! Необходимо повторить попытку ещё раз"
                    }
                }
            }
        }
    }

}