package com.dezdeqness.playlist.di

import com.dezdeqness.core.network.di.Qualifiers
import com.dezdeqness.playlist.data.api.PlaylistService
import com.dezdeqness.playlist.data.api.createPlaylistService
import com.dezdeqness.playlist.presentattion.mapper.PlaylistTrackUiMapper
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.dezdeqness.playlist")
class PlaylistModule {

    @Single
    fun providePlaylistTrackUiMapper() = PlaylistTrackUiMapper()

    @Single
    fun providePlaylistService(@Named(Qualifiers.sharedKtorfitQualified) ktorfit: Ktorfit): PlaylistService {
        return ktorfit.createPlaylistService()
    }
}
