package ru.campus.feature_support

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 22.05.2022 21:47
 */

class RulesViewModel : ViewModel() {

    private val mutableLiveData = MutableLiveData<ArrayList<String>>()
    val liveData: LiveData<ArrayList<String>>
        get() = mutableLiveData

    fun get() {
        val result = RulesDataSource().get()
        mutableLiveData.value = result
    }

}