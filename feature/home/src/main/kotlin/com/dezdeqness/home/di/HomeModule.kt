package com.dezdeqness.home.di

import com.dezdeqness.core.network.di.Qualifiers
import com.dezdeqness.home.data.api.PlaylistService
import com.dezdeqness.home.data.api.createPlaylistService
import com.dezdeqness.home.presentation.mapper.HomeUiMapper
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.dezdeqness.home")
class HomeModule {

    @Single
    fun provideHomeUIMapper() = HomeUiMapper()

    @Single
    fun providePlaylistService(@Named(Qualifiers.sharedKtorfitQualified) ktorfit: Ktorfit): PlaylistService {
        return ktorfit.createPlaylistService()
    }
}
