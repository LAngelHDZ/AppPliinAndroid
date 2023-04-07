package com.example.pliin.navigation

sealed class AppScreen(val route:String){
    object MainScreen: AppScreen("MainScreen")
    object LoginScreen: AppScreen("LoginScreen")
    object AppMainScreen: AppScreen("MainAppScreen")
    object RegisterDeliveryScreen: AppScreen("RegisterDeliveryScreen")
}