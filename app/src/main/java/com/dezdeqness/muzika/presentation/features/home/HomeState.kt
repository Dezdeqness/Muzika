package com.dezdeqness.muzika.presentation.features.home

import com.dezdeqness.muzika.presentation.models.SongUiModel
data class HomeState(
    val items: List<SongUiModel> = listOf(),
)
