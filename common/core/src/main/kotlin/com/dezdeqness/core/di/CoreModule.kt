package com.dezdeqness.core.di

import com.dezdeqness.core.coroutines.CoroutineDispatcherProvider
import com.dezdeqness.core.coroutines.CoroutineDispatcherProviderImpl
import org.koin.dsl.module

val coreModule = module {
    single<CoroutineDispatcherProvider> {
        CoroutineDispatcherProviderImpl()
    }
}
