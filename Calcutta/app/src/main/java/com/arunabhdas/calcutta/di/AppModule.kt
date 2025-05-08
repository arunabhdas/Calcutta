package com.arunabhdas.calcutta.di

import com.arunabhdas.calcutta.data.repository.UserRepository
import com.arunabhdas.calcutta.presentation.FolloweesViewModel
import com.arunabhdas.calcutta.presentation.FollowersViewModel
import com.arunabhdas.calcutta.presentation.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repository
    single { UserRepository(get()) }
    
    // ViewModel
    viewModel { UserViewModel(get()) }
    viewModel { FollowersViewModel(get()) }
    viewModel { FolloweesViewModel(get()) }
}