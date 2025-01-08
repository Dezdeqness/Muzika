package com.dezdeqness.innertube.models.others

data class SearchSuggestions(
    val queries: List<String>,
    val recommendedItems: List<YTItem>,
)
