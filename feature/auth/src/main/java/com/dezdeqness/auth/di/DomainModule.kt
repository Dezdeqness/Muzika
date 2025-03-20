package com.dezdeqness.auth.di

import com.dezdeqness.auth.domain.usecase.LoginUseCase
import org.koin.dsl.module


internal val domainModule = module {
    single {
        LoginUseCase(
            authRepository = get(),
            tokenDataProvider = get(),
        )
    }
}
