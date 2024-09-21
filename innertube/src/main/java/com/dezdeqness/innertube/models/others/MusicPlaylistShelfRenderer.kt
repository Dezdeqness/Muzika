package com.dezdeqness.innertube.models.others

import kotlinx.serialization.Serializable

@Serializable
data class MusicPlaylistShelfRenderer(
    val playlistId: String?,
    val contents: List<MusicShelfRenderer.Content>,
    val collapsedItemCount: Int,
    val continuations: List<Continuation>?,
)
