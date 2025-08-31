package com.dezdeqness.settings.domain.models

import com.dezdeqness.settings.core.SettingsPreference
import com.dezdeqness.settings.core.handlers.IntHandler

data object SongCacheMaxSize : SettingsPreference<Int> {
    override val name = "song_cache_cache_size"
    override val default = 2048
    override val handler = IntHandler
}

data object ImageCacheMaxSize : SettingsPreference<Int> {
    override val name = "image_cache_cache_size"
    override val default = 512
    override val handler = IntHandler
}
