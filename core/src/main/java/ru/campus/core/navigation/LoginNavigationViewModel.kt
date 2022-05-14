package ru.campus.core.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 21:53
 */

class LoginNavigationViewModel: ViewModel() {

    private val registrationLiveData = MutableLiveData<Boolean>()
    val registration: LiveData<Boolean>
        get() = registrationLiveData

    fun setRegistration(params: Boolean) {
        registrationLiveData.value = params
    }

}