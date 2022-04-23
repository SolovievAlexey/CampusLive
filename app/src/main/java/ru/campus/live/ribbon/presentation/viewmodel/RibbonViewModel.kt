package ru.campus.live.ribbon.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.discussion.data.model.DiscussionModel
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.domain.RibbonInteractor
import javax.inject.Inject

class RibbonViewModel @Inject constructor(
    private val dispatchers: IDispatchers,
    private val interactor: RibbonInteractor
) : ViewModel() {

    private var isLazyDownloadFeed = false
    private val listLiveData = MutableLiveData<ArrayList<RibbonModel>>()
    val list: LiveData<ArrayList<RibbonModel>>
        get() = listLiveData

    private val startDiscussionEvent = SingleLiveEvent<RibbonModel>()
    val startDiscussion: LiveData<RibbonModel>
        get() = startDiscussionEvent

    private val complaintLiveData = SingleLiveEvent<RibbonModel>()
    val complaintEvent: LiveData<RibbonModel>
        get() = complaintLiveData

    init { getCash() }

    private fun getCash() {
        viewModelScope.launch(dispatchers.io) {
            val result = interactor.cash()
            withContext(dispatchers.main) { listLiveData.value = result }
            get(refresh = true)
        }
    }

    fun get(refresh: Boolean = false) {
        if (!isLazyDownloadFeed && !refresh) return
        viewModelScope.launch(dispatchers.io) {
            val oldModel = interactor.getModel(list.value)
            val offset = interactor.getOffset(refresh, oldModel)
            val result = interactor.get(offset)
            val response = interactor.render(oldModel, result, offset)
            withContext(dispatchers.main) { listLiveData.value = response }
            isLazyDownloadFeed = interactor.lazyDownloadFeed(statusCode = result.statusCode)
        }
    }

    fun vote(params: VoteModel) {
        viewModelScope.launch(dispatchers.io) {
            async { interactor.sendVoteOnServer(params) }
            val result = interactor.vote(listLiveData.value!!, params)
            withContext(dispatchers.main) {
                listLiveData.value = result
            }
        }
    }

    fun complaint(item: RibbonModel) {
        complaintLiveData.value = item
    }

    fun sendComplaintOnServer(item: RibbonModel) {
        viewModelScope.launch(dispatchers.io) {
            async { interactor.sendComplaintOnServer(item) }
            val result = interactor.remove(listLiveData.value!!, id = item.id)
            withContext(dispatchers.main) {
                listLiveData.value = result
            }
        }
    }

    fun startDiscussion(item: RibbonModel) {
        startDiscussionEvent.value = item
    }

    fun convertToDiscussionModel(item: RibbonModel): DiscussionModel {
        return interactor.convertToDiscussionModel(item)
    }

}