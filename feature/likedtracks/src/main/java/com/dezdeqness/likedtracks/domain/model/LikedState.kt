package com.dezdeqness.likedtracks.domain.model

import com.dezdeqness.shared.domain.models.SongEntity

data class LikedState(
    val list: List<SongEntity>,
    val nextKey: String,
)
