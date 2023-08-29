package com.essoft.imagetomath

import android.app.Application
import com.essoft.imagetomath.module.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@App)
            modules(homeModule)
        }
    }
}