package com.arunabhdas.calcutta.data.model

import com.google.gson.annotations.SerializedName

data class GithubUser(
    val id: Long,
    val login: String,
    val name: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    val bio: String?,
    @SerializedName("public_repos")
    val publicRepos: Int,
    val followers: Int,
    val following: Int,
    val location: String?,
    val company: String?,
    val email: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("html_url")
    val htmlUrl: String
)