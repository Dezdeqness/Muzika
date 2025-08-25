package com.dezdeqness.home.presentation.mapper

import com.dezdeqness.home.domain.model.PlaylistEntity
import com.dezdeqness.home.presentation.model.HomePlaylistUiModel

class HomeUiMapper {
    fun toUiModel(data: PlaylistEntity) =
        HomePlaylistUiModel(
            id = data.id,
            title = data.title,
            userName = data.userEntity.userName,
            imageUrl = (data.artworkUrl ?: data.userEntity.userAvatar).replace("large", "t500x500"),
        )
}
