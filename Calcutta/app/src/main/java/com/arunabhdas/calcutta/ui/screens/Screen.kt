package com.arunabhdas.calcutta.ui.screens

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object LandingScreen: Screen
    @Serializable
    data object HomeScreen: Screen
    @Serializable
    data class FollowersScreen(val username: String): Screen
    @Serializable
    data object TodolistScreen: Screen
    @Serializable
    data class FollowingScreen(val username: String): Screen
}