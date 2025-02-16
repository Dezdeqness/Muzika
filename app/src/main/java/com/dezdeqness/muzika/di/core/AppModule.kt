package com.dezdeqness.muzika.di.core

import com.dezdeqness.muzika.core.CoroutineDispatcherProvider
import com.dezdeqness.muzika.core.CoroutineDispatcherProviderImpl
import org.koin.dsl.module

val appModule = module {

    single<CoroutineDispatcherProvider> {
        CoroutineDispatcherProviderImpl()
    }

}
