package com.dezdeqness.home.data.datasource.impl

import com.dezdeqness.home.data.api.PlaylistService
import com.dezdeqness.home.data.datasource.PlaylistRemoteDataSource
import com.dezdeqness.home.data.mapper.PlaylistMapper
import com.dezdeqness.home.domain.model.PlaylistState
import org.koin.core.annotation.Single

@Single(binds = [PlaylistRemoteDataSource::class])
class PlaylistRemoteDataSourceImpl(
    private val playlistService: PlaylistService,
    private val playlistMapper: PlaylistMapper,
) : PlaylistRemoteDataSource {

    override suspend fun getPlaylistByQuery(query: String, limit: Int): Result<PlaylistState> =
        tryWithCatch {
            val response = playlistService.playlistSearch(
                map = mapOf(
                    "limit" to 5,
                    "linked_partitioning" to true,
                    "show_tracks" to false,
                    "q" to query,
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                    ?: return@tryWithCatch Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))

                Result.success(
                    PlaylistState(
                        list = body.collection?.mapNotNull(playlistMapper::toEntity) ?: listOf(),
                        nextKey = body.nextHref.orEmpty(),
                    )
                )
            } else {
                // TODO: custom APIException
                Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))
            }
        }

    override suspend fun getLikedPlaylist(): Result<PlaylistState> =
        tryWithCatch {
            val response = playlistService.likedPlaylist(
                map = mapOf(
                    "limit" to 5,
                    "linked_partitioning" to true,
                    "show_tracks" to false,
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                    ?: return@tryWithCatch Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))

                Result.success(
                    PlaylistState(
                        list = body.collection?.mapNotNull(playlistMapper::toEntity) ?: listOf(),
                        nextKey = body.nextHref.orEmpty(),
                    )
                )
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
