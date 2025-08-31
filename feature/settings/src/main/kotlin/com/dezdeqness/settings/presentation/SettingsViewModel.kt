package com.dezdeqness.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dezdeqness.settings.core.SettingsPreference
import com.dezdeqness.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    fun <T> observe(key: SettingsPreference<T>) = settingsRepository.observePreference(key)

    fun <T> set(key: SettingsPreference<T>, value: T) {
        viewModelScope.launch {
            settingsRepository.setPreference(key, value)
        }
    }

}
