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

    private val listLiveData = MutableLiveData<List<StartModel>>()
    val list: LiveData<List<StartModel>>
        get() = listLiveData

    private val successLiveData = SingleLiveEvent<LoginModel>()
    val success: LiveData<LoginModel>
        get() = successLiveData

    private val failureLiveData = SingleLiveEvent<Int>()
    val failure: LiveData<Int>
        get() = failureLiveData

    fun start() {
        viewModelScope.launch(dispatchers.io) {
            val result = interactor.start()
            withContext(dispatchers.main) {
                listLiveData.value = result
            }
        }
    }

    fun login() {
        viewModelScope.launch(dispatchers.io) {
            when(val result = interactor.login()) {
                is ResponseObject.Success -> {
                    withContext(dispatchers.main) {
                        successLiveData.value = result.data
                    }
                }
                is ResponseObject.Failure -> {
                    withContext(dispatchers.main) {
                        failureLiveData.value = result.code
                    }
                }
            }
        }
    }

}