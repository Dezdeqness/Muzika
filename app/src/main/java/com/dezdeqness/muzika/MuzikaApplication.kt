package com.dezdeqness.muzika

import android.app.Application
import com.dezdeqness.auth.di.authModule
import com.dezdeqness.core.di.CoreModule
import com.dezdeqness.core.network.di.coreNetworkModule
import com.dezdeqness.core.player.di.CorePlayerModule
import com.dezdeqness.likedtracks.di.likedTracksModule
import com.dezdeqness.muzika.di.navigation.navigationModule
import com.dezdeqness.shared.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

class MuzikaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MuzikaApplication)
            modules(
                CoreModule().module,
                CorePlayerModule().module,
                navigationModule,
                sharedModule,
                coreNetworkModule,
                authModule,
                likedTracksModule,
            )
        }
    }
}
