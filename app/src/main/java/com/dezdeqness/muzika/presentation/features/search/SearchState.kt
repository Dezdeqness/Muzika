package com.dezdeqness.muzika.presentation.features.search

import com.dezdeqness.muzika.presentation.models.SongUiModel

data class SearchState(
    val items: List<SongUiModel> = listOf(),
    val isEmptyScreenShown: Boolean = false,
)
