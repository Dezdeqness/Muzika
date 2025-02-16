package com.dezdeqness.muzika.di

import com.dezdeqness.muzika.presentation.features.home.HomeViewModel
import com.dezdeqness.muzika.presentation.features.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel {
        HomeViewModel(
            coroutineDispatcherProvider = get(),
        )
    }

    viewModel {
        SearchViewModel(
            coroutineDispatcherProvider = get(),
        )
    }
}
