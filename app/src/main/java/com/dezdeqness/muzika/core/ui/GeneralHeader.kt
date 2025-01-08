package com.dezdeqness.muzika.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun GeneralHeader(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = Color(0xFFFFFFFF),
            modifier = Modifier.weight(1f),
        )
    }
}