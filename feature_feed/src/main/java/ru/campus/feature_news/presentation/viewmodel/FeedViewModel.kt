package ru.campus.feature_news.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.ResponseObject
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.core.presentation.SingleLiveEvent
import ru.campus.feature_news.data.model.FeedModel
import ru.campus.feature_news.domain.FeedInteractor
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 20:59
 */

class FeedViewModel @Inject constructor(
    private val interactor: FeedInteractor,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val listLiveData = MutableLiveData<ArrayList<FeedModel>>()
    val list: LiveData<ArrayList<FeedModel>>
        get() = listLiveData

    private val mutableFailure = MutableLiveData<String>()
    val failure: LiveData<String>
        get() = mutableFailure

    private val mutableScrollOnPosition = SingleLiveEvent<Int>()
    val scrollOnPositionEvent: LiveData<Int>
        get() = mutableScrollOnPosition

    fun get() {
        viewModelScope.launch(dispatchers.io) {
            when (val result = interactor.get(offset = 0)) {
                is ResponseObject.Success -> {
                    val preparation = interactor.preparation(result.data)
                    val response = interactor.footer(preparation)
                    withContext(dispatchers.main) {
                        listLiveData.value = response
                    }
                }

                is ResponseObject.Failure -> {
                    val error = interactor.error(statusCode = result.code)
                    val listSize = listLiveData.value?.size ?: 0
                    if (listSize == 0) {
                        withContext(dispatchers.main) {
                            mutableFailure.value = error
                        }
                    } else {
                        withContext(dispatchers.main) {
                            listLiveData.value = list.value
                        }
                    }
                }
            }
        }
    }

    fun insert(publication: FeedModel) {
        viewModelScope.launch(dispatchers.io) {
            val list = ArrayList<FeedModel>()
            listLiveData.value?.let { list.addAll(it) }
            val result = interactor.insert(list = list, publication = publication)
            val preparation = interactor.preparation(result)
            val response = interactor.footer(preparation)
            withContext(dispatchers.main) {
                listLiveData.value = response
                mutableScrollOnPosition.value = 0
            }
        }
    }

}