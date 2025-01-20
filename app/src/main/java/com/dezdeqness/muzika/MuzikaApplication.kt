package com.dezdeqness.muzika

import android.app.Application
import com.dezdeqness.innertube.core.YouTube
import com.dezdeqness.innertube.models.others.YouTubeLocale
import com.dezdeqness.muzika.di.core.appModule
import com.dezdeqness.muzika.di.core.cacheModule
import com.dezdeqness.muzika.di.core.networkModule
import com.dezdeqness.muzika.di.repositoryModule
import com.dezdeqness.muzika.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MuzikaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        YouTube.locale = YouTubeLocale(
            gl = "US",
            hl = "en"
        )

        startKoin {
            androidContext(this@MuzikaApplication)
            modules(networkModule, appModule, cacheModule, repositoryModule, viewmodelModule)
        }

        YouTube.visitorData = "CgtsZG1ySnZiQWtSbyiMjuGSBg%3D%3D"
    }
}
