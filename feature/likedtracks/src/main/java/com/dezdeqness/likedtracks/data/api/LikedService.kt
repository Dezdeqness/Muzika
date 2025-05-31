package com.dezdeqness.likedtracks.data.api

import com.dezdeqness.core.network.data.models.CollectionResponse
import com.dezdeqness.likedtracks.core.LikedConstants.API_LIKED
import com.dezdeqness.shared.data.models.SongRemote
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.FieldMap
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST

interface LikedService {
    @FormUrlEncoded
    @POST(API_LIKED)
    suspend fun liked(@FieldMap map: Map<String, Any>): Response<CollectionResponse<SongRemote>>
}
