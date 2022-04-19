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
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.domain.RibbonInteractor
import javax.inject.Inject

class RibbonViewModel @Inject constructor(
    private val dispatchers: IDispatchers,
    private val interactor: RibbonInteractor
) : ViewModel() {

    private val listLiveData = MutableLiveData<ArrayList<RibbonModel>>()
    val list: LiveData<ArrayList<RibbonModel>>
        get() = listLiveData

    private val startDiscussionEvent = SingleLiveEvent<RibbonModel>()
    val startDiscussion: LiveData<RibbonModel>
        get() = startDiscussionEvent

    private val complaintEvent = SingleLiveEvent<RibbonModel>()
    val complaint: LiveData<RibbonModel>
        get() = complaintEvent

    init { get() }

    fun get(refresh: Boolean = false) {
        viewModelScope.launch(dispatchers.io) {
            val model = ArrayList<RibbonModel>()
            if (!refresh) list.value?.let { model.addAll(it) }
            val offset = interactor.getOffset(model)
            val result = interactor.get(offset = offset)
            val response = interactor.map(result)
            withContext(dispatchers.main) {
                listLiveData.value = response
            }
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
        complaintEvent.value = item
    }

    fun sendComplaintOnServer(item: RibbonModel) {
        viewModelScope.launch(dispatchers.io) {
            async { interactor.complaintSendOnServer(item) }
            val result = interactor.complaint(listLiveData.value!!, id = item.id)
            withContext(dispatchers.main) {
                listLiveData.value = result
            }
        }
    }

    fun startDiscussion(item: RibbonModel) {
        startDiscussionEvent.value = item
    }

}