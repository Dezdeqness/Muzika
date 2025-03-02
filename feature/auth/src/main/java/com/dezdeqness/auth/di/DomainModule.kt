package com.dezdeqness.auth.di

import com.dezdeqness.auth.domain.usecase.LoginUseCase
import org.koin.dsl.module


val domainModule = module {
    single {
        LoginUseCase(
            authRepository = get(),
            tokenDataProvider = get(),
        )
    }
}
