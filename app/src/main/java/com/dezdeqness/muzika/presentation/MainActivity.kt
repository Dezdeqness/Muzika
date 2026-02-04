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
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import coil3.imageLoader
import com.dezdeqness.auth.presentation.AuthActivity
import com.dezdeqness.core.network.event.AppEvent
import com.dezdeqness.core.network.event.AppEventHandler
import com.dezdeqness.core.player.locals.LocalPlaybackConnection
import com.dezdeqness.core.ui.theme.FonoTheme
import com.dezdeqness.core.ui.views.image.LocalAstImageLoader
import com.dezdeqness.home.navigation.homeScreen
import com.dezdeqness.home.navigation.routeHome
import com.dezdeqness.likedtracks.navigation.likedScreen
import com.dezdeqness.likedtracks.navigation.routeLiked
import com.dezdeqness.muzika.BuildConfig
import com.dezdeqness.muzika.presentation.composables.AppNavBar
import com.dezdeqness.player.core.rememberBottomSheetState
import com.dezdeqness.player.presentation.PlayerBottomSheet
import com.dezdeqness.player.presentation.PlayerControllerManager
import com.dezdeqness.playlist.navigation.Playlist
import com.dezdeqness.playlist.navigation.playlistScreen
import com.dezdeqness.settings.core.LocalVersionName
import com.dezdeqness.settings.navigation.routeSettings
import com.dezdeqness.settings.navigation.settingsScreen
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var controllerManager: PlayerControllerManager

    private val appEventHandler: AppEventHandler by inject()

    @androidx.annotation.OptIn(UnstableApi::class)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedBoxWithConstraintsScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controllerManager = PlayerControllerManager(this.applicationContext)
        controllerManager.connect()

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            val density = LocalDensity.current
            val heightPx = LocalWindowInfo.current.containerSize.height
            val heightDp = with(density) { heightPx.toDp() }
            CompositionLocalProvider(
                LocalVersionName provides BuildConfig.VERSION_NAME,
                LocalAstImageLoader provides this.imageLoader,
            ) {
                FonoTheme {
                    val playbackConnection by controllerManager.playbackConnection.collectAsStateWithLifecycle()

                    LaunchedEffect(Unit) {
                        appEventHandler.events.collect { event ->
                            when (event) {
                                AppEvent.SessionExpired -> {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            AuthActivity::class.java
                                        )
                                    )
                                    finishAffinity()
                                }
                            }
                        }
                    }

                    CompositionLocalProvider(LocalPlaybackConnection provides playbackConnection) {
                        val playerBottomSheetState = rememberBottomSheetState(
                            dismissedBound = 0.dp,
                            collapsedBound = 72.dp,
                            expandedBound = heightDp,
                        )


                        val animatedHeight by animateDpAsState(
                            targetValue = 80.dp * (1f - playerBottomSheetState.progress),
                            label = "BottomBarHeight"
                        )

                        val bottomInset = if (playerBottomSheetState.isCollapsed) 80.dp else 0.dp

                        Scaffold(
                            bottomBar = {
                                AppNavBar(
                                    height = animatedHeight,
                                    selectedTab = currentTab(navController),
                                    onTabSelected = { tab ->
                                        navController.switchTab(tab.graph)
                                    }
                                )
                            }
                        ) { padding ->
                            val hostPadding = PaddingValues(
                                start = padding.calculateLeftPadding(LayoutDirection.Ltr),
                                end = padding.calculateRightPadding(LayoutDirection.Ltr),
                                bottom = padding.calculateBottomPadding() + bottomInset,
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
                                    startDestination = BottomGraph.Home.route,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = padding.calculateTopPadding())
                                ) {

                                    navigation(
                                        startDestination = routeHome,
                                        route = BottomGraph.Home.route
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

                                        playlistScreen(
                                            onBackClicked = navController::popBackStack,
                                            onPlaylistChanged = { items ->
                                                val mediaItems = items.map { item ->
                                                    MediaItem.Builder()
                                                        .setMediaId(item.id)
                                                        .setUri(item.streamUrl)
                                                        .setCustomCacheKey(item.id)
                                                        .setTag(item)
                                                        .setMediaMetadata(
                                                            MediaMetadata.Builder()
                                                                .setTitle(item.name)
                                                                .setSubtitle(item.authorName)
                                                                .setArtist(item.authorName)
                                                                .setArtworkUri(item.iconImageUrl.toUri())
                                                                .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                                                                .build()
                                                        )
                                                        .build()
                                                }
                                                playbackConnection?.updatePlaylist(mediaItems)
                                            },
                                            onSongClick = { index ->
                                                playbackConnection?.startPlay(index)
                                            }
                                        )
                                    }

                                    navigation(
                                        startDestination = routeLiked,
                                        route = BottomGraph.Liked.route
                                    ) {
                                        likedScreen(
                                            onPlaylistChanged = { items ->
                                                val mediaItems = items.map { item ->
                                                    MediaItem.Builder()
                                                        .setMediaId(item.id)
                                                        .setUri(item.streamUrl)
                                                        .setCustomCacheKey(item.id)
                                                        .setTag(item)
                                                        .setMediaMetadata(
                                                            MediaMetadata.Builder()
                                                                .setTitle(item.name)
                                                                .setSubtitle(item.authorName)
                                                                .setArtist(item.authorName)
                                                                .setArtworkUri(item.iconImageUrl.toUri())
                                                                .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                                                                .build()
                                                        )
                                                        .build()
                                                }
                                                playbackConnection?.updatePlaylist(mediaItems)
                                            },
                                            onSongClick = { index ->
                                                playbackConnection?.startPlay(index)
                                            }
                                        )
                                    }

                                    navigation(
                                        startDestination = routeSettings,
                                        route = BottomGraph.Settings.route
                                    ) {
                                        settingsScreen(navController::popBackStack)
                                    }
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

    override fun onDestroy() {
        super.onDestroy()
        controllerManager.disconnect(isFinishing = isFinishing)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

}

sealed class BottomGraph(val route: String) {
    object Home : BottomGraph("home_graph")
    object Liked : BottomGraph("liked_graph")
    object Settings : BottomGraph("settings_graph")
}

enum class AquaBottomTabModel(val title: String, val graph: BottomGraph, val icon: ImageVector) {
    HOME("Home", BottomGraph.Home, Icons.Filled.Home),
    SAVED("Liked", BottomGraph.Liked, Icons.Filled.Star),
    SETTINGS("Settings", BottomGraph.Settings, Icons.Filled.Settings)
}


@Composable
fun currentTab(navController: NavController): AquaBottomTabModel {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination

    return when {
        destination?.hierarchy?.any { it.route == BottomGraph.Home.route } == true -> AquaBottomTabModel.HOME
        destination?.hierarchy?.any { it.route == BottomGraph.Liked.route } == true -> AquaBottomTabModel.SAVED
        destination?.hierarchy?.any { it.route == BottomGraph.Settings.route } == true -> AquaBottomTabModel.SETTINGS
        else -> AquaBottomTabModel.HOME
    }
}

fun NavController.switchTab(graph: BottomGraph) {
    if (currentDestination?.hierarchy?.any { it.route == graph.route } == true) return

    navigate(graph.route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(this@switchTab.graph.findStartDestination().id) {
            saveState = true
        }
    }
}