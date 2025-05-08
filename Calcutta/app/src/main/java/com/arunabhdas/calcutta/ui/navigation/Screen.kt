package com.arunabhdas.calcutta.ui.navigation

sealed class Screen(val route: String) {
    object LandingScreen: Screen(route = "landing_screen")
}