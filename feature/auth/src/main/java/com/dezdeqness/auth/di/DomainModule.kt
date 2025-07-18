package com.dezdeqness.auth.di

import com.dezdeqness.auth.domain.usecase.IsRefreshedTokenUseCaseImpl
import com.dezdeqness.auth.domain.usecase.LoginUseCase
import com.dezdeqness.auth.domain.usecase.RetrieveAccessTokenUseCaseImpl
import com.dezdeqness.core.network.domain.IsRefreshedTokenUseCase
import com.dezdeqness.core.network.domain.RetrieveAccessTokenUseCase
import org.koin.dsl.module


internal val domainModule = module {


    single {
        LoginUseCase(
            authRepository = get(),
            tokenDataProvider = get(),
        )
    }
}
