package ru.campus.feature_location.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.ResponseObject
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.feature_location.data.LocationModel
import ru.campus.feature_location.domain.LocationInteractor
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 17:03
 */

class LocationViewModel @Inject constructor(
    private val interactor: LocationInteractor,
    private val dispatchers: CoroutineDispatchers,
) : ViewModel() {

    private val liveData = MutableLiveData<ArrayList<LocationModel>>()
    val list: LiveData<ArrayList<LocationModel>>
        get() = liveData

    fun location(name: String?) {
        viewModelScope.launch(dispatchers.io) {
            when (val result = interactor.get(name = name)) {
                is ResponseObject.Success<ArrayList<LocationModel>> -> {
                    withContext(dispatchers.main) {
                        liveData.value = result.data
                    }
                }
                is ResponseObject.Failure -> {
                    withContext(dispatchers.main) {
                        liveData.value = ArrayList()
                    }
                }
            }
        }
    }

}