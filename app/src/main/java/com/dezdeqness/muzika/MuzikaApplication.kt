package com.dezdeqness.muzika

import android.app.Application
import com.dezdeqness.auth.di.authModule
import com.dezdeqness.muzika.di.core.appModule
import com.dezdeqness.muzika.di.core.cacheModule
import com.dezdeqness.muzika.di.navigation.navigationModule
import com.dezdeqness.muzika.di.repositoryModule
import com.dezdeqness.muzika.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MuzikaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MuzikaApplication)
            modules(
                navigationModule,
                appModule,
                cacheModule,
                repositoryModule,
                viewmodelModule,
                authModule,
            )
        }
    }
}
