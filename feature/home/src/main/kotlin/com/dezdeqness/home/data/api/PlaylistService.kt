package com.dezdeqness.home.data.api

import com.dezdeqness.core.network.data.models.CollectionResponse
import com.dezdeqness.home.core.HomeConstants.LIKED_PLAYLIST_API
import com.dezdeqness.home.core.HomeConstants.PLAYLIST_SEARCH_API
import com.dezdeqness.home.data.models.PlaylistRemote
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.QueryMap

interface PlaylistService {
    @GET(PLAYLIST_SEARCH_API)
    suspend fun playlistSearch(@QueryMap map: Map<String, Any>): Response<CollectionResponse<PlaylistRemote>>

    @GET(LIKED_PLAYLIST_API)
    suspend fun likedPlaylist(@QueryMap map: Map<String, Any>): Response<CollectionResponse<PlaylistRemote>>
}
