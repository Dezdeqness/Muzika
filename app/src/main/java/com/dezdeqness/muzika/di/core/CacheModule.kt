package com.dezdeqness.muzika.di.core

import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module


@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
val cacheModule = module {

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
