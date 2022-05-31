package ru.campus.feature_discussion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.domain.DiscussionInteractor

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 22.05.2022 16:58
 */

@Suppress("UNCHECKED_CAST")
class DiscussionViewModelFactory @AssistedInject constructor(
    private val interactor: DiscussionInteractor,
    private val dispatchers: CoroutineDispatchers,
    @Assisted("publication") private val publication: DiscussionModel,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DiscussionViewModel(interactor, dispatchers, publication) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("publication") publicationModel: DiscussionModel): DiscussionViewModelFactory
    }

}