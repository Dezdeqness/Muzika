package com.dezdeqness.home.data.mapper

import com.dezdeqness.home.data.models.PlaylistRemote
import com.dezdeqness.home.domain.model.PlaylistEntity
import com.dezdeqness.shared.domain.models.SongUserEntity
import org.koin.core.annotation.Single

@Single
class PlaylistMapper {
    fun toEntity(data: PlaylistRemote): PlaylistEntity {
        with(data) {
            return PlaylistEntity(
                id = id,
                urn = urn,
                duration = duration,
                genre = genre,
                title = title,
                description = description,
                uri = uri,
                artworkUrl = artworkUrl,
                userEntity = SongUserEntity(
                    id = data.user.id,
                    userName = data.user.userName,
                    userAvatar = data.user.avatarUrl,
                ),
                trackCount = trackCount,
            )
        }
    }
}
