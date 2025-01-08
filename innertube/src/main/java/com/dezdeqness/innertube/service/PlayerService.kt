package com.dezdeqness.innertube.service

import com.dezdeqness.innertube.core.Constants
import com.dezdeqness.innertube.models.request.PlayerBody
import com.dezdeqness.innertube.models.response.PlayerResponse
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.HeaderMap
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.QueryMap

interface PlayerService {
    @POST(Constants.Endpoints.PLAYER)
    suspend fun player(
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queries: Map<String, Any>,
        @Body body: PlayerBody,
    ): Response<PlayerResponse>

}
