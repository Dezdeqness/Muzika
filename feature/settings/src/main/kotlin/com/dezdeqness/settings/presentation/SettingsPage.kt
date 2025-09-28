package com.dezdeqness.settings.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.imageLoader
import com.dezdeqness.core.ui.theme.AppTheme
import com.dezdeqness.core.ui.views.dialogs.SingleChoiceDialog
import com.dezdeqness.core.ui.views.settings.HeaderSettingsView
import com.dezdeqness.core.ui.views.settings.ProgressSettingsView
import com.dezdeqness.core.ui.views.settings.TextSettingsView
import com.dezdeqness.settings.core.LocalVersionName
import com.dezdeqness.settings.core.formatFileSize
import com.dezdeqness.settings.domain.models.ImageCacheMaxSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsPage(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val versionName = LocalVersionName.current

    val imageDiskCache = context.imageLoader.diskCache ?: return

    val imageCacheMaxSize by settingsViewModel
        .observe(ImageCacheMaxSize)
        .collectAsStateWithLifecycle(0)

    var imageCacheSize by remember(imageCacheMaxSize) {
        mutableLongStateOf(imageDiskCache.size)
    }

    val imageCacheProgress by animateFloatAsState(
        targetValue = (imageCacheSize.toFloat() / imageDiskCache.maxSize).coerceIn(0f, 1f),
        label = ""
    )

    LaunchedEffect(imageDiskCache) {
        while (isActive) {
            delay(500)
            imageCacheSize = imageDiskCache.size
        }
    }

    LazyColumn(
        modifier
            .background(AppTheme.colors.onPrimary)
            .fillMaxSize()
    ) {
        item {
            HeaderSettingsView(title = "Storage")
        }

        item {
            ProgressSettingsView(
                modifier = Modifier.fillMaxWidth(),
                title = "Image cache",
                subtitle = "${formatFileSize(imageCacheSize)} / ${formatFileSize(imageDiskCache.maxSize)} used",
                progress = imageCacheProgress,
            )
        }

        item {
            var isDialogOpened by rememberSaveable {
                mutableStateOf(false)
            }

            TextSettingsView(
                title = "Max cache size",
                subtitle = formatFileSize(imageDiskCache.maxSize),
                onClick = {
                    isDialogOpened = true
                }
            )

            if (isDialogOpened) {
                SingleChoiceDialog(
                    values = (7..13).map { 1 shl it },
                    selectedValue = imageCacheMaxSize,
                    valueText = { formatFileSize(it * 1024 * 1024L) },
                    onValueSelected = {
                        isDialogOpened = false
                        settingsViewModel.set(ImageCacheMaxSize, it)
                    },
                    onDismiss = {
                        isDialogOpened = false
                    },
                )
            }
        }

        item {
            TextSettingsView(
                title = "Clear image cache",
                onClick = {
                    imageDiskCache.clear()
                }
            )
        }

        item {
            HeaderSettingsView(title = "About")
        }
        item {
            TextSettingsView(
                title = "Version",
                subtitle = versionName,
            )
        }
    }
}
