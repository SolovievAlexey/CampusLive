package ru.campus.feature_discussion.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.GalleryDataModel
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UploadMediaModel
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.core.presentation.SingleLiveEvent
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionPostModel
import ru.campus.feature_discussion.domain.CreateCommentInteractor
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 19:08
 */

class CreateCommentViewModel @Inject constructor(
    private val interactor: CreateCommentInteractor,
    private val dispatchers: CoroutineDispatchers,
) : ViewModel() {

    private val mutableSuccessLiveData = SingleLiveEvent<DiscussionModel>()
    val successLiveData: LiveData<DiscussionModel>
        get() = mutableSuccessLiveData

    private val mutableFailureLiveData = SingleLiveEvent<String>()
    val failureLiveData: LiveData<String>
        get() = mutableFailureLiveData

    private val mutableMediaListLiveData = MutableLiveData<ArrayList<UploadMediaModel>>()
    val mediaListLiveData: LiveData<ArrayList<UploadMediaModel>>
        get() = mutableMediaListLiveData

    fun post(params: DiscussionPostModel) {
        viewModelScope.launch(dispatchers.io) {
            val attachment = mutableMediaListLiveData.value?.get(0)?.id ?: 0
            params.attachment = attachment
            params.icon = interactor.avatar()
            when (val result = interactor.post(params = params)) {
                is ResponseObject.Success -> {
                    withContext(dispatchers.main) {
                        mutableSuccessLiveData.value = result.data
                    }
                }
                is ResponseObject.Failure -> {
                    withContext(dispatchers.main) {
                        mutableFailureLiveData.value =
                            "Произошла какая-то ошибка! Необходимо повторить попытку ещё раз"
                    }
                }
            }
        }
    }

    fun upload(item: GalleryDataModel) {
        viewModelScope.launch(dispatchers.main) {
            viewModelScope.launch(dispatchers.io) {
                val upload = interactor.uploadMediaMap(item)
                val model = ArrayList<UploadMediaModel>()
                model.add(upload)
                withContext(dispatchers.main) {
                    mutableMediaListLiveData.value = model
                }

                val result = interactor.upload(params = item)
                val response = interactor.newUploadModel(model, result)
                withContext(dispatchers.main) {
                    mutableMediaListLiveData.value = response
                }
            }
        }
    }

}