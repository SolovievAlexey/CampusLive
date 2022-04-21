package ru.campus.live.discussion.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.discussion.data.model.DiscussionModel
import ru.campus.live.discussion.domain.DiscussionInteractor
import javax.inject.Inject

class DiscussionViewModel @Inject constructor(
    private val dispatcher: IDispatchers,
    private val interactor: DiscussionInteractor,
    private val publication: DiscussionModel
) : ViewModel() {

    private val listLiveData = MutableLiveData<ArrayList<DiscussionModel>>()
    val list: LiveData<ArrayList<DiscussionModel>>
        get() = listLiveData

    private val titleLiveData = MutableLiveData<String>()
    val title: LiveData<String>
        get() = titleLiveData

    private val complaintLiveData = SingleLiveEvent<DiscussionModel>()
    val complaintEvent: LiveData<DiscussionModel>
        get() = complaintLiveData

    fun get() {
        viewModelScope.launch(dispatcher.io) {
            val list = when (val result = interactor.get(publication = publication.id)) {
                is ResponseObject.Success -> interactor.preparation(result.data)
                is ResponseObject.Failure -> interactor.getErrorView()
            }
            val response =
                interactor.adPublicationView(publication = publication, model = list)
            listLiveData.postValue(response)
        }
    }

    fun insert(item: DiscussionModel) {
        viewModelScope.launch(dispatcher.io) {
            val model = ArrayList<DiscussionModel>().apply {
                listLiveData.value?.let { addAll(it) }
            }
            val result =  interactor.insert(item, model)
            val list = interactor.preparation(result)
            val response = interactor.adPublicationView(publication = publication, model = list)
            withContext(dispatcher.main) {
                listLiveData.value = response
            }
        }
    }

    fun getTitle() {
        viewModelScope.launch(dispatcher.io) {
            val model = ArrayList<DiscussionModel>().apply {
                listLiveData.value?.let { addAll(it) }
            }
            val count = interactor.count(model)
            val title = interactor.title(count)
            withContext(dispatcher.main) {
                titleLiveData.value = title
            }
        }
    }

    fun vote(params: VoteModel) {
        viewModelScope.launch(dispatcher.io) {
            async { interactor.vote(params) }
            val model = ArrayList<DiscussionModel>().apply {
                listLiveData.value?.let { addAll(it) }
            }
            val response = interactor.renderVoteView(model, params)
            withContext(dispatcher.main) {
                listLiveData.value = response
            }
        }
    }

    fun complaint(item: DiscussionModel) {
        complaintLiveData.value = item
        viewModelScope.launch(dispatcher.io) {
            interactor.complaint(item.id)
        }
    }

    fun refreshUserAvatar() {
        viewModelScope.launch(dispatcher.io) {
            val model = ArrayList<DiscussionModel>().apply {
                listLiveData.value?.let { addAll(it) }
            }
            interactor.refreshUserAvatar(model)
        }
    }

    private suspend fun getShimmerLayout() {
        val model = interactor.shimmer()
        val response = interactor.adPublicationView(publication = publication, model = model)
        withContext(dispatcher.main) {
            listLiveData.value = response
        }
    }

}