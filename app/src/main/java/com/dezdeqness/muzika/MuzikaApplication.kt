package com.dezdeqness.muzika

import android.app.Application
import com.dezdeqness.auth.di.dataModule
import com.dezdeqness.muzika.di.core.appModule
import com.dezdeqness.muzika.di.core.cacheModule
import com.dezdeqness.auth.di.networkModule
import com.dezdeqness.auth.di.utilsModule
import com.dezdeqness.auth.di.viewModelModule
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
                utilsModule,
                dataModule,
                networkModule,
                viewModelModule,
                appModule,
                cacheModule,
                repositoryModule,
                viewmodelModule
            )
        }
    }
}
