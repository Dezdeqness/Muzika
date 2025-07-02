package com.dezdeqness.shared.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun ContentTile(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    iconUrl: String,
    isDownloaded: Boolean = false,
    isCurrentSong: Boolean = false,
    isCurrentlyPlaying: Boolean,
    onMoreClicked: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                remember(iconUrl) { iconUrl },
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(60.dp)
                    .clip(RoundedCornerShape(4.dp)),
                colorFilter = if (isCurrentSong) {
                    ColorFilter.tint(
                        Color.Black.copy(alpha = 0.5f),
                        BlendMode.Darken
                    )
                } else {
                    null
                }
            )
            if (isCurrentSong) {
                val animatedList = remember(isCurrentlyPlaying) {
                    listOf(
                        Animatable(0.1f),
                        Animatable(0.1f),
                        Animatable(0.1f),
                    )
                }

                if (isCurrentlyPlaying) {
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
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.size(24.dp),
                ) {
                    animatedList.forEach { item ->
                        Box(
                            modifier = Modifier
                                .width(6.dp)
                                .height(item.value * 16.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color.White)
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                fontSize = 14.sp,
                maxLines = 1,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isDownloaded) {
                    Icon(
                        painterResource(id = R.drawable.ic_download_offline),
                        tint = Color.Black,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 4.dp)
                    )
                }

                Text(
                    subTitle,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        IconButton(
            onClick = {
                onMoreClicked()
            },
        ) {
            Icon(
                painterResource(id = R.drawable.ic_more),
                tint = Color.Black,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
        }

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun ContentTilePreview() {
    MaterialTheme {
        ContentTile(
            title = "Landmine",
            subTitle = "Three Days Grace",
            iconUrl = "https://thicc.mywaifulist.moe/pending/waifus/xtW1KUiJlD3LTH2arqNTUlKYofAfzTm6RexN9jdA.jpg",
            isDownloaded = true,
            onMoreClicked = {

            },
            isCurrentlyPlaying = true,
            modifier = Modifier
                .width(320.dp)
                .clip((RoundedCornerShape(12.dp)))
        )
    }
}