package ru.campus.feature_start.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.ResponseObject
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.core.presentation.SingleLiveEvent
import ru.campus.feature_start.data.model.LoginModel
import ru.campus.feature_start.data.model.StartModel
import ru.campus.feature_start.domain.StartInteractor
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:30
 */

class StartViewModel @Inject constructor(
    private val interactor: StartInteractor,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val mutableListLiveData = MutableLiveData<ArrayList<StartModel>>()
    val listLiveData: LiveData<ArrayList<StartModel>>
        get() = mutableListLiveData

    private val mutableSuccessLiveData = SingleLiveEvent<LoginModel>()
    val successLiveData: LiveData<LoginModel>
        get() = mutableSuccessLiveData

    private val mutableFailureLiveData = SingleLiveEvent<String>()
    val failureLiveData: LiveData<String>
        get() = mutableFailureLiveData

    fun start() {
        viewModelScope.launch {
            val result = interactor.start()
            withContext(dispatchers.main) {
                mutableListLiveData.value = result as ArrayList<StartModel>
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            when (val result = interactor.login()) {
                is ResponseObject.Success -> {
                    withContext(dispatchers.main) {
                        mutableSuccessLiveData.value = result.data
                    }
                }

                is ResponseObject.Failure -> {
                    val error = interactor.error(statusCode = result.code)
                    withContext(dispatchers.main) {
                        mutableFailureLiveData.value = error
                    }
                }
            }
        }
    }

}