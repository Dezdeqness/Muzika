package com.dezdeqness.muzika.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dezdeqness.core.player.locals.LocalPlaybackConnection
import com.dezdeqness.core.ui.theme.AppTheme
import com.dezdeqness.likedtracks.navigation.LIKED_ROUTE
import com.dezdeqness.likedtracks.navigation.likedScreen
import com.dezdeqness.player.core.rememberBottomSheetState
import com.dezdeqness.player.presentation.PlayerBottomSheet
import com.dezdeqness.player.presentation.PlayerControllerManager

class MainActivity : AppCompatActivity() {

    private lateinit var controllerManager: PlayerControllerManager

    @androidx.annotation.OptIn(UnstableApi::class)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedBoxWithConstraintsScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controllerManager = PlayerControllerManager(this.applicationContext)
        controllerManager.connect()

        WindowCompat.setDecorFitsSystemWindows(window, false)
//        WindowCompat.getInsetsController(window, window.decorView.rootView).apply {
//            isAppearanceLightStatusBars = false
//            isAppearanceLightNavigationBars = false
//        }

        setContent {
            val rootController = rememberNavController()

            AppTheme {
                val playbackConnection by controllerManager.playbackConnection.collectAsStateWithLifecycle()
                CompositionLocalProvider(LocalPlaybackConnection provides playbackConnection) {
                    NavHost(
                        navController = rootController,
                        startDestination = "root",
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        composable(route = "root") {
                            val navController = rememberNavController()

                            val currentDestination =
                                navController.currentBackStackEntryAsState().value?.destination?.route
                            Scaffold(
                                bottomBar = {
                                    NavigationBar(
                                        containerColor = MaterialTheme.colorScheme.background,
                                        tonalElevation = 0.dp,
                                    ) {
                                        AquaBottomTabModel.entries.forEach { item ->
                                            NavigationBarItem(
                                                label = {
                                                    Text(item.title)
                                                },
                                                selected = currentDestination == item.route,
                                                onClick = {
                                                    if (currentDestination != item.route) {
                                                        navController.navigate(item.route) {
                                                            popUpTo(navController.graph.startDestinationId) {
                                                                saveState = true
                                                            }
                                                            launchSingleTop = true
                                                            restoreState = true
                                                        }
                                                    }
                                                },
                                                icon = {

                                                },
                                            )
                                        }
                                    }
                                }
                            ) { padding ->
                                BoxWithConstraints(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(padding)
                                ) {
                                    NavHost(
                                        navController = navController,
                                        startDestination = "home",
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        composable("home") {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(Color.Green)
                                            )
                                        }
                                        likedScreen(
                                            onSongClick = { item ->
                                                val mediaItem = MediaItem
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
                                                playbackConnection?.startPlay(mediaItem)

                                            }
                                        )
                                        composable("settings") {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(Color.Cyan)
                                            )
                                        }
                                    }

                                    val playerBottomSheetState = rememberBottomSheetState(
                                        dismissedBound = 0.dp,
                                        collapsedBound = 72.dp,
                                        expandedBound = maxHeight,
                                    )

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

    override fun onDestroy() {
        super.onDestroy()
        controllerManager.disconnect(isFinishing = isFinishing)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

}

enum class AquaBottomTabModel(val title: String, val route: String) {
    HOME("Home", "home"),
    SAVED("Liked", LIKED_ROUTE),
    SETTINGS("Settings", "settings")
}
