package com.dezdeqness.settings.di

import android.content.Context
import com.dezdeqness.settings.data.repository.SettingsRepositoryImpl
import com.dezdeqness.settings.domain.repository.SettingsRepository
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.dezdeqness.settings")
class SettingsModule {
    @Single
    fun provideSettingsRepository(@Provided context: Context): SettingsRepository {
        return SettingsRepositoryImpl(
            context = context,
        )
    }
}
