package ru.campus.live.start.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.campus.live.core.data.model.ErrorModel
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.start.data.model.LoginModel
import ru.campus.live.start.data.model.StartModel
import ru.campus.live.start.domain.IStartInteractor
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val dispatchers: IDispatchers,
    private val interactor: IStartInteractor
) : ViewModel() {

    private val liveDataList = MutableLiveData<ArrayList<StartModel>>()
    val list: LiveData<ArrayList<StartModel>>
        get() = liveDataList

    private val successLiveData = SingleLiveEvent<LoginModel>()
    val success: LiveData<LoginModel>
        get() = successLiveData

    private val failureLiveData = SingleLiveEvent<ErrorModel>()
    val failure: LiveData<ErrorModel>
        get() = failureLiveData

    init { start() }

    fun start() {
        viewModelScope.launch(dispatchers.io) {
            val result = interactor.start()
            liveDataList.postValue(result)
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun login() {
        viewModelScope.launch(dispatchers.io) {
            when (val result = interactor.login()) {
                is ResponseObject.Success -> successLiveData.postValue(result.data)
                is ResponseObject.Failure -> failureLiveData.postValue(result.error)
            }
        }
    }

}
