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
import ru.campus.feature_news.data.model.StatusModel
import ru.campus.feature_news.domain.FeedInteractor
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 20:59
 */

class FeedViewModel @Inject constructor(
    private val interactor: FeedInteractor,
    private val dispatchers: CoroutineDispatchers,
) : ViewModel() {

    private var isDataCash = false
    private val listLiveData = MutableLiveData<ArrayList<FeedModel>>()
    val list: LiveData<ArrayList<FeedModel>>
        get() = listLiveData

    private val mutableFailure = MutableLiveData<String>()
    val failure: LiveData<String>
        get() = mutableFailure

    private val mutableScrollOnPosition = SingleLiveEvent<Int>()
    val scrollOnPositionEvent: LiveData<Int>
        get() = mutableScrollOnPosition

    private val mutableComplaintLiveData = SingleLiveEvent<FeedModel>()
    val complaintLiveData: LiveData<FeedModel>
        get() = mutableComplaintLiveData

    private val mutableDiscussionLiveData = SingleLiveEvent<FeedModel>()
    val discussionLiveData: LiveData<FeedModel>
        get() = mutableDiscussionLiveData

    private val mutableStatusLiveData = MutableLiveData<StatusModel>()
    val statusLiveData: LiveData<StatusModel>
        get() = mutableStatusLiveData

    init {
        status()
        getCash()
    }

    fun get(refresh: Boolean = false) {
        if(refresh) status()
        viewModelScope.launch(dispatchers.io) {
            when (val result = interactor.get(offset = 0)) {
                is ResponseObject.Success -> {
                    val model = ArrayList<FeedModel>()
                    model.addAll(result.data)
                    val preparation = interactor.preparation(model)
                    val response = interactor.footer(preparation)
                    withContext(dispatchers.main) {
                        listLiveData.value = response
                    }

                    interactor.save(list = result.data)
                }

                is ResponseObject.Failure -> {
                    val error = interactor.error(statusCode = result.code)
                    val listSize = listLiveData.value?.size ?: 0
                    if (listSize == 0 && !isDataCash) {
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

    fun complaint(item: FeedModel) {
        mutableComplaintLiveData.value = item
    }

    fun sendComplaintDataOnServer(item: FeedModel) {
        viewModelScope.launch(dispatchers.io) {
            val model = listLiveData.value
            val result = interactor.delete(item = item, model = model!!)
            withContext(dispatchers.main) {
                listLiveData.value = result
            }
            interactor.sendComplaintDataOnServer(id = item.id)
        }
    }

    fun vote(item: FeedModel, vote: Int) {
        viewModelScope.launch(dispatchers.io) {
            val model = listLiveData.value
            val result = interactor.vote(item = item, model = model!!, vote = vote)
            withContext(dispatchers.main) {
                listLiveData.value = result
            }
            interactor.sendVoteDataOnServer(id = item.id, vote = vote)
        }
    }

    fun discussion(item: FeedModel) {
        mutableDiscussionLiveData.value = item
    }

    private fun status() {
        viewModelScope.launch(dispatchers.io) {
            val status = interactor.status()
            withContext(dispatchers.main) {
                mutableStatusLiveData.value = status
            }
            val refresh = interactor.statusRefresh()
            withContext(dispatchers.main) {
                mutableStatusLiveData.value = refresh
            }
        }
    }


    private fun getCash() {
        viewModelScope.launch(dispatchers.io) {
            val result = interactor.cache()
            if (result.size != 0) {
                isDataCash = true
                val preparation = interactor.preparation(result)
                val response = interactor.footer(preparation)
                withContext(dispatchers.main) {
                    listLiveData.value = response
                }
            }
            get()
        }
    }

}