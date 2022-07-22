package ru.campus.feature_location.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.ResponseObject
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.feature_location.data.model.LocationModel
import ru.campus.feature_location.data.repository.LocationRepository
import javax.inject.Inject

class BesideViewModel @Inject constructor(
    private val repository: LocationRepository,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val mutableLiveData = MutableLiveData<ArrayList<LocationModel>>()
    val liveData: LiveData<ArrayList<LocationModel>>
        get() = mutableLiveData

    fun get(latitude: Double, longitude: Double) {
        viewModelScope.launch(dispatchers.io) {
            when (val result = repository.beside(latitude, longitude)) {
                is ResponseObject.Success -> {
                    withContext(Dispatchers.Main) {
                        mutableLiveData.value = result.data
                    }
                }
                is ResponseObject.Failure -> {
                    val model = ArrayList<LocationModel>()
                    withContext(Dispatchers.Main) {
                        mutableLiveData.value = model
                    }
                }
            }
        }
    }

}