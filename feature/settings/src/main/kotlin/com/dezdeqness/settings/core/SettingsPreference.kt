package com.dezdeqness.settings.core

interface SettingsPreference<T> {
    val name: String
    val default: T
    val handler: PreferenceHandler<T>
}
