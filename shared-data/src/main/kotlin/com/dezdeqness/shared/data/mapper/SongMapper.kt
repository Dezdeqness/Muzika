package com.dezdeqness.shared.data.mapper

import com.dezdeqness.shared.data.models.SongRemote
import com.dezdeqness.shared.domain.models.SongEntity
import com.dezdeqness.shared.domain.models.SongKind
import com.dezdeqness.shared.domain.models.SongUserEntity

class SongMapper {

    fun toEntity(data: SongRemote): SongEntity {
        with(data) {
            return SongEntity(
                kind = SongKind.from(kind),
                id = id,
                urn = urn,
                createdAt = createdAt,
                duration = duration,
                commentCount = commentCount,
                tags = tagList.split(" ").filter { it.isNotBlank() },
                genre = genre,
                title = title,
                description = description,
                releaseYear = releaseYear ?: 0,
                releaseMonth = releaseMonth ?: 0,
                releaseDay = releaseDay ?: 0,
                uri = uri,
                permalinkUrl = permalinkUrl,
                artworkUrl = artworkUrl,
                streamUrl = streamUrl,
                downloadUrl = downloadUrl,
                waveformUrl = waveformUrl,
                userPlaybackCount = userPlaybackCount,
                playbackCount = playbackCount,
                downloadCount = downloadCount,
                favoriteCount = favoriteCount,
                repostsCount = repostsCount,
                userEntity = SongUserEntity(
                    id = data.user.id,
                    userName = data.user.userName,
                )
            )
        }
    }
}
