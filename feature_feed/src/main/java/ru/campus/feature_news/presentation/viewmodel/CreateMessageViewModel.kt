package ru.campus.feature_news.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.AlertMessageModel
import ru.campus.core.data.GalleryDataModel
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UploadMediaModel
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.core.presentation.SingleLiveEvent
import ru.campus.feature_news.data.FeedModel
import ru.campus.feature_news.data.FeedPostModel
import ru.campus.feature_news.domain.CreateMessageInteractor
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 21:50
 */

class CreateMessageViewModel @Inject constructor(
    private val interactor: CreateMessageInteractor,
    private val dispatchers: CoroutineDispatchers,
) : ViewModel() {

    private val mutableSuccess = SingleLiveEvent<FeedModel>()
    val success: LiveData<FeedModel>
        get() = mutableSuccess

    private val mutableFailure = SingleLiveEvent<AlertMessageModel>()
    val failure: LiveData<AlertMessageModel>
        get() = mutableFailure

    private val mutableMediaList = MutableLiveData<ArrayList<UploadMediaModel>>()
    val mediaList: LiveData<ArrayList<UploadMediaModel>>
        get() = mutableMediaList

    fun post(message: String) {
        viewModelScope.launch(dispatchers.io) {
            val attachment = mutableMediaList.value?.get(0)?.id ?: 0
            val post = FeedPostModel(message = message, attachment = attachment)
            when (val result = interactor.post(post)) {
                is ResponseObject.Success -> {
                    withContext(dispatchers.main) {
                        mutableSuccess.value = result.data
                    }
                }
                is ResponseObject.Failure -> {
                    val response = AlertMessageModel(message = "", icon = 1)
                    withContext(dispatchers.main) {
                        mutableFailure.value = response
                    }
                }
            }
        }
    }

    fun upload(item: GalleryDataModel) {
        viewModelScope.launch(dispatchers.io) {
            val upload = interactor.uploadMediaMap(item)
            val model = ArrayList<UploadMediaModel>()
            model.add(upload)
            withContext(dispatchers.main) {
                mutableMediaList.value = model
            }

            val result = interactor.upload(params = item)
            val response = interactor.newUploadModel(model, result)
            withContext(dispatchers.main) {
                mutableMediaList.value = response
            }
        }
    }

    fun uploadListClear() {
        mutableMediaList.value = ArrayList()
    }


}