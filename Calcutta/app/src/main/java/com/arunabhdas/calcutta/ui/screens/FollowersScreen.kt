package com.arunabhdas.calcutta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arunabhdas.calcutta.presentation.FollowersViewModel
import com.arunabhdas.calcutta.ui.composables.FollowersList
import com.arunabhdas.calcutta.ui.theme.ThemeUtils
import com.arunabhdas.calcutta.ui.theme.createGradientEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowersScreen(
    viewModel: FollowersViewModel,
    username: String,
    onButtonClick: () -> Unit = {}
) {
    val followersState by viewModel.followersState.collectAsState()
    
    LaunchedEffect(username) {
        viewModel.getFollowers(username)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$username's Followers") },
                navigationIcon = {
                    IconButton(onClick = onButtonClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = createGradientEffect(
                        colors = ThemeUtils.GradientColors,
                        isVertical = true
                    )
                )
        ) {
            when (val state = followersState) {
                is FollowersViewModel.FollowersState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
                is FollowersViewModel.FollowersState.Success -> {
                    if (state.followers.isEmpty()) {
                        Text(
                            text = "No followers found",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    } else {
                        FollowersList(followers = state.followers)
                    }
                }
                is FollowersViewModel.FollowersState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.getFollowers(username) }) {
                            Text("Retry")
                        }
                    }
                }
                null -> {
                    // Initial state, do nothing
                }
            }
        }
    }
}


