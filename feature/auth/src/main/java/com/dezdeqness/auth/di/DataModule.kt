package com.dezdeqness.auth.di

import com.dezdeqness.auth.data.provider.AuthorizationUrlProvider
import org.koin.dsl.module

val dataModule = module {
    single<AuthorizationUrlProvider> {
        AuthorizationUrlProvider()
    }
}
