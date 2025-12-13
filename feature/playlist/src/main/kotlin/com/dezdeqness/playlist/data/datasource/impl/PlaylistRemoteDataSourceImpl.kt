package com.dezdeqness.playlist.data.datasource.impl

import com.dezdeqness.playlist.data.api.PlaylistService
import com.dezdeqness.playlist.data.datasource.PlaylistRemoteDataSource
import com.dezdeqness.playlist.domain.model.PlaylistTracksState
import com.dezdeqness.shared.data.mapper.SongMapper
import org.koin.core.annotation.Single

@Single(binds = [PlaylistRemoteDataSource::class])
class PlaylistRemoteDataSourceImpl(
    private val playlistService: PlaylistService,
    private val songMapper: SongMapper,
) : PlaylistRemoteDataSource {
    override suspend fun getPlaylistTracks(
        usn: String,
        key: String?
    ): Result<PlaylistTracksState> = tryWithCatch{
        val response = playlistService.playlistTracks(
            map = mapOf(
                "limit" to 40,
                "linked_partitioning" to true,
                "access" to "playable",
                "offset" to (key ?: "0"),
            ),
            usn = usn,
        )

        if (response.isSuccessful) {
            val body = response.body()
                ?: return@tryWithCatch Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))

            Result.success(PlaylistTracksState(
                list = body.collection?.mapNotNull(songMapper::toEntity) ?: listOf(),
                nextKey = body.nextHref.orEmpty(),
            ))
        } else {
            // TODO: custom APIException
            Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))
        }
    }

    private suspend fun <T> tryWithCatch(block: suspend () -> Result<T>) = try {
        block()
    } catch (throwable: Throwable) {
        Result.failure(throwable)
    }
}
