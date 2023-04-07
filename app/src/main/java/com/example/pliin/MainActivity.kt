package com.example.pliin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pliin.apppliin.ui.appmain.MainAppScreen
import com.example.pliin.apppliin.ui.login.LoginScreen
import com.example.pliin.apppliin.ui.login.LoginViewModel
import com.example.pliin.apppliin.ui.main.MainScreen
import com.example.pliin.apppliin.ui.registerdelivery.RDViewModel
import com.example.pliin.apppliin.ui.registerdelivery.RegisterDeliveryScreen
import com.example.pliin.navigation.AppScreen
import com.example.pliin.ui.theme.PliinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val rdViewModel: RDViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PliinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DataInit()
                    AppNavigation()
                }
            }
        }
    }
    @Composable
    fun AppNavigation() {
        val navigationController = rememberNavController()
        NavHost(
            navController = navigationController,
            startDestination = AppScreen.MainScreen.route
        ) {
            composable(route = AppScreen.MainScreen.route) {
                MainScreen(navigationController)
            }
            composable(route = AppScreen.LoginScreen.route) {
                LoginScreen(loginViewModel = loginViewModel, navigationController)
            }
            composable(route = AppScreen.AppMainScreen.route) {
                MainAppScreen(navigationController)
            }
            composable(route = AppScreen.RegisterDeliveryScreen.route) {
                RegisterDeliveryScreen(rdViewModel = rdViewModel, navigationController)
            }

        }
    }
    private fun DataInit(){
        loginViewModel.onCreate()
        loginViewModel.onGuideCreate()
    }
}

