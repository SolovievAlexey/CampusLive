package ru.campus.live.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(context: Context) : ViewModel() {

    /*
    private val _authEvent = SingleLiveEvent<Boolean>()
    fun authEvent() = _authEvent

    init {
        isAuth()
    }

    private fun isAuth() {
        viewModelScope.launch(dispatcher.io) {
            val auth = userDataStore.isAuth()
            if (auth) {
                withContext(dispatcher.main) {
                    _authEvent.value = true
                }
            }
        }
    }

     */

}