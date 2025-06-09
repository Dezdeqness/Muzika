package com.dezdeqness.likedtracks.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dezdeqness.likedtracks.presentation.model.LikedTrackUiModel
import com.dezdeqness.shared.ui.ContentTile
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LikedTrackPage(
    modifier: Modifier = Modifier,
    viewModel: LikedTracksViewModel = koinViewModel(),
    onSongClick: (LikedTrackUiModel) -> Unit,
) {
    val state by viewModel.likedTracks.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            when (state.status) {
                StateStatus.Loaded -> {
                    items(state.tracks.size) { index ->
                        val item = state.tracks[index]
                        ContentTile(
                            modifier = Modifier.clickable(
                                onClick = {
                                    onSongClick(item)
                                }
                            ),
                            title = item.name,
                            subTitle = item.authorName,
                            iconUrl = item.iconImageUrl,
                            onMoreClicked = {}
                        )
                    }
                }

                StateStatus.Loading, StateStatus.Initial -> {

                }

                StateStatus.Error -> {

                }
            }
        }
    }
}
