package ru.campus.core.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 21:53
 */

class ActivityNavigationViewModel: ViewModel() {

    private val loginLiveData = MutableLiveData<Boolean>()
    val login: LiveData<Boolean>
        get() = loginLiveData

    fun registration() {
        loginLiveData.value = true
    }

}