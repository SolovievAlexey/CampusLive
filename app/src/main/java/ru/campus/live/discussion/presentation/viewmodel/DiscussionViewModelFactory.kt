package ru.campus.live.discussion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.discussion.data.model.DiscussionModel
import ru.campus.live.discussion.domain.DiscussionInteractor

@Suppress("UNCHECKED_CAST")
class DiscussionViewModelFactory @AssistedInject constructor(
    private val dispatcher: IDispatchers,
    private val interactor: DiscussionInteractor,
    @Assisted("publication") private val publication: DiscussionModel,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DiscussionViewModel(dispatcher, interactor, publication) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("publication") publicationModel: DiscussionModel): DiscussionViewModelFactory
    }

}