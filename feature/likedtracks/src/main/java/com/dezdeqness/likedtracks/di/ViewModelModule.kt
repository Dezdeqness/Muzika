package com.dezdeqness.likedtracks.di

import com.dezdeqness.likedtracks.presentation.LikedTracksViewModel
import com.dezdeqness.likedtracks.presentation.mapper.LikedTrackMapper
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    single {
        LikedTrackMapper()
    }

    viewModel {
        LikedTracksViewModel(
            likedRepository = get(),
            likedTrackMapper = get(),
        )
    }
}
