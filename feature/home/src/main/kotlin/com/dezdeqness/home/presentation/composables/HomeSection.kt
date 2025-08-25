package com.dezdeqness.home.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dezdeqness.core.ui.views.header.Header
import com.dezdeqness.home.presentation.model.HomePlaylistUiModel

@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    title: String,
    items: List<HomePlaylistUiModel>,
) {
    Column(modifier = modifier) {
        Header(title = title)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier,
        ) {
            items(
                count = items.size,
                key = { index -> items[index].id },
            ) { index ->
                val item = items[index]

                val paddingStart = if (index == 0) 0.dp else 4.dp
                val paddingEnd = if (index < items.size - 1) 4.dp else 0.dp

                HomeSectionItem(
                    modifier = Modifier.padding(start = paddingStart, end = paddingEnd),
                    item = item,
                )
            }
        }
    }
}
