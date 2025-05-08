package com.arunabhdas.calcutta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunabhdas.calcutta.data.model.GithubUser
import com.arunabhdas.calcutta.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FolloweesViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _followeesState = MutableStateFlow<FolloweesState?>(null)
    val followeesState: StateFlow<FolloweesState?> = _followeesState.asStateFlow()

    fun getFollowees(username: String) {
        _followeesState.value = FolloweesState.Loading
        viewModelScope.launch {
            userRepository.getFollowees(username)
                .collect { result ->
                    result.fold(
                        onSuccess = { followees -> _followeesState.value = FolloweesState.Success(followees) },
                        onFailure = { exception -> _followeesState.value = FolloweesState.Error(exception.message ?: "Unknown error occurred") }
                    )
                }
        }
    }

    sealed class FolloweesState {
        object Loading : FolloweesState()
        data class Success(val followees: List<GithubUser>) : FolloweesState()
        data class Error(val message: String) : FolloweesState()
    }
}