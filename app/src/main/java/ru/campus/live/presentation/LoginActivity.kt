package ru.campus.live.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import ru.campus.core.navigation.LoginNavigationViewModel
import ru.campus.live.R

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 21:22
 */

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginNavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.loginNavigationHost) as NavHostFragment
        val navController = navHostFragment.navController
        viewModel.registration.observe(this, registration())
    }

    private fun registration() = Observer<Boolean> {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}