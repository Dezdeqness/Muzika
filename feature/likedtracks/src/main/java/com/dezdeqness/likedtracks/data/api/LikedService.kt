package com.dezdeqness.likedtracks.data.api

import com.dezdeqness.core.network.data.models.CollectionResponse
import com.dezdeqness.likedtracks.core.LikedConstants.API_LIKED
import com.dezdeqness.shared.data.models.SongRemote
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.QueryMap

interface LikedService {
    @GET(API_LIKED)
    suspend fun liked(@QueryMap map: Map<String, Any>): Response<CollectionResponse<SongRemote>>
}
