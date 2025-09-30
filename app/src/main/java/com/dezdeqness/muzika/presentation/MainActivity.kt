package com.dezdeqness.muzika.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.imageLoader
import com.dezdeqness.core.player.locals.LocalPlaybackConnection
import com.dezdeqness.core.ui.theme.FonoTheme
import com.dezdeqness.core.ui.views.image.LocalAstImageLoader
import com.dezdeqness.home.navigation.Home
import com.dezdeqness.home.navigation.homeScreen
import com.dezdeqness.likedtracks.navigation.Liked
import com.dezdeqness.likedtracks.navigation.likedScreen
import com.dezdeqness.muzika.BuildConfig
import com.dezdeqness.muzika.presentation.composables.AppNavBar
import com.dezdeqness.player.core.rememberBottomSheetState
import com.dezdeqness.player.presentation.PlayerBottomSheet
import com.dezdeqness.player.presentation.PlayerControllerManager
import com.dezdeqness.playlist.navigation.Playlist
import com.dezdeqness.playlist.navigation.playlistScreen
import com.dezdeqness.settings.core.LocalVersionName
import com.dezdeqness.settings.navigation.Settings
import com.dezdeqness.settings.navigation.settingsScreen
import kotlinx.serialization.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var controllerManager: PlayerControllerManager

    @androidx.annotation.OptIn(UnstableApi::class)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedBoxWithConstraintsScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controllerManager = PlayerControllerManager(this.applicationContext)
        controllerManager.connect()

        enableEdgeToEdge()

        setContent {
            val rootController = rememberNavController()

            val density = LocalDensity.current
            val heightPx = LocalWindowInfo.current.containerSize.height
            val heightDp = with(density) { heightPx.toDp() }
            CompositionLocalProvider(
                LocalVersionName provides BuildConfig.VERSION_NAME,
                LocalAstImageLoader provides this.imageLoader,
            ) {
                FonoTheme {
                    val playbackConnection by controllerManager.playbackConnection.collectAsStateWithLifecycle()

                    CompositionLocalProvider(LocalPlaybackConnection provides playbackConnection) {
                        val playerBottomSheetState = rememberBottomSheetState(
                            dismissedBound = 0.dp,
                            collapsedBound = 72.dp,
                            expandedBound = heightDp,
                        )

                        NavHost(
                            navController = rootController,
                            startDestination = Root,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            composable<Root> {
                                val navController = rememberNavController()

                                val animatedHeight by animateDpAsState(
                                    targetValue = 80.dp * (1f - playerBottomSheetState.progress),
                                    label = "BottomBarHeight"
                                )

                                Scaffold(
                                    bottomBar = {
                                        AppNavBar(
                                            height = animatedHeight,
                                            navController = navController,
                                        )
                                    }
                                ) { padding ->

                                    val hostPadding = PaddingValues(
                                        start = padding.calculateLeftPadding(LayoutDirection.Ltr),
                                        end = padding.calculateRightPadding(LayoutDirection.Ltr),
                                        bottom = padding.calculateBottomPadding(),
                                    )
                                    BoxWithConstraints(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(hostPadding)
                                    ) {
                                        val height = remember {
                                            derivedStateOf { maxHeight }
                                        }
                                        LaunchedEffect(height.value) {
                                            playerBottomSheetState.updateBounds(
                                                0.dp,
                                                height.value
                                            )
                                        }

                                        NavHost(
                                            navController = navController,
                                            startDestination = Home,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(top = padding.calculateTopPadding())
                                        ) {
                                            homeScreen(
                                                onPlaylistClicked = {
                                                    navController.navigate(
                                                        Playlist(
                                                            id = it.id,
                                                            title = it.title,
                                                            userName = it.userName,
                                                            imageUrl = it.imageUrl,
                                                            urn = it.urn,
                                                            description = it.description,
                                                            duration = it.duration,
                                                            tracksCount = it.tracksCount,
                                                        )
                                                    )
                                                }
                                            )

                                            likedScreen(
                                                onPlaylistChanged = { items ->
                                                    val mediaItems = items.map { item ->
                                                        MediaItem
                                                            .Builder()
                                                            .setMediaId(item.id)
                                                            .setUri(item.streamUrl)
                                                            .setCustomCacheKey(item.id)
                                                            .setTag(item)
                                                            .setMediaMetadata(
                                                                MediaMetadata
                                                                    .Builder()
                                                                    .setTitle(item.name)
                                                                    .setSubtitle(item.authorName)
                                                                    .setArtist(item.authorName)
                                                                    .setArtworkUri(item.iconImageUrl.toUri())
                                                                    .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                                                                    .build()
                                                            )
                                                            .build()
                                                    }

                                                    playbackConnection?.updatePlaylist(
                                                        mediaItems
                                                    )
                                                },
                                                onSongClick = { index ->
                                                    playbackConnection?.startPlay(index)
                                                }
                                            )

                                            playlistScreen(
                                                onBackClicked = navController::popBackStack,
                                                onPlaylistChanged = { items ->
                                                    val mediaItems = items.map { item ->
                                                        MediaItem
                                                            .Builder()
                                                            .setMediaId(item.id)
                                                            .setUri(item.streamUrl)
                                                            .setCustomCacheKey(item.id)
                                                            .setTag(item)
                                                            .setMediaMetadata(
                                                                MediaMetadata
                                                                    .Builder()
                                                                    .setTitle(item.name)
                                                                    .setSubtitle(item.authorName)
                                                                    .setArtist(item.authorName)
                                                                    .setArtworkUri(item.iconImageUrl.toUri())
                                                                    .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                                                                    .build()
                                                            )
                                                            .build()
                                                    }

                                                    playbackConnection?.updatePlaylist(
                                                        mediaItems
                                                    )
                                                },
                                                onSongClick = { index ->
                                                    playbackConnection?.startPlay(index)
                                                }
                                            )

                                            settingsScreen(navController::popBackStack)
                                        }

                                        val currentMediaItem =
                                            playbackConnection?.currentMediaItem?.collectAsStateWithLifecycle()

                                        LaunchedEffect(currentMediaItem?.value) {
                                            val mediaItem = playbackConnection?.currentMediaItem
                                            if (mediaItem == null) {
                                                if (!playerBottomSheetState.isDismissed) {
                                                    playerBottomSheetState.dismiss()
                                                }
                                            } else {
                                                if (playerBottomSheetState.isDismissed) {
                                                    playerBottomSheetState.collapseSoft()
                                                }
                                            }
                                        }

                                        PlayerBottomSheet(state = playerBottomSheetState)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        controllerManager.disconnect(isFinishing = isFinishing)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

}

enum class AquaBottomTabModel(val title: String, val route: Any, val icon: ImageVector) {
    HOME("Home", Home, Icons.Filled.Home),
    SAVED("Liked", Liked, Icons.Filled.Star),
    SETTINGS("Settings", Settings, Icons.Filled.Settings)
}

@Serializable
object Root
