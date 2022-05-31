package ru.campus.feature_discussion.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.ResponseObject
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.core.presentation.SingleLiveEvent
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.domain.DiscussionInteractor
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 16:27
 */

class DiscussionViewModel(
    private val interactor: DiscussionInteractor,
    private val dispatchers: CoroutineDispatchers,
    private val publication: DiscussionModel
) : ViewModel() {

    private val mutableListLiveData = MutableLiveData<ArrayList<DiscussionModel>>()
    val listLiveData: LiveData<ArrayList<DiscussionModel>>
        get() = mutableListLiveData

    private val mutableTitleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String>
        get() = mutableTitleLiveData

    private val mutableComplaintLiveData = SingleLiveEvent<DiscussionModel>()
    val complaintLiveData: LiveData<DiscussionModel>
        get() = mutableComplaintLiveData

    private val mutableReplyEvent = SingleLiveEvent<DiscussionModel>()
    val replyEvent: LiveData<DiscussionModel>
        get() = mutableReplyEvent

    fun get(publicationId: Int) {
        viewModelScope.launch(dispatchers.io) {
            showShimmerLayout()
            when (val result = interactor.get(publicationId = publicationId)) {
                is ResponseObject.Success -> {
                    val raw = interactor.map(model = result.data)
                    val preparation = interactor.preparation(model = raw)
                    interactor.avatar(model = preparation)
                    val response = interactor.insertPublication(preparation, publication)
                    withContext(dispatchers.main) {
                        mutableListLiveData.value = response
                    }
                }

                is ResponseObject.Failure -> {
                    interactor.avatar(null)
                    val response = interactor.insertPublication(model = ArrayList(), publication = publication)
                    withContext(dispatchers.main) {
                        mutableListLiveData.value = response
                    }
                }
            }
            title()
        }
    }

    fun insert(item: DiscussionModel) {
        viewModelScope.launch(dispatchers.io) {
            val model = mutableListLiveData.value ?: ArrayList()
            model.add(item)
            val raw = interactor.map(model = model)
            val preparation = interactor.preparation(model = raw)
            interactor.avatar(model = preparation)
            withContext(dispatchers.main) {
                mutableListLiveData.value = preparation
            }
            title()
        }
    }

    fun complaint(item: DiscussionModel) {
        mutableComplaintLiveData.value = item
    }

    fun sendComplaintDataOnServer(id: Int) {
        viewModelScope.launch(dispatchers.io) {
            interactor.complaint(id = id)
        }
    }

    fun vote(item: DiscussionModel, vote: Int) {
        viewModelScope.launch(dispatchers.io) {
            async { interactor.vote(id = item.id, vote = vote) }
            val model = ArrayList<DiscussionModel>().apply {
                listLiveData.value?.let { addAll(it) }
            }

            val response = interactor.renderVoteView(model, item, vote)
            withContext(dispatchers.main) {
                mutableListLiveData.value = response
            }
        }
    }

    fun reply(item: DiscussionModel) {
        mutableReplyEvent.value = item
    }

    private suspend fun showShimmerLayout() {
        if(listLiveData.value == null) {
            val shimmer = interactor.shimmer()
            val response = interactor.insertPublication(model = shimmer, publication = publication)
            withContext(dispatchers.main) {
                mutableListLiveData.value = response
            }
        }
    }

    private suspend fun title() {
        val count = (mutableListLiveData.value?.size ?: 0) - 1
        val title = interactor.title(count = count)
        withContext(dispatchers.main) {
            mutableTitleLiveData.value = title
        }
    }

}