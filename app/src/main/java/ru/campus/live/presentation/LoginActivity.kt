package ru.campus.live.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import ru.campus.live.R

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 21:22
 */

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.loginNavigationHost) as NavHostFragment
        //val navController = navHostFragment.navController
    }

}