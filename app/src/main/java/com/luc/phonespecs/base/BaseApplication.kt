package com.luc.phonespecs.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.luc.data.di.dataModule
import com.luc.data.di.isFirebaseLocal
import com.luc.domain.di.domainModule
import com.luc.phonespecs.BuildConfig
import com.luc.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if(BuildConfig.DEBUG) {
            isFirebaseLocal = true
        }

        isFirebaseLocal = false

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(applicationContext)
            modules(dataModule + domainModule + presentationModule)
        }
    }
}