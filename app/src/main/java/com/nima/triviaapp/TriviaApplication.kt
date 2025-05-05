package com.nima.triviaapp

import android.app.Application
import com.nima.triviaapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TriviaApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@TriviaApplication)
            modules(appModule)
        }
    }
}