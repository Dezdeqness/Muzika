package com.dezdeqness.core.player.di

import androidx.annotation.OptIn
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module


@OptIn(androidx.media3.common.util.UnstableApi::class)
val corePlayerModule = module {

    single(named("PlayerCache")) {
        SimpleCache(
            androidContext().filesDir.resolve("player_cache"),
            NoOpCacheEvictor(),
        )
    }

    single(named("DownloadCache")) {
        SimpleCache(
            androidContext().filesDir.resolve("download_cache"),
            NoOpCacheEvictor(),
        )
    }

}
