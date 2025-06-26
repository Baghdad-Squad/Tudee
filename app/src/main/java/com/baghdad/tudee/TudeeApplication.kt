package com.baghdad.tudee

import android.app.Application
import com.baghdad.tudee.di.databaseModule
import com.baghdad.tudee.di.serviceModule
import com.baghdad.tudee.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class TudeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
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