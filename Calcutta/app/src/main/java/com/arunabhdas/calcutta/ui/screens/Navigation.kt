package com.arunabhdas.calcutta.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.arunabhdas.calcutta.presentation.FolloweesViewModel
import com.arunabhdas.calcutta.presentation.FollowersViewModel
import com.arunabhdas.calcutta.presentation.UserViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    // Get ViewModel using Koin compose extension
    val userViewModel: UserViewModel = koinViewModel()
    val followersViewModel: FollowersViewModel = koinViewModel()
    val followeesViewModel: FolloweesViewModel = koinViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.LandingScreen
    ) {
        composable<Screen.LandingScreen> {
            LandingScreen(
                onGoNext = {
                    navController.navigate(Screen.HomeScreen)
                },
                onGoTodoList = {
                    navController.navigate(Screen.TodolistScreen)
                }
            )
        }

        composable<Screen.TodolistScreen> {
            TodolistScreen(
                viewModel = userViewModel,
                onButtonClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.HomeScreen> {
            HomeScreen(
                viewModel = userViewModel,
                onFollowersClick = { username ->
                    navController.navigate(Screen.FollowersScreen(username))
                },
                onFolloweesClick = { username ->
                    navController.navigate(Screen.FollowingScreen(username))
                },
                onButtonClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.FollowersScreen> { backStackEntry ->
            val followersScreen = backStackEntry.toRoute<Screen.FollowersScreen>()
            FollowersScreen(
                username = followersScreen.username,
                viewModel = followersViewModel,
                onButtonClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.FollowingScreen> { backStackEntry ->
            val followeesScreen = backStackEntry.toRoute<Screen.FollowingScreen>()
            FollowingScreen(
                username = followeesScreen.username,
                viewModel = followeesViewModel,
                onButtonClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
