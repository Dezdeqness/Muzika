package com.dezdeqness.auth.presentation

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.MaterialTheme
import androidx.core.bundle.Bundle
import androidx.core.net.toUri
import com.dezdeqness.auth.presentation.ui.AuthPage
import com.dezdeqness.auth.utils.CollectEvents
import org.koin.android.ext.android.inject

class AuthActivity : ComponentActivity() {

    val viewModel: AuthViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeepLink(intent)

        setContent {
            MaterialTheme {
                AuthPage(
                    onAuthorizeClick = {
                        viewModel.onAuthorizedClick()
                    }
                )

                viewModel.events.CollectEvents { event ->
                    when (event) {
                        AuthEvent.NavigateMainFlow -> {}
                        is AuthEvent.OpenUrl -> {
                            val customTabsIntent = CustomTabsIntent.Builder().build()
                            customTabsIntent.launchUrl(this, event.url.toUri())
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent) {
        intent.data?.let { uri ->
            viewModel.onHandleDeeplink(uri.toString())
        }
    }

}
