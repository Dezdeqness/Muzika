package com.dezdeqness.innertube.models.pages

import com.dezdeqness.innertube.models.others.SongItem

data class PlaylistContinuationPage(
    val songs: List<SongItem>,
    val continuation: String?,
)
