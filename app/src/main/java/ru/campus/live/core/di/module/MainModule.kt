package ru.campus.live.core.di.module

import dagger.Module
import ru.campus.live.core.di.module.viewmodel.MainVModule

@Module(includes = [MainVModule::class])
class MainModule