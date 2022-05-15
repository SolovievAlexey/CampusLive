package ru.campus.live.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        component.inject(this)
        if (userDataSource.token().isEmpty() || userDataSource.location() == 0) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.mainNavigationHost) as NavHostFragment
            val navController = navHostFragment.navController
        }
    }

}