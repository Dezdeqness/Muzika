package com.dezdeqness.playlist.presentattion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dezdeqness.core.player.locals.LocalPlaybackConnection
import com.dezdeqness.playlist.presentattion.composables.CollapseHeaderLayout
import com.dezdeqness.playlist.presentattion.composables.CollapseNestedScrollConnection
import com.dezdeqness.playlist.presentattion.composables.PlaylistCollapsedHeader
import com.dezdeqness.playlist.presentattion.composables.PlaylistHeader
import com.dezdeqness.playlist.presentattion.model.PlaylistTrackUiModel
import com.dezdeqness.shared.ui.ContentTile
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistPage(
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel = koinViewModel(),
    onSongClick: (Int) -> Unit,
    onPlaylistChanged: (List<PlaylistTrackUiModel>) -> Unit,
    onBackClicked: () -> Unit,
) {
    val playbackConnection = LocalPlaybackConnection.current ?: return

    val connection = remember {
        CollapseNestedScrollConnection()
    }

    val state by viewModel.playlistState.collectAsStateWithLifecycle()

    val tracks = viewModel.playlistTracks.collectAsLazyPagingItems()

    val mediaItem by playbackConnection.currentMediaItem.collectAsStateWithLifecycle()

    val isPlaying by playbackConnection.isPlaying.collectAsStateWithLifecycle()

    val isAppending = tracks.loadState.append is LoadState.Loading

    val isRefreshing = tracks.loadState.refresh is LoadState.Loading

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(connection)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(
                    PaddingValues(
                        start = padding.calculateLeftPadding(LayoutDirection.Ltr),
                        end = padding.calculateRightPadding(LayoutDirection.Ltr),
                    )
                )
                .scrollable(
                    orientation = Orientation.Vertical,
                    state = rememberScrollableState { delta -> 0f }
                )
        ) {
            CollapseHeaderLayout(
                connection = connection,
                collapseHeader = {
                    PlaylistCollapsedHeader(
                        connection = connection,
                        title = state.title,
                        onClick = onBackClicked,
                    )
                },
                expandHeader = {
                    PlaylistHeader(
                        state = state,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                }
            )

            Box(
                modifier = modifier.fillMaxSize(),
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(
                        tracks.itemCount,
                        key = { index ->
                            val item = tracks.peek(index)
                            item?.id ?: "placeholder_$index"
                        }
                    ) { index ->
                        val item = tracks[index] ?: return@items
                        ContentTile(
                            modifier = Modifier.clickable(
                                onClick = {
                                    val items = tracks.itemSnapshotList.items
                                    onPlaylistChanged(items)
                                    onSongClick(items.indexOf(item))
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

                if (isRefreshing && tracks.itemCount == 0) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

}
