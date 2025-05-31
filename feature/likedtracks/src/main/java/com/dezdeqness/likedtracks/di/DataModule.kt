package com.dezdeqness.likedtracks.di

import com.dezdeqness.likedtracks.data.api.LikedService
import com.dezdeqness.likedtracks.data.api.createLikedService
import com.dezdeqness.likedtracks.data.datasource.LikedSongDataSource
import com.dezdeqness.likedtracks.data.datasource.LikedSongDataSourceImpl
import com.dezdeqness.likedtracks.data.repository.LikedRepositoryImpl
import com.dezdeqness.likedtracks.domain.LikedRepository
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

internal val dataModule = module {
    single<LikedService> {
        get<Ktorfit>().createLikedService()
    }

    single<LikedSongDataSource> {
        LikedSongDataSourceImpl(
            likedService = get(),
            songMapper = get(),
        )
    }
    single<LikedRepository> {
        LikedRepositoryImpl(
            likedSongDataSource = get(),
        )
    }
}
