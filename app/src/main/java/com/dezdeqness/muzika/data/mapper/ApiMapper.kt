package com.dezdeqness.muzika.data.mapper

import com.dezdeqness.innertube.models.others.MusicShelfRenderer
import com.dezdeqness.innertube.models.others.PlaylistPanelVideoRenderer
import com.dezdeqness.innertube.models.others.oddElements
import com.dezdeqness.innertube.models.others.splitBySeparator
import com.dezdeqness.innertube.models.response.NextResponse
import com.dezdeqness.innertube.models.response.PlayerResponse
import com.dezdeqness.innertube.models.response.SearchResponse
import com.dezdeqness.muzika.domain.models.SongItemEntity

class ApiMapper {

    fun mapSearchResponse(response: SearchResponse) =
        response
            .contents
            ?.tabbedSearchResultsRenderer?.tabs?.firstOrNull()
            ?.tabRenderer?.content?.sectionListRenderer?.contents?.lastOrNull()
            ?.musicShelfRenderer?.contents?.mapNotNull(::mapMusicShelfRenderer)
            .orEmpty()

    private fun mapMusicShelfRenderer(item: MusicShelfRenderer.Content?): SongItemEntity? {
        if (item?.musicResponsiveListItemRenderer == null) return null
        val renderer = item.musicResponsiveListItemRenderer

        val secondaryLine =
            renderer.flexColumns.getOrNull(1)?.musicResponsiveListItemFlexColumnRenderer?.text?.runs?.splitBySeparator()

        val id = renderer.playlistItemData?.videoId.orEmpty()
        val title = renderer.flexColumns.firstOrNull()
            ?.musicResponsiveListItemFlexColumnRenderer?.text?.runs
            ?.firstOrNull()?.text.orEmpty()

        val subTitle = secondaryLine?.getOrNull(0)?.oddElements()?.map { it.text }
            ?.firstOrNull().orEmpty()

        val iconUrl = renderer.thumbnail?.musicThumbnailRenderer?.getThumbnailUrl()
        val mainImageUrl =
            renderer.thumbnail?.musicThumbnailRenderer?.getThumbnailUrl()?.resize(600)

        return SongItemEntity(
            id = id,
            title = title,
            subTitle = subTitle,
            iconUrl = iconUrl.orEmpty(),
            mainImageUrl = mainImageUrl.orEmpty()
        )
    }

    private fun String.resize(size: Int) = "${split("=w")[0]}=w$size-h$size-p-l90-rj"

    fun mapPlayerResponse(response: PlayerResponse): String {
        val format = response.streamingData?.adaptiveFormats
            ?.filter { it.width == null }
            ?.maxByOrNull { it.bitrate + (if (it.mimeType.startsWith("audio/webm")) 10240 else 0) }
        return format?.url.orEmpty()

    }

    fun mapNextResponse(response: NextResponse): List<SongItemEntity> {
        val renderer = response.continuationContents?.playlistPanelContinuation
            ?: response.contents.singleColumnMusicWatchNextResultsRenderer.tabbedRenderer.watchNextTabbedResultsRenderer.tabs[0].tabRenderer.content?.musicQueueRenderer?.content?.playlistPanelRenderer

        return renderer
            ?.contents
            ?.mapNotNull {
                it.playlistPanelVideoRenderer?.let { renderer ->
                    mapPlaylistPanelVideoRenderer(renderer)
                }
            }.orEmpty()
    }

    private fun mapPlaylistPanelVideoRenderer(renderer: PlaylistPanelVideoRenderer): SongItemEntity? {
        val longByLineRuns = renderer.longBylineText?.runs?.splitBySeparator() ?: return null
        return SongItemEntity(
            id = renderer.videoId ?: return null,
            title = renderer.title?.runs?.firstOrNull()?.text ?: return null,
            subTitle = longByLineRuns
                .firstOrNull()
                ?.oddElements()
                ?.map { it.text }
                ?.firstOrNull().orEmpty(),
            mainImageUrl = renderer.thumbnail.thumbnails.lastOrNull()?.url?.resize(600).orEmpty(),
            iconUrl = renderer.thumbnail.thumbnails.lastOrNull()?.url.orEmpty(),
        )
    }

}
