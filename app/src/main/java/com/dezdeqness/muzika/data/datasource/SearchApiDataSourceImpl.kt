package com.dezdeqness.muzika.data.datasource

import com.dezdeqness.innertube.core.YouTube.SearchFilter
import com.dezdeqness.innertube.models.others.YouTubeClient.Companion.WEB_REMIX
import com.dezdeqness.innertube.models.others.getContinuation
import com.dezdeqness.innertube.models.pages.SearchPage
import com.dezdeqness.innertube.models.pages.SearchResult
import com.dezdeqness.innertube.models.request.SearchBody
import com.dezdeqness.muzika.data.core.BaseApiDataSource
import com.dezdeqness.muzika.data.mapper.ApiMapper
import com.dezdeqness.innertube.service.SearchService

class SearchApiDataSourceImpl(
    private val searchService: SearchService,
    apiMapper: ApiMapper,
) : BaseApiDataSource(apiMapper = apiMapper), SearchApiDataSource {

    override suspend fun search(
        query: String,
        filter: SearchFilter
    ): Result<SearchResult> = tryWithCatch {
        val client = WEB_REMIX
        val body = SearchBody(
            context = client.toContext(locale, visitorData),
            query = query,
            params = filter.value,
        )

//        val queryMap = mapOf<String, Any?>(
//            "continuation" to null,
//            "ctoken" to null
//        )

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

        val apiResponse = searchService.search(
            body = body,
            queries = mapOf(),
            headers = headerMap,
        )

        val responseCode = apiResponse.status.value

        if (responseCode in 200..299) {
            val response = apiResponse.body() ?: throw Exception(responseCode.toString())
            Result.success(
                SearchResult(
                    items = response.contents?.tabbedSearchResultsRenderer?.tabs?.firstOrNull()
                        ?.tabRenderer?.content?.sectionListRenderer?.contents?.lastOrNull()
                        ?.musicShelfRenderer?.contents?.mapNotNull {
                            SearchPage.toYTItem(it.musicResponsiveListItemRenderer)
                        }.orEmpty(),
                    continuation = response.contents?.tabbedSearchResultsRenderer?.tabs?.firstOrNull()
                        ?.tabRenderer?.content?.sectionListRenderer?.contents?.lastOrNull()
                        ?.musicShelfRenderer?.continuations?.getContinuation()
                )
            )
        } else {
            throw Exception(responseCode.toString())
        }

    }

}
