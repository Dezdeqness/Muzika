package com.dezdeqness.auth.di

import com.dezdeqness.auth.data.api.AuthService
import com.dezdeqness.auth.data.api.createAuthService
import com.dezdeqness.auth.data.datasource.AuthDatasource
import com.dezdeqness.auth.data.datasource.impl.AuthDatasourceImpl
import com.dezdeqness.auth.data.mapper.TokenDataMapper
import com.dezdeqness.auth.data.provider.AuthorizationUrlProvider
import com.dezdeqness.auth.data.provider.TokenDataProvider
import com.dezdeqness.auth.data.repository.AuthRepositoryImpl
import com.dezdeqness.auth.domain.repository.AuthRepository
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

val dataModule = module {
    single {
        AuthorizationUrlProvider()
    }
    single<AuthService> {
        get<Ktorfit>().createAuthService()
    }

    single<AuthDatasource> {
        AuthDatasourceImpl(
            authService = get(),
            tokenDataMapper = get(),
        )
    }

    single {
        TokenDataMapper()
    }

    single {
        TokenDataProvider(get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            authDatasource = get(),
            tokenDataProvider = get(),
        )
    }
}
