package com.dezdeqness.likedtracks.di

import com.dezdeqness.core.network.di.Qualifiers
import com.dezdeqness.likedtracks.data.api.LikedService
import com.dezdeqness.likedtracks.data.api.createLikedService
import com.dezdeqness.likedtracks.data.datasource.LikedSongLocalDataSourceImpl
import com.dezdeqness.likedtracks.data.datasource.LikedSongLocalDatasource
import com.dezdeqness.likedtracks.data.datasource.LikedSongRemoteDataSource
import com.dezdeqness.likedtracks.data.datasource.LikedSongRemoteDataSourceImpl
import com.dezdeqness.likedtracks.data.db.mapper.SongLocalMapper
import com.dezdeqness.likedtracks.data.repository.LikedRepositoryImpl
import com.dezdeqness.likedtracks.domain.LikedRepository
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

internal val dataModule = module {
    single<LikedService> {
        get<Ktorfit>(Qualifiers.sharedKtorfitQualified).createLikedService()
    }

    single {
        SongLocalMapper()
    }

    single<LikedSongRemoteDataSource> {
        LikedSongRemoteDataSourceImpl(
            likedService = get(),
            songMapper = get(),
        )
    }
    single<LikedSongLocalDatasource> {
        LikedSongLocalDataSourceImpl(
            database = get(),
            mapper = get(),
        )
    }
    single<LikedRepository> {
        LikedRepositoryImpl(
            likedSongRemoteDataSource = get(),
            likedSongLocalDatasource = get(),
            roomDatabase = get(),
            songLocalMapper = get()
        )
    }
}
