package ru.campus.feature_news.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.core.data.ResponseObject
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.feature_news.data.FeedModel
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

    private val listLiveData = MutableLiveData<ArrayList<FeedModel>>()
    val list: LiveData<ArrayList<FeedModel>>
        get() = listLiveData

    fun get() {
        viewModelScope.launch(dispatchers.io) {
            when (val result = interactor.get(offset = 0)) {
                is ResponseObject.Success -> {
                    val response = interactor.preparation(result.data)
                    withContext(dispatchers.main) {
                        listLiveData.value = response
                    }
                }
                is ResponseObject.Failure -> {

                }
            }
        }
    }

    fun insert(publication : FeedModel) {
        viewModelScope.launch(dispatchers.io) {
            val list = ArrayList<FeedModel>()
            listLiveData.value?.let { list.addAll(it) }
            val result = interactor.insert(list = list, publication = publication)
            val response = interactor.preparation(result)
            withContext(dispatchers.main) {
                listLiveData.value = response
            }
        }
    }

}