package com.arunabhdas.calcutta

import android.app.Application

import com.arunabhdas.calcutta.di.appModule
import com.arunabhdas.calcutta.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class CalcuttaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        startKoin {
            androidLogger(Level.ERROR) // Use ERROR level to avoid Kotlin reflection issues
            androidContext(this@CalcuttaApplication)
            modules(listOf(appModule, networkModule))
        }
    }
}