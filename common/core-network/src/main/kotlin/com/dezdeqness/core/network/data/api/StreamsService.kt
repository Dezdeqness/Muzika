package com.dezdeqness.core.network.data.api

import com.dezdeqness.core.network.core.StreamsConstants
import com.dezdeqness.core.network.data.models.StreamsResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface StreamsService {
    @GET(StreamsConstants.API_STREAMS)
    suspend fun getStreams(
        @Path("track_id") trackId: String,
    ): StreamsResponse
}
