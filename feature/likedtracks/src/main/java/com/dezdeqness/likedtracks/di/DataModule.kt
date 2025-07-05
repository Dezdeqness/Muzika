package com.dezdeqness.likedtracks.di

import com.dezdeqness.core.network.di.Qualifiers
import com.dezdeqness.likedtracks.data.api.LikedService
import com.dezdeqness.likedtracks.data.api.createLikedService
import com.dezdeqness.likedtracks.data.datasource.LikedSongRemoteDataSource
import com.dezdeqness.likedtracks.data.datasource.LikedSongRemoteDataSourceImpl
import com.dezdeqness.likedtracks.data.repository.LikedRepositoryImpl
import com.dezdeqness.likedtracks.domain.LikedRepository
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

internal val dataModule = module {
    single<LikedService> {
        get<Ktorfit>(Qualifiers.sharedKtorfitQualified).createLikedService()
    }

    single<LikedSongRemoteDataSource> {
        LikedSongRemoteDataSourceImpl(
            likedService = get(),
            songMapper = get(),
        )
    }
    single<LikedRepository> {
        LikedRepositoryImpl(
            likedSongRemoteDataSource = get(),
        )
    }
}
