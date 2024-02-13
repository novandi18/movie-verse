package com.novandi.movieverse.presentation.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.novandi.movieverse.presentation.navigation.MainNavigation
import com.novandi.movieverse.presentation.navigation.WelcomeNavigation
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isWelcomed: Boolean = false
    private var keepSplashOnScreen = true
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashOnScreen = false
        }, 500L)

        lifecycleScope.launch {
            viewModel.isWelcomed.observe(this@MainActivity) { isWelcomed = it!! }

            setContent {
                MovieVerseTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MovieVerseApp(
                            startedDestination = if (isWelcomed)
                                MainNavigation.MAIN_ROUTE else WelcomeNavigation.WELCOME_ROUTE
                        )
                    }
                }
            }
        }
    }
}