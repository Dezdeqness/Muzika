package com.dezdeqness.likedtracks.di

import org.koin.dsl.module

val likedTracksModule = module {
    includes(
        databaseModule,
        dataModule,
        viewModelModule
    )
}
