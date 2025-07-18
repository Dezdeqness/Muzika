package com.dezdeqness.muzika

import android.app.Application
import com.dezdeqness.auth.di.AuthModule
import com.dezdeqness.core.di.CoreModule
import com.dezdeqness.core.network.di.CoreNetworkModule
import com.dezdeqness.core.player.di.CorePlayerModule
import com.dezdeqness.likedtracks.di.LikedTracksModule
import com.dezdeqness.muzika.di.navigation.NavigationModule
import com.dezdeqness.shared.di.SharedModule
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
                SharedModule().module,
                NavigationModule().module,
                CoreNetworkModule().module,
                AuthModule().module,
                LikedTracksModule().module,
            )
        }
    }
}
