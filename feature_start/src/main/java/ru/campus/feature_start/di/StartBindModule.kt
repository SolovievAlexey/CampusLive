package ru.campus.feature_start.di

import dagger.Binds
import dagger.Module
import ru.campus.core.data.BaseResourceManager
import ru.campus.core.data.BaseUserDataStore
import ru.campus.core.data.ResourceManager
import ru.campus.core.data.UserDataStore
import ru.campus.feature_start.data.repository.BaseStartRepository
import ru.campus.feature_start.data.repository.BaseUserRepository
import ru.campus.feature_start.data.repository.StartRepository
import ru.campus.feature_start.data.repository.UserRepository
import ru.campus.feature_start.domain.BaseStartInteractor
import ru.campus.feature_start.domain.StartInteractor

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 23:21
 */

@Module
interface StartBindModule {

    @Binds
    fun bindStartRepository(startRepository: BaseStartRepository): StartRepository

    @Binds
    fun bindStartInteractor(startInteractor: BaseStartInteractor): StartInteractor

    @Binds
    fun bindUserRepository(baseUserRepository: BaseUserRepository): UserRepository

}