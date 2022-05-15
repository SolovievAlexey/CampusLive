package ru.campus.feature_gallery.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.feature_gallery.data.GalleryDataModel
import ru.campus.feature_gallery.data.GalleryRepository
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 23:10
 */

class GalleryViewModel @Inject constructor(
    private val repository: GalleryRepository,
    private val dispatcher: CoroutineDispatchers,
) : ViewModel() {

    private val listLiveData = MutableLiveData<ArrayList<GalleryDataModel>>()
    val list: LiveData<ArrayList<GalleryDataModel>>
        get() = listLiveData

    fun execute() {
        viewModelScope.launch(dispatcher.io) {
            val model = ArrayList<GalleryDataModel>()
            listLiveData.value?.let { model.addAll(it) }
            val response = repository.execute(model.size)
            if (response.size != 0) model.addAll(response)
            withContext(dispatcher.main) {
                listLiveData.value = model
            }
        }
    }

}