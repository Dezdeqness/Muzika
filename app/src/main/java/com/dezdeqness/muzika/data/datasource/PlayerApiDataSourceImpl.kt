package com.dezdeqness.muzika.data.datasource

import com.dezdeqness.innertube.models.others.Context
import com.dezdeqness.innertube.models.others.YouTubeClient
import com.dezdeqness.innertube.models.others.YouTubeClient.Companion.ANDROID_MUSIC
import com.dezdeqness.innertube.models.others.YouTubeClient.Companion.TVHTML5
import com.dezdeqness.innertube.models.request.PlayerBody
import com.dezdeqness.innertube.models.response.PlayerResponse
import com.dezdeqness.muzika.data.core.BaseApiDataSource
import com.dezdeqness.muzika.data.mapper.ApiMapper
import com.dezdeqness.innertube.service.PlayerService


class PlayerApiDataSourceImpl(
    private val playerService: PlayerService,
    apiMapper: ApiMapper,
) : BaseApiDataSource(apiMapper = apiMapper), PlayerApiDataSource {

    override suspend fun player(videoId: String, playlistId: String?): Result<PlayerResponse> =
        tryWithCatch {
            val client = ANDROID_MUSIC

            val body = PlayerBody(
                context = client.toContext(locale, visitorData).let {
                    if (client == TVHTML5) {
                        it.copy(
                            thirdParty = Context.ThirdParty(
                                embedUrl = "https://www.youtube.com/watch?v=${videoId}"
                            )
                        )
                    } else it
                },
                videoId = videoId,
                playlistId = playlistId
            )
            val queryMap = mapOf(
//                "key" to client.api_key,
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

            val response = playerService.player(
                body = body,
                headers = headerMap,
                queries = queryMap,
            )

            val responseCode = response.status.value

            if (responseCode in 200..299) {
                val playerResponse = response.body() ?: throw Exception(responseCode.toString())
                Result.success(playerResponse)
            } else {
                throw Exception(responseCode.toString())
            }
        }

}
