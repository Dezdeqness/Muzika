package com.dezdeqness.muzika.data.datasource

import com.dezdeqness.innertube.core.YouTube.SearchFilter
import com.dezdeqness.innertube.models.pages.SearchResult

interface SearchApiDataSource {

    suspend fun search(
        query: String,
        filter: SearchFilter
    ): Result<SearchResult>

}
