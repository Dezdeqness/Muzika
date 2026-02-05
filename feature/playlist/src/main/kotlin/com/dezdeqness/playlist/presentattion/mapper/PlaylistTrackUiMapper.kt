package com.dezdeqness.playlist.presentattion.mapper

import com.dezdeqness.playlist.presentattion.model.PlaylistTrackUiModel
import com.dezdeqness.shared.domain.models.SongEntity

class PlaylistTrackUiMapper {
    fun toUiModel(list: List<SongEntity>) = list.map(::toUiModel)

    fun toUiModel(data: SongEntity) =
        PlaylistTrackUiModel(
            id = data.id.toString(),
            urn = data.urn,
            name = data.title,
            authorName = data.metadataArtist ?: data.userEntity.userName,
            iconImageUrl = (data.artworkUrl ?: data.userEntity.userAvatar).replace("large", "t500x500"),
            streamUrl = data.streamUrl.orEmpty(),
        )
}
