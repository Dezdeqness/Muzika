package com.dezdeqness.core.player.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@OptIn(UnstableApi::class)
@Module
class CorePlayerModule {

    @Single
    @Named("PlayerCache")
    fun providePlayerCache(context: Context): SimpleCache {
        return SimpleCache(
            context.filesDir.resolve("player_cache"),
            NoOpCacheEvictor()
        )
    }

    @Single
    @Named("DownloadCache")
    fun provideDownloadCache(context: Context): SimpleCache {
        return SimpleCache(
            context.filesDir.resolve("download_cache"),
            NoOpCacheEvictor()
        )
    }
}
