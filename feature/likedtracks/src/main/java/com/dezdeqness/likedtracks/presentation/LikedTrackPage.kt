package com.dezdeqness.likedtracks.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dezdeqness.core.player.locals.LocalPlaybackConnection
import com.dezdeqness.likedtracks.presentation.model.LikedTrackUiModel
import com.dezdeqness.shared.ui.ContentTile
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LikedTrackPage(
    modifier: Modifier = Modifier,
    viewModel: LikedTracksViewModel = koinViewModel(),
    onSongClick: (Int) -> Unit,
    onPlaylistChanged: (List<LikedTrackUiModel>) -> Unit,
) {
    val playbackConnection = LocalPlaybackConnection.current ?: return

    val mediaItem by playbackConnection.currentMediaItem.collectAsStateWithLifecycle()

    val isPlaying by playbackConnection.isPlaying.collectAsStateWithLifecycle()

    val state = viewModel.likedTracks.collectAsLazyPagingItems()

    val isAppending = state.loadState.append is LoadState.Loading

    val isRefreshing = state.loadState.refresh is LoadState.Loading

    // TODO: make separate playlist entity
    LaunchedEffect(isPlaying, state.itemSnapshotList.items) {
        if (isPlaying) {
            onPlaylistChanged(state.itemSnapshotList.items)
        }
    }

    LaunchedEffect(mediaItem, isPlaying, state.itemSnapshotList.items, state.loadState) {
        if (isPlaying.not()) return@LaunchedEffect

        val index = state.itemSnapshotList.items.indexOfFirst { it.id == mediaItem?.mediaId }
        if (index != -1 && index == state.itemSnapshotList.items.lastIndex) {
            if (state.loadState.append.endOfPaginationReached) return@LaunchedEffect
            
            state[index + 1]
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(
                state.itemCount,
                key = { index ->
                    val item = state.peek(index)
                    item?.id ?: "placeholder_$index"
                }
            ) { index ->
                val item = state[index] ?: return@items
                ContentTile(
                    modifier = Modifier.clickable(
                        onClick = {
                            onPlaylistChanged(state.itemSnapshotList.items)
                            onSongClick(state.itemSnapshotList.items.indexOf(item))
                        }
                    ),
                    title = item.name,
                    subTitle = item.authorName,
                    iconUrl = item.iconImageUrl,
                    isCurrentSong = item.id == mediaItem?.mediaId,
                    isCurrentlyPlaying = isPlaying,
                    onMoreClicked = {}
                )
            }

            if (isAppending) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        if (isRefreshing && state.itemCount == 0) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
