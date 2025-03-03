package com.dezdeqness.auth.presentation.ui

import androidx.browser.customtabs.CustomTabsIntent.*
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.net.toUri
import com.dezdeqness.auth.presentation.AuthEvent
import com.dezdeqness.auth.presentation.AuthState
import com.dezdeqness.auth.presentation.ScreenState
import com.dezdeqness.auth.utils.CollectEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthPage(
    state: StateFlow<AuthState>,
    events: Flow<AuthEvent>,
    modifier: Modifier = Modifier,
    onAuthorizeClick: () -> Unit,
    onNavigationMainFlow: () -> Unit,
) {
    val context = LocalContext.current

    val localState by state.collectAsState()

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF00BCD4), Color(0xFF0288D1))
    )

    val isContentVisible = localState.state != ScreenState.Initial

    Scaffold(modifier = modifier.fillMaxSize()) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(padding)
                .padding(32.dp),
        ) {
            if (isContentVisible) {
                Text(
                    text = "Aqua",
                    fontSize = 48.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                )

                Column(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Button(
                        onClick = {
                            onAuthorizeClick()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF0288D1)
                        )
                    ) {
                        Text("Login", fontSize = 18.sp)
                    }

                    OutlinedButton(
                        onClick = { onAuthorizeClick() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("Sign Up", fontSize = 18.sp)
                    }
                }
            }

            if (localState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    val animatedList = remember {
                        listOf(
                            Animatable(0f),
                            Animatable(0f),
                            Animatable(0f),
                        )
                    }

                    LaunchedEffect(Unit) {
                        animatedList.forEach { item ->
                            launch {
                                while (true) {
                                    item.animateTo(Random.nextFloat() * 1f)
                                    delay(100)
                                }
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.size(48.dp),
                    ) {
                        animatedList.forEach { item ->
                            Box(
                                modifier = Modifier
                                    .width(8.dp)
                                    .height(item.value * 16.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color.White)
                            ) {}
                        }
                    }
                }
            }

        }
    }

    events.CollectEvents { event ->
        when (event) {
            AuthEvent.NavigateMainFlow -> {
                onNavigationMainFlow()
            }

            is AuthEvent.OpenUrl -> {
                val customTabsIntent = Builder().build()
                customTabsIntent.launchUrl(context, event.url.toUri())
            }

            AuthEvent.Failure -> {

            }
        }
    }
}

@Preview
@Composable
fun AuthPagePreview() {
    MaterialTheme {
        AuthPage(
            state = MutableStateFlow(AuthState()),
            events = flowOf(),
            onNavigationMainFlow = {},
            onAuthorizeClick = {}
        )
    }
}