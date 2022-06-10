package ru.campus.feature_start.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.ResponseObject
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
) : ViewModel() {

    private val listLiveData = MutableLiveData<ArrayList<StartModel>>()
    val list: LiveData<ArrayList<StartModel>>
        get() = listLiveData

    private val successLiveData = SingleLiveEvent<LoginModel>()
    val success: LiveData<LoginModel>
        get() = successLiveData

    private val failureLiveData = SingleLiveEvent<String>()
    val failure: LiveData<String>
        get() = failureLiveData

    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = interactor.start()
            withContext(Dispatchers.Main) {
                listLiveData.value = result as ArrayList<StartModel>
            }
        }
    }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = interactor.login()) {
                is ResponseObject.Success -> {
                    withContext(Dispatchers.Main) {
                        successLiveData.value = result.data
                    }
                }

                is ResponseObject.Failure -> {
                    val error = interactor.error(statusCode = result.code)
                    withContext(Dispatchers.Main) {
                        failureLiveData.value = error
                    }
                }
            }
        }
    }

}