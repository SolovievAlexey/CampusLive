package ru.campus.live.discussion.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.discussion.data.model.DiscussionModel
import ru.campus.live.discussion.domain.DiscussionInteractor
import ru.campus.live.ribbon.data.model.RibbonModel
import javax.inject.Inject

class DiscussionViewModel @Inject constructor(
    private val dispatcher: IDispatchers,
    private val interactor: DiscussionInteractor,
) : ViewModel() {

    private var publication: DiscussionModel? = null

    private val listLiveData = MutableLiveData<ArrayList<DiscussionModel>>()
    val list: LiveData<ArrayList<DiscussionModel>>
        get() = listLiveData

    private val titleLiveData = MutableLiveData<String>()
    val title: LiveData<String>
        get() = titleLiveData

    private val complaintLiveData = SingleLiveEvent<DiscussionModel>()
    val complaintEvent: LiveData<DiscussionModel>
        get() = complaintLiveData

    init {
        listLiveData.observeForever {
            title()
            refreshUserAvatar()
        }
    }

    fun set(params: RibbonModel) {
        publication = interactor.map(params)
    }

    fun get(comments: Int = 0) {
        viewModelScope.launch(dispatcher.io) {
            if (listLiveData.value == null && comments != 0) shimmer()
            when (val result = interactor.get(publication!!.id)) {
                is ResponseObject.Success -> {
                    val preparation = interactor.preparation(result.data)
                    val response = interactor.header(publication!!, preparation)
                    withContext(dispatcher.main) {
                        listLiveData.value = response
                    }
                }
                is ResponseObject.Failure -> {
                    val model = interactor.error()
                    val response = interactor.header(publication!!, model)
                    withContext(dispatcher.main) {
                        listLiveData.value = response
                    }
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(dispatcher.io) {
            val publication = listLiveData.value?.get(0)!!
            when (val result = interactor.get(publication.id)) {
                is ResponseObject.Success -> {
                    val preparationList = interactor.preparation(result.data)
                    val response = interactor.header(publication, preparationList)
                    withContext(dispatcher.main) {
                        listLiveData.value = response
                    }
                }
                is ResponseObject.Failure -> {
                    withContext(dispatcher.main) {
                        listLiveData.value = listLiveData.value
                    }
                }
            }
        }
    }

    fun insert(item: DiscussionModel) {
        viewModelScope.launch(dispatcher.io) {
            val result = interactor.insert(item, listLiveData.value!!)
            val list = interactor.preparation(result)
            val response = interactor.header(publication!!, list)
            withContext(dispatcher.main) {
                listLiveData.value = response
            }
        }
    }

    fun title() {
        viewModelScope.launch(dispatcher.io) {
            val count = interactor.count(listLiveData.value!!)
            val title = interactor.title(count)
            withContext(dispatcher.main) {
                titleLiveData.value = title
            }
        }
    }

    fun vote(params: VoteModel) {
        viewModelScope.launch(dispatcher.io) {
            val result = interactor.renderVoteView(listLiveData.value!!, params)
            withContext(dispatcher.main) {
                listLiveData.value = result
            }
            interactor.vote(params)
        }
    }

    fun complaint(item: DiscussionModel) {
        complaintLiveData.value = item
        viewModelScope.launch(dispatcher.io) {
            interactor.complaint(item.id)
        }
    }

    private suspend fun shimmer() {
        val model = interactor.shimmer()
        val response = interactor.header(publication!!, model)
        withContext(dispatcher.main) {
            listLiveData.value = response
        }
    }

    private fun refreshUserAvatar() {
        viewModelScope.launch(dispatcher.io) {
            if (listLiveData.value != null) interactor.refreshUserAvatar(listLiveData.value!!)
        }
    }

    override fun onCleared() {
        listLiveData.removeObserver { }
        super.onCleared()
    }

}