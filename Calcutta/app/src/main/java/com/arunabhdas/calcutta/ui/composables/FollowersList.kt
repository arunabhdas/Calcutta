package com.arunabhdas.calcutta.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arunabhdas.calcutta.data.model.GithubUser
import com.arunabhdas.calcutta.data.model.Todo


@Composable
fun FollowersList(followers: List<GithubUser>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(followers.size) { index ->
            val follower = followers[index]
            FollowerItem(follower = follower)
        }
    }
}

@Composable
fun TodosList(todos: List<Todo>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(todos.size) { index ->
            val todo = todos[index]
            TodoItem(todo = todo)
        }
    }
}
