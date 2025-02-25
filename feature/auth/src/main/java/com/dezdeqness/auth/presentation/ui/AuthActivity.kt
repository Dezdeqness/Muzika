package com.dezdeqness.auth.presentation.ui

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.MaterialTheme
import androidx.core.bundle.Bundle
import com.dezdeqness.auth.presentation.AuthViewModel
import org.koin.android.ext.android.inject
import kotlin.getValue
import androidx.core.net.toUri
import com.dezdeqness.auth.data.provider.AuthorizationUrlProvider
import com.dezdeqness.auth.utils.PKCEUtils.generateCodeChallenge
import com.dezdeqness.auth.utils.PKCEUtils.generateCodeVerifier

class AuthActivity : ComponentActivity() {

    val viewModel: AuthViewModel by inject()

    val authUrlProvider: AuthorizationUrlProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeepLink(intent)

        setContent {
            MaterialTheme {
                AuthPage(
                    onAuthorizeClick = {
                        val verifier = generateCodeVerifier()
                        val challenge = generateCodeChallenge(verifier)

                        val url = authUrlProvider.composeAuthUrl(codeChallenge = challenge, "auth")

                        val customTabsIntent = CustomTabsIntent.Builder().build()
                        customTabsIntent.launchUrl(this, url.toUri())

//                        viewModel.onAuthorizedClick()
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
        Log.d("deeplink", intent.toString())
        intent.data?.let { uri ->
            if (uri.scheme == "dezdeqness" && uri.host == "aqua" && uri.path == "/auth") {
                Toast.makeText(this, "Auth successful!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
