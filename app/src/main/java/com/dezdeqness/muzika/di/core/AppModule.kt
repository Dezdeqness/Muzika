package com.dezdeqness.muzika.di.core

import com.dezdeqness.muzika.core.CoroutineDispatcherProvider
import com.dezdeqness.muzika.core.CoroutineDispatcherProviderImpl
import com.dezdeqness.muzika.data.mapper.ApiMapper
import org.koin.dsl.module

val appModule = module {

    single {
        ApiMapper()
    }

    single<CoroutineDispatcherProvider> {
        CoroutineDispatcherProviderImpl()
    }

}
