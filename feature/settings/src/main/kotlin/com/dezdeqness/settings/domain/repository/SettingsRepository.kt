package com.dezdeqness.settings.domain.repository

import com.dezdeqness.settings.core.SettingsPreference
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun <T> getPreference(key: SettingsPreference<T>): T
    suspend fun <T> setPreference(key: SettingsPreference<T>, value: T)
    fun <T> observePreference(key: SettingsPreference<T>): Flow<T>
}
