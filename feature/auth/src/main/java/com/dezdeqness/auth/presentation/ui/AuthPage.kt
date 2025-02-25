package com.dezdeqness.auth.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark

@Composable
fun AuthPage(
    modifier: Modifier = Modifier,
    onAuthorizeClick: () -> Unit,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center) {
            ElevatedButton(
                onClick = onAuthorizeClick,
            ) {
                Text("Authorize")
            }
        }
    }

}

@PreviewLightDark
@Composable
fun AuthPagePreview() {
    MaterialTheme {
        AuthPage {  }
    }
}