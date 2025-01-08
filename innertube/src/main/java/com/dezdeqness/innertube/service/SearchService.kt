package com.dezdeqness.innertube.service

import com.dezdeqness.innertube.core.Constants
import com.dezdeqness.innertube.models.request.SearchBody
import com.dezdeqness.innertube.models.response.SearchResponse
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.HeaderMap
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.QueryMap

interface SearchService {
    @POST(Constants.Endpoints.SEARCH)
    suspend fun search(
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queries: Map<String, Any>,
        @Body body: SearchBody,
    ): Response<SearchResponse>

}
