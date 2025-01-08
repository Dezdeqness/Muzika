package com.dezdeqness.innertube.models.pages

import com.dezdeqness.innertube.models.others.AlbumItem

data class ExplorePage(
    val newReleaseAlbums: List<AlbumItem>,
    val moodAndGenres: List<MoodAndGenres.Item>,
)
