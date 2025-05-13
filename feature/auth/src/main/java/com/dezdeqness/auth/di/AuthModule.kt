package com.dezdeqness.auth.di

import com.dezdeqness.core.network.di.coreNetworkModule
import org.koin.dsl.module

val authModule = module {
    includes(
        utilsModule,
        networkModule,
        coreNetworkModule,
        dataModule,
        domainModule,
        viewModelModule
    )
}
