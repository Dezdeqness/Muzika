package com.dezdeqness.settings.core

import androidx.compose.runtime.compositionLocalOf

val LocalVersionName = compositionLocalOf<String> {
    error("No version name provided")
}
