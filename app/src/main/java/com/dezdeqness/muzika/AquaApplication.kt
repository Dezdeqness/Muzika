package com.dezdeqness.muzika

import android.app.Application
import android.os.Build
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import com.dezdeqness.auth.di.AuthModule
import com.dezdeqness.core.di.CoreModule
import com.dezdeqness.core.network.di.CoreNetworkModule
import com.dezdeqness.core.player.di.CorePlayerModule
import com.dezdeqness.home.di.HomeModule
import com.dezdeqness.likedtracks.di.LikedTracksModule
import com.dezdeqness.muzika.di.navigation.NavigationModule
import com.dezdeqness.playlist.di.PlaylistModule
import com.dezdeqness.settings.di.SettingsModule
import com.dezdeqness.settings.domain.models.ImageCacheMaxSize
import com.dezdeqness.settings.domain.repository.SettingsRepository
import com.dezdeqness.shared.di.SharedModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module
import kotlin.coroutines.CoroutineContext

class AquaApplication : Application(), ImageLoaderFactory, CoroutineScope {
    private val settingsRepository: SettingsRepository by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AquaApplication)
            modules(
                CoreModule().module,
                CorePlayerModule().module,
                SharedModule().module,
                NavigationModule().module,
                CoreNetworkModule().module,
                AuthModule().module,
                LikedTracksModule().module,
                HomeModule().module,
                PlaylistModule().module,
                SettingsModule().module,
            )
        }
        val settingsRepository: SettingsRepository by inject()
        launch {
            settingsRepository
                .observePreference(ImageCacheMaxSize)
                .collect { cacheMb ->
                    Coil.setImageLoader(createImageLoader(cacheMb))
                }
        }
    }

    override fun newImageLoader() = createImageLoader(
        runBlocking { settingsRepository.getPreference(ImageCacheMaxSize) }
    )

    private fun createImageLoader(cacheSizeMb: Int) = ImageLoader.Builder(this)
        .allowHardware(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        .diskCache(
            DiskCache.Builder()
                .directory(cacheDir.resolve("coil"))
                .maxSizeBytes(cacheSizeMb * 1024 * 1024L)
                .build()
        )
        .build()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
}
