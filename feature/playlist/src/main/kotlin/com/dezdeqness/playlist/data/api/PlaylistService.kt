package com.dezdeqness.playlist.data.api

import com.dezdeqness.core.network.data.models.CollectionResponse
import com.dezdeqness.playlist.core.PlaylistConstants.PLAYLIST_API
import com.dezdeqness.shared.data.models.SongRemote
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.QueryMap

interface PlaylistService {
    @GET(PLAYLIST_API)
    suspend fun playlistTracks(
        @Path("playlist_id") usn: String,
        @QueryMap map: Map<String, Any>,
    ): Response<CollectionResponse<SongRemote>>
}
