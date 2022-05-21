package ru.campus.feature_discussion.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.ResponseObject
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.domain.DiscussionInteractor
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 16:27
 */

class DiscussionViewModel @Inject constructor(
    private val interactor: DiscussionInteractor,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val mutableListLiveData = MutableLiveData<ArrayList<DiscussionModel>>()
    val listLiveData: LiveData<ArrayList<DiscussionModel>>
        get() = mutableListLiveData

    private val mutableFailureLiveData = MutableLiveData<String>()
    val failureLiveData: LiveData<String>
        get() = mutableFailureLiveData

    private val mutableTitleLiveData = MutableLiveData<String>()
    val titleLiveData: LiveData<String>
        get() = mutableTitleLiveData

    fun get(publicationId: Int) {
        viewModelScope.launch(dispatchers.io) {
            showShimmerLayout()
            when (val result = interactor.get(publicationId = publicationId)) {
                is ResponseObject.Success -> {
                    val raw = interactor.map(model = result.data)
                    val preparation = interactor.preparation(model = raw)
                    interactor.avatar(model = preparation)
                    withContext(dispatchers.main) {
                        mutableListLiveData.value = preparation
                    }
                }

                is ResponseObject.Failure -> {
                    interactor.avatar(null)
                    val error = interactor.error(statusCode = result.code)
                    withContext(dispatchers.main) {
                        mutableListLiveData.value?.clear()
                        mutableFailureLiveData.value = error
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

    private suspend fun showShimmerLayout() {
        if(listLiveData.value == null) {
            val shimmer = interactor.shimmer()
            withContext(dispatchers.main) {
                mutableListLiveData.value = shimmer
            }
        }
    }

    private suspend fun title() {
        val count = mutableListLiveData.value?.size ?: 0
        Log.d("MyLog", "count = $count")
        val title = interactor.title(count = count)
        withContext(dispatchers.main) {
            mutableTitleLiveData.value = title
        }
    }

}