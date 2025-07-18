package com.dezdeqness.likedtracks.data.db.mapper

import com.dezdeqness.likedtracks.data.db.model.SongLocal
import com.dezdeqness.shared.domain.models.SongEntity
import com.dezdeqness.shared.domain.models.SongKind
import com.dezdeqness.shared.domain.models.SongUserEntity
import org.koin.core.annotation.Single

@Single
class SongLocalMapper {
    fun toLocal(index: Int, entity: SongEntity): SongLocal = SongLocal(
        id = entity.id,
        kind = entity.kind.name,
        urn = entity.urn,
        createdAt = entity.createdAt,
        duration = entity.duration,
        commentCount = entity.commentCount,
        tags = entity.tags,
        genre = entity.genre,
        title = entity.title,
        description = entity.description,
        releaseYear = entity.releaseYear,
        releaseMonth = entity.releaseMonth,
        releaseDay = entity.releaseDay,
        uri = entity.uri,
        permalinkUrl = entity.permalinkUrl,
        artworkUrl = entity.artworkUrl,
        streamUrl = entity.streamUrl,
        downloadUrl = entity.downloadUrl,
        waveformUrl = entity.waveformUrl,
        userPlaybackCount = entity.userPlaybackCount,
        playbackCount = entity.playbackCount,
        downloadCount = entity.downloadCount,
        favoriteCount = entity.favoriteCount,
        repostsCount = entity.repostsCount,
        metadataArtist = entity.metadataArtist,
        userId = entity.userEntity.id,
        userName = entity.userEntity.userName,
        userAvatar = entity.userEntity.userAvatar,
        orderInResponse = index,
    )

    fun toEntity(local: SongLocal): SongEntity = SongEntity(
        kind = SongKind.valueOf(local.kind),
        id = local.id,
        urn = local.urn,
        createdAt = local.createdAt,
        duration = local.duration,
        commentCount = local.commentCount,
        tags = local.tags,
        genre = local.genre,
        title = local.title,
        description = local.description,
        releaseYear = local.releaseYear,
        releaseMonth = local.releaseMonth,
        releaseDay = local.releaseDay,
        uri = local.uri,
        permalinkUrl = local.permalinkUrl,
        artworkUrl = local.artworkUrl,
        streamUrl = local.streamUrl,
        downloadUrl = local.downloadUrl,
        waveformUrl = local.waveformUrl,
        userPlaybackCount = local.userPlaybackCount,
        playbackCount = local.playbackCount,
        downloadCount = local.downloadCount,
        favoriteCount = local.favoriteCount,
        repostsCount = local.repostsCount,
        metadataArtist = local.metadataArtist,
        userEntity = SongUserEntity(
            id = local.userId,
            userName = local.userName,
            userAvatar = local.userAvatar
        )
    )

}
