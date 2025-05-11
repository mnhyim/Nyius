package com.mnhyim.nyius

import android.app.Application
import com.mnhyim.data.di.dataModule
import com.mnhyim.data.di.provideNewsApiModule
import com.mnhyim.data.di.provideRepositoryModule
import com.mnhyim.nyius.di.appModule
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                appModule,
                dataModule,
                provideRepositoryModule,
                provideNewsApiModule
            )

        }
    }
}