package com.dezdeqness.innertube.service

import com.dezdeqness.innertube.core.Constants
import com.dezdeqness.innertube.models.request.NextBody
import com.dezdeqness.innertube.models.response.NextResponse
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.HeaderMap
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.QueryMap

interface NextService {
    @POST(Constants.Endpoints.NEXT)
    suspend fun next(
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queries: Map<String, Any>,
        @Body body: NextBody,
    ): Response<NextResponse>

}
