package ru.campus.live.core.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.data.source.UserDataSource
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val dispatcher: IDispatchers,
    private val userDataSource: UserDataSource
) : ViewModel() {

    private val _authEvent = SingleLiveEvent<Boolean>()
    fun authEvent() = _authEvent
    init { isAuth() }

    private fun isAuth() {
        viewModelScope.launch(dispatcher.io) {
            val auth = userDataSource.isAuth()
            if (auth) {
                withContext(dispatcher.main) {
                    _authEvent.value = true
                }
            }
        }
    }

}