package com.dezdeqness.auth.di

import org.koin.dsl.module

val authModule = module {
    includes(
        utilsModule,
        networkModule,
        dataModule,
        domainModule,
        viewModelModule
    )
}
