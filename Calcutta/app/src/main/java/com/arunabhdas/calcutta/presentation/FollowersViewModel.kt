package com.arunabhdas.calcutta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunabhdas.calcutta.data.model.GithubUser
import com.arunabhdas.calcutta.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FollowersViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _followersState = MutableStateFlow<FollowersState?>(null)
    val followersState: StateFlow<FollowersState?> = _followersState.asStateFlow()

    fun getFollowers(username: String) {
        _followersState.value = FollowersState.Loading
        viewModelScope.launch {
            userRepository.getFollowers(username)
                .collect { result ->
                    result.fold(
                        onSuccess = { followers -> _followersState.value = FollowersState.Success(followers) },
                        onFailure = { exception -> _followersState.value = FollowersState.Error(exception.message ?: "Unknown error occurred") }
                    )
                }
        }
    }

    sealed class FollowersState {
        object Loading : FollowersState()
        data class Success(val followers: List<GithubUser>) : FollowersState()
        data class Error(val message: String) : FollowersState()
    }
}