package ru.campus.live.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.campus.core.di.AppDepsProvider
import ru.campus.live.R
import ru.campus.live.di.DaggerMainComponent
import ru.campus.live.di.MainComponent

class MainActivity : AppCompatActivity() {

    private val component: MainComponent by lazy {
        DaggerMainComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private var navController: NavController? = null
    private val viewModel by viewModels<MainViewModel> { component.viewModelsFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavigationHost) as NavHostFragment
        navController = navHostFragment.navController
    }

}