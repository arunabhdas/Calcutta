package com.arunabhdas.calcutta.data.api

import com.arunabhdas.calcutta.data.model.GithubUser
import com.arunabhdas.calcutta.data.model.Todo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<GithubUser>

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): List<GithubUser>

    @GET("users/{username}/following")
    suspend fun getFollowees(@Path("username") username: String): List<GithubUser>

    // https://jsonplaceholder.typicode.com/todos

    @GET("https://jsonplaceholder.typicode.com/todos")
    suspend fun getTodos(): List<Todo>
}