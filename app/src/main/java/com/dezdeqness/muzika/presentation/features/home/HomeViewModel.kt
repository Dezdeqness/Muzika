package com.dezdeqness.muzika.presentation.features.home

import android.util.Log
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import com.dezdeqness.innertube.core.YouTube
import com.dezdeqness.innertube.models.others.SongItem
import com.dezdeqness.innertube.models.others.WatchEndpoint
import com.dezdeqness.muzika.core.BaseViewModel
import com.dezdeqness.muzika.core.CoroutineDispatcherProvider
import com.dezdeqness.muzika.data.datasource.NextApiDataSource
import com.dezdeqness.muzika.data.datasource.SearchApiDataSource
import com.dezdeqness.muzika.presentation.models.SongUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val searchApiDataSource: SearchApiDataSource,
    private val nextApiDataSource: NextApiDataSource,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    private val _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState

    init {
        launchOnIo {

            YouTube
                .search(
                    query = "Three days grace",
                    filter = YouTube.SearchFilter("EgWKAQIIAWoKEAkQBRAKEAMQBA%3D%3D"),
                )
                .onSuccess { result ->
                    val uiItems = result.items.filterIsInstance<SongItem>().map { item ->
                        SongUiModel(
                            id = item.id,
                            title = item.title,
                            subTitle = item.artists.joinToString { it.name },
                            iconUrl = item.thumbnail,
                            mainImageUrl = item.thumbnail,
                        )
                    }

                    launchOnMain {
                        _homeState.update {
                            _homeState.value.copy(items = uiItems)
                        }
                    }
                }
                .onFailure {
                    Log.d("SearchViewModel", it.message.toString())
                }

        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun loadQuery(songId: String, mediaController: MediaController) {
        launchOnIo {
            YouTube.next(WatchEndpoint(videoId = songId))
                .onSuccess { result ->
                    val items = ArrayList(result.items)

                    val mediaItems = items.map { item ->
                        MediaItem
                            .Builder()
                            .setMediaId(item.id)
                            .setUri(item.id)
                            .setCustomCacheKey(item.id)
                            .setTag(item)
                            .setMediaMetadata(
                                MediaMetadata
                                    .Builder()
                                    .setTitle(item.title)
                                    .setSubtitle(item.artists.joinToString { it.name })
                                    .setArtist(item.artists.joinToString { it.name })
                                    .setArtworkUri(item.thumbnail.toUri())
                                    .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                                    .build()
                            )
                            .build()
                    }

                    launchOnMain {
                        mediaController.setMediaItems(mediaItems)
                        mediaController.prepare()
                        mediaController.playWhenReady = true
                    }
                }
                .onFailure {
                    Log.d("SearchViewModel", it.message.toString())
                }

        }
    }
}
