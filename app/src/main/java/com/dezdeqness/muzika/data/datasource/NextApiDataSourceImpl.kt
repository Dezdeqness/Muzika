package com.dezdeqness.muzika.data.datasource

import com.dezdeqness.innertube.models.others.WatchEndpoint
import com.dezdeqness.innertube.models.others.YouTubeClient.Companion.WEB_REMIX
import com.dezdeqness.innertube.models.others.getContinuation
import com.dezdeqness.innertube.models.pages.NextPage
import com.dezdeqness.innertube.models.pages.NextResult
import com.dezdeqness.innertube.models.request.NextBody
import com.dezdeqness.muzika.data.core.BaseApiDataSource
import com.dezdeqness.muzika.data.mapper.ApiMapper
import com.dezdeqness.innertube.service.NextService


class NextApiDataSourceImpl(
    private val nextService: NextService,
    apiMapper: ApiMapper,
) : BaseApiDataSource(apiMapper = apiMapper), NextApiDataSource {

    override suspend fun next(endpoint: WatchEndpoint, continuation: String?): Result<NextResult> =
        tryWithCatch {
            val client = WEB_REMIX
            val body = NextBody(
                context = client.toContext(locale, visitorData),
                videoId = endpoint.videoId,
                playlistId = endpoint.playlistId,
                playlistSetVideoId = endpoint.playlistSetVideoId,
                index = endpoint.index,
                params = endpoint.params,
                continuation = continuation
            )
            val queryMap = mapOf(
                "key" to client.api_key,
                "prettyPrint" to false
            )

            val headerMap = mutableMapOf(
                "X-Goog-Api-Format-Version" to "1",
                "X-YouTube-Client-Name" to client.clientName,
                "X-YouTube-Client-Version" to client.clientVersion,
                "x-origin" to "https://music.youtube.com",
                "User-Agent" to client.userAgent,
            )
            if (client.referer != null) {
                headerMap["Referer"] = client.referer.toString()
            }

            val apiResponse = nextService.next(
                body = body,
                queries = queryMap,
                headers = headerMap,
            )

            val responseCode = apiResponse.status.value

            if (responseCode in 200..299) {
                val response = apiResponse.body() ?: throw Exception(responseCode.toString())
                val title =
                    response.contents.singleColumnMusicWatchNextResultsRenderer.tabbedRenderer.watchNextTabbedResultsRenderer.tabs[0].tabRenderer.content?.musicQueueRenderer?.header?.musicQueueHeaderRenderer?.subtitle?.runs?.firstOrNull()?.text
                val playlistPanelRenderer = response.continuationContents?.playlistPanelContinuation
                    ?: response.contents.singleColumnMusicWatchNextResultsRenderer.tabbedRenderer.watchNextTabbedResultsRenderer.tabs[0].tabRenderer.content?.musicQueueRenderer?.content?.playlistPanelRenderer!!

                val items = playlistPanelRenderer.contents.mapNotNull { content ->
                    content.playlistPanelVideoRenderer
                        ?.let(NextPage::fromPlaylistPanelVideoRenderer)
                        ?.let { it to (content.playlistPanelVideoRenderer?.selected ?: false) }
                }
                val songs = items.map { it.first }
                val currentIndex = items.indexOfFirst { it.second }.takeIf { it != -1 }

                playlistPanelRenderer.contents.lastOrNull()?.automixPreviewVideoRenderer?.content?.automixPlaylistVideoRenderer?.navigationEndpoint?.watchPlaylistEndpoint?.let { watchPlaylistEndpoint ->
                    return@let Result.success(
                        next(watchPlaylistEndpoint).getOrThrow().let { result ->
                            result.copy(
                                title = title,
                                items = songs + result.items,
                                lyricsEndpoint = response.contents.singleColumnMusicWatchNextResultsRenderer.tabbedRenderer.watchNextTabbedResultsRenderer.tabs.getOrNull(
                                    1
                                )?.tabRenderer?.endpoint?.browseEndpoint,
                                relatedEndpoint = response.contents.singleColumnMusicWatchNextResultsRenderer.tabbedRenderer.watchNextTabbedResultsRenderer.tabs.getOrNull(
                                    2
                                )?.tabRenderer?.endpoint?.browseEndpoint,
                                currentIndex = currentIndex,
                                endpoint = watchPlaylistEndpoint
                            )
                        })
                }
                Result.success(
                    NextResult(
                        title = title,
                        items = songs,
                        currentIndex = currentIndex,
                        lyricsEndpoint = response.contents.singleColumnMusicWatchNextResultsRenderer.tabbedRenderer.watchNextTabbedResultsRenderer.tabs.getOrNull(
                            1
                        )?.tabRenderer?.endpoint?.browseEndpoint,
                        relatedEndpoint = response.contents.singleColumnMusicWatchNextResultsRenderer.tabbedRenderer.watchNextTabbedResultsRenderer.tabs.getOrNull(
                            2
                        )?.tabRenderer?.endpoint?.browseEndpoint,
                        continuation = playlistPanelRenderer.continuations?.getContinuation(),
                        endpoint = endpoint
                    )
                )


            } else {
                throw Exception(responseCode.toString())
            }
        }

}
