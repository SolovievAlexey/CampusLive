package ru.campus.live.gallery.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.gallery.data.model.GalleryDataModel
import ru.campus.live.gallery.data.repository.IGalleryRepository
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val repository: IGalleryRepository
) : ViewModel() {

    private val listLiveData = MutableLiveData<ArrayList<GalleryDataModel>>()
    val list: LiveData<ArrayList<GalleryDataModel>>
        get() = listLiveData

    fun execute() {
        viewModelScope.launch(Dispatchers.IO) {
            val model = ArrayList<GalleryDataModel>()
            listLiveData.value?.let { model.addAll(it) }
            val response = repository.get(model.size)
            if (response.size != 0) model.addAll(response)
            withContext(Dispatchers.Main) {
                listLiveData.value = model
            }
        }
    }

}