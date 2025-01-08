package com.dezdeqness.muzika.presentation.features.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dezdeqness.muzika.presentation.LocalPlaybackConnection
import com.dezdeqness.muzika.core.ui.ContentTile
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Search(
    viewModel: SearchViewModel = koinViewModel(),
) {
    val playbackConnection = LocalPlaybackConnection.current ?: return

    val isPlaying by playbackConnection.isPlaying.collectAsState()
    val mediaItem = playbackConnection.currentMediaItem.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        var query by remember {
            mutableStateOf("")
        }

        Column {

            SearchBar(
                query = query,
                placeholder = "Search songs",
                onQueryChanged = {
                    query = it
                    viewModel.onEmptyQuery()
                },
                onDoneAction = {
                    viewModel.performSearch(it)
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )

            val state = viewModel.searchState.collectAsState()

            if (state.value.items.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(items = state.value.items) { item ->
                        ContentTile(
                            title = item.title,
                            subTitle = item.subTitle,
                            iconUrl = item.iconUrl,
                            isDownloaded = true,
                            onMoreClicked = {

                            },
                            isCurrentlyPlaying = item.id == mediaItem.value?.mediaId,
                            isPlaying = isPlaying,
                            modifier = Modifier
                                .clip((RoundedCornerShape(12.dp)))
                                .clickable {
                                    playbackConnection.mediaController.let {
                                        viewModel.loadQuery(item.id, it)
                                    }
                                }
                        )
                    }
                }
            } else if (state.value.isEmptyScreenShown) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = "No results by your query",
                        color = Color.White,
                    )
                }
            }

        }
    }
}
