package com.dezdeqness.muzika.presentation

import android.annotation.SuppressLint
import android.content.ComponentName
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dezdeqness.muzika.core.ui.rememberBottomSheetState
import com.dezdeqness.muzika.presentation.features.home.Home
import com.dezdeqness.muzika.presentation.features.navigation.HOME_ROUTE
import com.dezdeqness.muzika.presentation.features.navigation.SEARCH_ROUTE
import com.dezdeqness.muzika.presentation.features.player.Player
import com.dezdeqness.muzika.presentation.features.search.Search
import com.dezdeqness.muzika.service.MusicService
import com.dezdeqness.muzika.service.PlaybackConnection
import com.google.common.util.concurrent.ListenableFuture

class MainActivity : AppCompatActivity() {

    private var mediaController: MediaController? = null
    private var controllerFuture: ListenableFuture<MediaController>? = null
    private var playbackConnection by mutableStateOf<PlaybackConnection?>(null)

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedBoxWithConstraintsScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView.rootView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }

        setContent {
            MaterialTheme {
                val rippleTheme = RippleConfiguration(
                    rippleAlpha = RippleAlpha(
                        pressedAlpha = 0.48f,
                        focusedAlpha = 0.48f,
                        draggedAlpha = 0.16f,
                        hoveredAlpha = 0.08f
                    ),
                    color = Color.White,
                )

                CompositionLocalProvider(
                    LocalRippleConfiguration provides rippleTheme,
                    LocalPlaybackConnection provides playbackConnection,
                ) {
                    val navController = rememberNavController()

                    BoxWithConstraints(
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxSize()
                    ) {
                        val playerBottomSheetState = rememberBottomSheetState(
                            dismissedBound = 0.dp,
                            collapsedBound = WindowInsets.navigationBars.asPaddingValues()
                                .calculateBottomPadding() + 72.dp,
                            expandedBound = maxHeight,
                        )

                        val connection =
                            LocalPlaybackConnection.current ?: return@BoxWithConstraints

                        val currentMediaItem = connection.currentMediaItem.collectAsState()

                        LaunchedEffect(currentMediaItem.value) {
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

                        NavHost(
                            navController = navController,
                            startDestination = HOME_ROUTE,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            composable(HOME_ROUTE) {
                                Home(navController = navController)
                            }
                            composable(SEARCH_ROUTE) {
                                Search()
                            }
                        }

                        Player(state = playerBottomSheetState)
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()

        val sessionToken = SessionToken(this, ComponentName(this, MusicService::class.java))
        controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture?.addListener({
            if (controllerFuture?.isDone == true) {
                mediaController = controllerFuture?.get()
                playbackConnection = PlaybackConnection(mediaController!!)

            }
        }, ContextCompat.getMainExecutor(this))

    }

    override fun onStop() {
        super.onStop()
        controllerFuture?.let { MediaController.releaseFuture(it) }
        playbackConnection?.dispose()
    }

}

val LocalPlaybackConnection =
    staticCompositionLocalOf<PlaybackConnection?> { error("No PlaybackConnection provided") }
