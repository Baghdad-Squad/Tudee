package com.baghdad.tudee.app

import android.app.Application
import com.baghdad.tudee.di.databaseModule
import com.baghdad.tudee.di.serviceModule
import com.baghdad.tudee.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TudeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TudeeApplication)
            modules(
                viewModelModule,
                serviceModule,
                databaseModule
            )
        }
    }
}