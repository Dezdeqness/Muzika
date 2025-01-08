package com.dezdeqness.muzika.data.datasource

import com.dezdeqness.innertube.models.response.PlayerResponse

interface PlayerApiDataSource {

    suspend fun player(videoId: String, playlistId: String? = null): Result<PlayerResponse>

}
