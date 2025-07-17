package com.dezdeqness.core.di

import com.dezdeqness.core.dispatcher.CoroutineDispatcherProvider
import com.dezdeqness.core.dispatcher.CoroutineDispatcherProviderImpl
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class CoreModule {

    @Single
    fun coroutineDispatcherProvider(): CoroutineDispatcherProvider =
        CoroutineDispatcherProviderImpl()
}
