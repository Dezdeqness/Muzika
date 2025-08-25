package com.dezdeqness.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dezdeqness.core.ui.theme.AppTheme
import com.dezdeqness.home.presentation.composables.HomeError
import com.dezdeqness.home.presentation.composables.HomeSection
import com.dezdeqness.home.presentation.composables.HomeShimmer
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomePageViewModel = koinViewModel(),
) {
    val state by viewModel.homeState.collectAsStateWithLifecycle()

    val isLoading = state.status == StateStatus.Loading || state.status == StateStatus.Initial
    val isError = state.status == StateStatus.Error

    Box(
        modifier
            .background(AppTheme.colors.onPrimary)
            .fillMaxSize()
    ) {
        if (isLoading) {
            HomeShimmer(modifier = Modifier.align(Alignment.Center))
        } else if (isError) {
            HomeError(
                modifier = Modifier.align(Alignment.Center),
                onAction = viewModel::onErrorRetry,
            )
        } else {
            LazyColumn(
                modifier = modifier,
            ) {

                item {
                    if (state.liked.isNotEmpty()) {
                        HomeSection(
                            title = "Liked playlists",
                            items = state.liked,
                        )
                    }
                }
                item {
                    HomeSection(
                        title = "Anime",
                        items = state.sectionAnime,
                    )
                }
                item {
                    HomeSection(
                        title = "Eurobeat",
                        items = state.sectionEurobeat,
                    )
                }
                item {
                    HomeSection(
                        title = "Phonk",
                        items = state.sectionPhonk,
                    )
                }
            }
        }
    }
}
