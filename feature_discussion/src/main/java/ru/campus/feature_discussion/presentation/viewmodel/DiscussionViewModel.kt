package ru.campus.feature_discussion.presentation.viewmodel

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

    fun get(publicationId: Int) {
        viewModelScope.launch(dispatchers.io) {
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
                    withContext(dispatchers.main) {
                        mutableFailureLiveData.value = "Произошла какая-то ошибка"
                    }
                }
            }
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
        }
    }

}