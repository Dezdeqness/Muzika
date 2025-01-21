package com.dezdeqness.muzika.presentation

import android.content.ComponentName
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
//import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dezdeqness.muzika.presentation.features.home.Home
import com.dezdeqness.muzika.presentation.features.navigation.HOME_ROUTE
import com.dezdeqness.muzika.presentation.features.navigation.PLAYER_ROUTE
import com.dezdeqness.muzika.presentation.features.navigation.SEARCH_ROUTE
import com.dezdeqness.muzika.presentation.features.player.MiniPlayer
import com.dezdeqness.muzika.presentation.features.player.Player
import com.dezdeqness.muzika.presentation.features.search.Search
import com.dezdeqness.muzika.service.MusicService
import com.dezdeqness.muzika.service.PlaybackConnection
import com.google.common.util.concurrent.ListenableFuture

class MainActivity : AppCompatActivity() {

    private var mediaController: MediaController? = null
    private var controllerFuture: ListenableFuture<MediaController>? = null
    private var playbackConnection by mutableStateOf<PlaybackConnection?>(null)

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
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    Box(
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxSize()
                    ) {

                        var isMiniPlayerVisible by remember {
                            mutableStateOf(false)
                        }


                        isMiniPlayerVisible = when (navBackStackEntry?.destination?.route) {
                            PLAYER_ROUTE -> {
                                false
                            }

                            else -> {
                                true
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

                            composable(PLAYER_ROUTE) {
                                Player()
                            }

                            composable(SEARCH_ROUTE) {
                                Search()
                            }
                        }

                        MiniPlayer(
                            isVisible = isMiniPlayerVisible,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.BottomCenter),
                            onPlayerClicked = {
                                navController.navigate(PLAYER_ROUTE)
                            },
                        )
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
