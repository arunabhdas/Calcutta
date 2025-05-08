package com.arunabhdas.calcutta.data.repository

import com.arunabhdas.calcutta.data.api.GitHubApiService
import com.arunabhdas.calcutta.data.model.GithubUser
import com.arunabhdas.calcutta.data.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository (
    private val apiService: GitHubApiService
) {
    suspend fun getUser(username: String): Flow<Result<GithubUser>> = flow {
        try {
            val response = apiService.getUser(username)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("User data is null")))
            } else {
                emit(Result.failure(Exception("Error: ${response.code()} ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getFollowers(username: String): Flow<Result<List<GithubUser>>> = flow {
        try {
            val followers = apiService.getFollowers(username)
            emit(Result.success(followers))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getFollowees(username: String): Flow<Result<List<GithubUser>>> = flow {
        try {
            val followees = apiService.getFollowees(username)
            emit(Result.success(followees))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    fun getTodos(): Flow<Result<List<Todo>>> = flow {
        try {
            val todos = apiService.getTodos()
            emit(Result.success(todos))
        } catch(e: Exception) {
            emit(Result.failure(e))
        }
    }
}