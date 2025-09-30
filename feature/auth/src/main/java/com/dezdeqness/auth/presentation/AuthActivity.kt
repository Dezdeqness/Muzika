package com.dezdeqness.auth.presentation

import android.content.Intent
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.bundle.Bundle
import com.dezdeqness.auth.navigation.AuthNavigation
import com.dezdeqness.auth.presentation.ui.AuthPage
import com.dezdeqness.core.ui.theme.FonoTheme
import org.koin.android.ext.android.inject

class AuthActivity : AppCompatActivity() {

    val viewModel: AuthViewModel by inject()
    val navigator: AuthNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handleDeepLink(intent)

        setContent {
            FonoTheme {
                AuthPage(
                    viewModel.authState,
                    viewModel.events,
                    onAuthorizeClick = {
                        viewModel.onAuthorizedClick()
                    },
                    onNavigationMainFlow = {
                        navigator.navigateToMainScreen(this)
                        finish()
                    }
                )
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
