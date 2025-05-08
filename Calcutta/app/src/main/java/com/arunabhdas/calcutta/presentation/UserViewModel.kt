package com.arunabhdas.calcutta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunabhdas.calcutta.data.model.GithubUser
import com.arunabhdas.calcutta.data.model.Todo
import com.arunabhdas.calcutta.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UserViewModel (
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _userState

    private val _todoState = MutableStateFlow<TodoState>(TodoState.Idle)
    val todoState: StateFlow<TodoState> = _todoState

    fun getUser(username: String) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            userRepository.getUser(username)
                .catch { e ->
                    _userState.value = UserState.Error(e.message ?: "Unknown error occurred")
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { user ->
                            _userState.value = UserState.Success(user)
                        },
                        onFailure = { exception ->
                            _userState.value = UserState.Error(exception.message ?: "Unknown error occurred")
                        }
                    )
                }
        }
    }

    fun getTodoList() {
        _todoState.value = TodoState.Loading
        viewModelScope.launch {
            userRepository.getTodos()
                .catch { e ->
                    _todoState.value = TodoState.Error(e.message ?: "Unknown error")
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { todos ->
                            _todoState.value = TodoState.Success(todos)
                        },
                        onFailure = {exception ->
                            _todoState.value = TodoState.Error(exception.message ?: "Unknown Error")
                        }
                    )
                }
        }
    }

    sealed class UserState {
        object Idle: UserState()
        object Loading : UserState()
        data class Success(val user: GithubUser) : UserState()
        data class Error(val message: String) : UserState()
    }

    sealed class TodoState {
        object Idle: TodoState()
        object Loading: TodoState()
        data class Success(val todos: List<Todo>): TodoState()
        data class Error(val message: String): TodoState()
    }
}