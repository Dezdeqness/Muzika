package com.dezdeqness.likedtracks.data.datasource

import com.dezdeqness.likedtracks.data.api.LikedService
import com.dezdeqness.shared.data.mapper.SongMapper

class LikedSongDataSourceImpl(
    private val likedService: LikedService,
    private val songMapper: SongMapper,
): LikedSongDataSource {

    override suspend fun getLikedSongs() = tryWithCatch {
        val response = likedService.liked(
            map = mapOf(
                "limit" to 20,
                "linked_partitioning" to true,
                "access" to "playable",
            )
        )

        if (response.isSuccessful) {
            val body = response.body()
                ?: return@tryWithCatch Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))

            Result.success(body.collection?.mapNotNull(songMapper::toEntity) ?: listOf())
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
