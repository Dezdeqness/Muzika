package com.dezdeqness.muzika.presentation.features.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dezdeqness.muzika.presentation.LocalPlaybackConnection
import com.dezdeqness.muzika.presentation.features.navigation.SEARCH_ROUTE
import com.dezdeqness.muzika.core.ui.GeneralHeader
import com.dezdeqness.muzika.core.ui.ContentTile
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val playbackConnection = LocalPlaybackConnection.current ?: return
    val configuration = LocalConfiguration.current

    val isPlaying by playbackConnection.isPlaying.collectAsState()
    val mediaItem = playbackConnection.currentMediaItem.collectAsState()
    val state = viewModel.homeState.collectAsState()

    val lazyGridState = rememberLazyGridState()

    Box(modifier = Modifier.fillMaxSize()) {
        val maxWidth = 400.dp

        val orientation = configuration.orientation
        val widthGridFactor = if (orientation == Configuration.ORIENTATION_PORTRAIT) 0.9f else 0.45f

        Column {

            TopAppBar(
                title = {
                    Text("Muzika")
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(SEARCH_ROUTE)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            tint = Color.White,
                            contentDescription = null,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color.White,
                    containerColor = Color.Black,
                )
            )

            GeneralHeader(
                text = "Quick picks",
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .fillMaxWidth()
            )

            LazyHorizontalGrid(
                state = lazyGridState,
                rows = GridCells.Fixed(5),
                contentPadding = PaddingValues(horizontal = 12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp * 5)
            ) {
                items(state.value.items) { item ->
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
                            .width(maxWidth * widthGridFactor)
                            .clip((RoundedCornerShape(12.dp)))
                            .clickable {
                                playbackConnection.mediaController.let {
                                    viewModel.loadQuery(item.id, it)
                                }
                            }
                    )
                }
            }
        }
    }

}