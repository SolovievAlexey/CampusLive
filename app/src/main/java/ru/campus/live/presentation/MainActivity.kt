package ru.campus.live.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ru.campus.core.data.UserDataStore
import ru.campus.core.di.AppDepsProvider
import ru.campus.live.R
import ru.campus.live.databinding.ActivityMainBinding
import ru.campus.live.di.DaggerMainComponent
import ru.campus.live.di.MainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val component: MainComponent by lazy {
        DaggerMainComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    @Inject
    lateinit var userDataSource: UserDataStore
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        component.inject(this)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavigationHost) as NavHostFragment
        val navController = navHostFragment.navController

        if (userDataSource.token().isNotEmpty() || userDataSource.location() != 0) {
            val navGraphResourceId = resources.getIdentifier(
                "main_navigation", "id", packageName)
            val options = NavOptions.Builder().setPopUpTo(
                destinationId = navGraphResourceId, inclusive = true).build()
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://ru.campus.live/feedFragment".toUri())
                .build()
            navController.navigate(request, options)
        }
    }

}