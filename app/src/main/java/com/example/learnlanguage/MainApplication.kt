package com.example.learnlanguage

import android.app.Application
import com.example.learnlanguage.di.KoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    companion object {
        private var instance: MainApplication? = null

        fun getInstance(): MainApplication {
            return instance ?: synchronized(this) {
                instance ?: MainApplication().also { instance = it }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(KoinModule)
        }
    }
}