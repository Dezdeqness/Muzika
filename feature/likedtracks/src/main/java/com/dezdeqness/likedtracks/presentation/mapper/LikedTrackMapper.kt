package com.dezdeqness.likedtracks.presentation.mapper

import com.dezdeqness.likedtracks.presentation.model.LikedTrackUiModel
import com.dezdeqness.shared.domain.models.SongEntity

class LikedTrackMapper {

    fun toUiModel(list: List<SongEntity>) = list.map(::toUiModel)

    fun toUiModel(data: SongEntity) =
        LikedTrackUiModel(
            id = data.id.toString(),
            urn = data.urn,
            name = data.title,
            authorName = data.metadataArtist ?: data.userEntity.userName,
            iconImageUrl = (data.artworkUrl ?: data.userEntity.userAvatar).replace("large", "t500x500"),
            streamUrl = data.streamUrl.orEmpty(),
        )

}
