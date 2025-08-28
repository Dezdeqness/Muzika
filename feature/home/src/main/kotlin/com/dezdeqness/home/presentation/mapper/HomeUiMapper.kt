package com.dezdeqness.home.presentation.mapper

import com.dezdeqness.shared.domain.models.PlaylistEntity
import com.dezdeqness.home.presentation.model.HomePlaylistUiModel

class HomeUiMapper {
    fun toUiModel(data: PlaylistEntity) =
        HomePlaylistUiModel(
            id = data.id,
            urn = data.urn,
            title = data.title,
            description = data.description,
            userName = data.userEntity.userName,
            duration = data.duration,
            tracksCount = data.trackCount,
            imageUrl = (data.artworkUrl ?: data.userEntity.userAvatar).replace("large", "t500x500"),
        )
}
