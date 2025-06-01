package com.dezdeqness.auth.di

import com.dezdeqness.auth.presentation.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel {
        AuthViewModel(
            loginUseCase = get(),
            authUrlProvider = get(),
            authRepository = get(),
            coroutineDispatcherProvider = get(),
            utils = get()
        )
    }
}
