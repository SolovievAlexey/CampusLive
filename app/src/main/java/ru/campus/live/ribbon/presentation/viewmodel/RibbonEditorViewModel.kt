package ru.campus.live.ribbon.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.data.model.ErrorModel
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.core.domain.UploadMediaInteractor
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.gallery.data.model.GalleryDataModel
import ru.campus.live.gallery.data.model.UploadMediaObject
import ru.campus.live.ribbon.data.model.RibbonPostModel
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.domain.RibbonEditorInteractor
import javax.inject.Inject

class RibbonEditorViewModel @Inject constructor(
    private val dispatcher: IDispatchers,
    private val interactor: RibbonEditorInteractor,
    private val uploadMediaInteractor: UploadMediaInteractor,
) : ViewModel() {

    private val successLiveData = SingleLiveEvent<RibbonModel>()
    val success: LiveData<RibbonModel>
        get() = successLiveData

    private val failureLiveData = SingleLiveEvent<ErrorModel>()
    val failure: LiveData<ErrorModel>
        get() = failureLiveData

    private val uploadLiveData = MutableLiveData<ArrayList<UploadMediaObject>>()
    val upload: LiveData<ArrayList<UploadMediaObject>>
        get() = uploadLiveData

    @SuppressLint("NullSafeMutableLiveData")
    fun post(params: RibbonPostModel) {
        viewModelScope.launch(dispatcher.io) {
            uploadLiveData.value?.let { model -> params.attachment = model[0].id }
            when (val result = interactor.post(params)) {
                is ResponseObject.Success -> successLiveData.postValue(result.data)
                is ResponseObject.Failure -> failureLiveData.postValue(result.error)
            }
        }
    }

    fun upload(params: GalleryDataModel) {
        viewModelScope.launch(dispatcher.io) {
            val model = ArrayList<UploadMediaObject>()
            model.add(uploadMediaInteractor.addList(params = params))
            uploadLiveData.postValue(model)
            val result = uploadMediaInteractor.upload(params = params)
            model.clear()
            model.add(result)
            withContext(dispatcher.main) {
                uploadLiveData.value = model
            }
        }
    }

    fun clearUploadList() {
        viewModelScope.launch(dispatcher.io) {
            val result = uploadMediaInteractor.mediaRemove()
            uploadLiveData.postValue(result)
        }
    }

}