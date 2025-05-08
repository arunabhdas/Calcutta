package com.arunabhdas.calcutta.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arunabhdas.calcutta.R
import com.arunabhdas.calcutta.presentation.UserViewModel
import com.arunabhdas.calcutta.ui.composables.ErrorType
import com.arunabhdas.calcutta.ui.theme.ThemeUtils
import com.arunabhdas.calcutta.ui.theme.TransparentColor
import com.arunabhdas.calcutta.ui.theme.createGradientEffect
import com.arunabhdas.calcutta.ui.composables.UserInfoCard
import com.arunabhdas.calcutta.ui.composables.NotFoundCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: UserViewModel,
    onFollowersClick: (String) -> Unit,
    onFolloweesClick: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val userState by viewModel.userState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = createGradientEffect(
                    colors = ThemeUtils.GradientColors,
                    isVertical = true
                )
            )
    ) {
        if (isSearchActive) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        isSearchActive = false
                        focusManager.clearFocus()
                    }
            )
        }

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            DockedSearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {
                    if (searchQuery.isNotEmpty()) {
                        viewModel.getUser(searchQuery)
                        isSearchActive = false
                        focusManager.clearFocus()
                    }
                },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                placeholder = { Text("Search for GitHub users...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (isSearchActive && searchQuery.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    searchQuery = ""
                                }
                        )
                    }
                }
            ) {}

            Spacer(modifier = Modifier.height(24.dp))

            when (userState) {
                is UserViewModel.UserState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                is UserViewModel.UserState.Success -> {
                    val user = (userState as UserViewModel.UserState.Success).user
                    UserInfoCard(
                        user = user,
                        onFollowersClick = onFollowersClick,
                        onFolloweesClick = onFolloweesClick
                    )
                }
                is UserViewModel.UserState.Error -> {
                    val errorMessage = (userState as UserViewModel.UserState.Error).message
                    val errorType = when {
                        errorMessage.contains("not found", ignoreCase = true) -> ErrorType.NOT_FOUND
                        errorMessage.contains("network", ignoreCase = true) ||
                                errorMessage.contains("connection", ignoreCase = true) -> ErrorType.NETWORK
                        errorMessage.contains("server", ignoreCase = true) ||
                                errorMessage.contains("500", ignoreCase = true) -> ErrorType.SERVER
                        else -> ErrorType.UNKNOWN
                    }

                    // Customize title and message based on error type
                    val (title, detailedMessage) = when (errorType) {
                        ErrorType.NOT_FOUND -> Pair(
                            "User Not Found",
                            "We couldn't find any GitHub user with that username. Please check the spelling and try again."
                        )
                        ErrorType.NETWORK -> Pair(
                            "Network Error",
                            "Unable to connect to GitHub. Please check your internet connection and try again."
                        )
                        ErrorType.SERVER -> Pair(
                            "Server Error",
                            "GitHub's servers are experiencing issues. Please try again later."
                        )
                        ErrorType.UNKNOWN -> Pair(
                            "Something Went Wrong",
                            errorMessage
                        )
                    }

                    NotFoundCard(
                        title = title,
                        message = detailedMessage,
                        errorType = errorType,
                        onRetryClick = {
                            if (searchQuery.isNotEmpty()) {
                                viewModel.getUser(searchQuery)
                            }
                        }
                    )
                }
                else -> {
                    Text(
                        text = "Enter a GitHub username to see their profile",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painterResource(id = R.drawable.appicon),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(60.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onButtonClick()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(TransparentColor),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(text = "Back to LandingScreen")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
