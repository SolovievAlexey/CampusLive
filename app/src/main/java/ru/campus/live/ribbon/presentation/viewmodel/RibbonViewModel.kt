package ru.campus.live.ribbon.presentation.viewmodel

import android.util.Log
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
    private val interactor: RibbonInteractor,
) : ViewModel() {

    private var isLazyDownloadFeed = false
    private var isDataCash = false

    private val listLiveData = MutableLiveData<ArrayList<RibbonModel>>()
    val list: LiveData<ArrayList<RibbonModel>>
        get() = listLiveData

    private val startDiscussionEvent = SingleLiveEvent<RibbonModel>()
    val startDiscussion: LiveData<RibbonModel>
        get() = startDiscussionEvent

    private val complaintLiveData = SingleLiveEvent<RibbonModel>()
    val complaintEvent: LiveData<RibbonModel>
        get() = complaintLiveData

    init {
        getCash()
    }

    private fun getCash() {
        viewModelScope.launch(dispatchers.io) {
            val result = interactor.getCash()
            if(result.size > 1) isDataCash = true
            withContext(dispatchers.main) {
                listLiveData.value = result
            }
            get(refresh = true)
        }
    }

    fun get(refresh: Boolean = false) {
        if (!isLazyDownloadFeed && !refresh) return
        viewModelScope.launch(dispatchers.io) {
            val model = ArrayList<RibbonModel>()
            listLiveData.value?.let { model.addAll(it) }

            val offset = interactor.getOffset(refresh, model)
            val result = interactor.get(offset = offset, model = model)

            val error = interactor.isErrorView(model = result)
            if(offset == 0 && !error) interactor.postCash(result)
            isLazyDownloadFeed = interactor.lazyDownloadFeed(result)

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