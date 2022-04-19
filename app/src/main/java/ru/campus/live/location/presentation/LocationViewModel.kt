package ru.campus.live.location.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.location.domain.LocationInteractor
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val dispatcher: IDispatchers,
    private val interactor: LocationInteractor
) : ViewModel() {

    private val listLiveData = MutableLiveData<List<LocationModel>>()
    val list: LiveData<List<LocationModel>>
        get() = listLiveData

    private val successLiveData = SingleLiveEvent<LocationModel>()
    val success: LiveData<LocationModel>
        get() = successLiveData

    init { search() }

    fun search(name: String? = null) {
        viewModelScope.launch(dispatcher.io) {
            val result = interactor.search(name)
            withContext(dispatcher.main) {
                listLiveData.value = result
            }
        }
    }

    fun save(item: LocationModel) {
        viewModelScope.launch(dispatcher.io) {
            interactor.save(item)
            successLiveData.postValue(item)
        }
    }

}