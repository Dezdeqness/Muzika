package com.dezdeqness.playlist.domain.model

import com.dezdeqness.shared.domain.models.SongEntity

data class PlaylistTracksState(
    val list: List<SongEntity>,
    val nextKey: String,
)