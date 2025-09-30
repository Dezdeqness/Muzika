package com.dezdeqness.home.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dezdeqness.core.ui.views.header.Header
import com.dezdeqness.core.ui.views.sections.AppSections
import com.dezdeqness.home.presentation.PlaylistTransferObject
import com.dezdeqness.home.presentation.model.HomePlaylistUiModel

@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    title: String,
    items: List<HomePlaylistUiModel>,
    onPlaylistClicked: (PlaylistTransferObject) -> Unit,
) {

    AppSections(
        modifier = modifier,
        header = {
            Header(title = title)
        },
        items = items,
        itemContent = { item ->
            HomeSectionItem(
                item = item,
                onPlaylistClicked = onPlaylistClicked,
            )
        }
    )
}
