package com.dezdeqness.muzika

import android.app.Application
import android.content.Context
import android.os.Build
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.annotation.DelicateCoilApi
import coil3.disk.DiskCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.allowHardware
import coil3.util.DebugLogger
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
import okio.Path.Companion.toOkioPath
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module
import kotlin.coroutines.CoroutineContext

class FonoApplication :
    Application(),
    SingletonImageLoader.Factory,
    CoroutineScope {
    private val settingsRepository: SettingsRepository by inject()

    @OptIn(DelicateCoilApi::class)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FonoApplication)
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
                    SingletonImageLoader.setUnsafe {
                       createImageLoader(cacheMb)
                    }
                }
        }
    }

    override fun newImageLoader(context: Context) = createImageLoader(
        runBlocking { settingsRepository.getPreference(ImageCacheMaxSize) }
    )

    private fun createImageLoader(cacheSizeMb: Int) = ImageLoader
        .Builder(this)
        .components {
            add(OkHttpNetworkFetcherFactory())
        }
        .logger(DebugLogger())
        .allowHardware(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        .diskCache(
            DiskCache.Builder()
                .directory(cacheDir.resolve("coil").toOkioPath())
                .maxSizeBytes(cacheSizeMb * 1024 * 1024L)
                .build()
        )
        .build()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
}
