package com.dezdeqness.auth.di

import com.dezdeqness.auth.presentation.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AuthViewModel(
            authDatasource = get(),
            authUrlProvider = get(),
            utils = get()
        )
    }
}
