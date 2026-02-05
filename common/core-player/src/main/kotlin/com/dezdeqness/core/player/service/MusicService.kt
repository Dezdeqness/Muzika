package com.dezdeqness.core.player.service

import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.dezdeqness.core.network.data.api.StreamsService
import com.dezdeqness.core.network.domain.RetrieveAccessTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

@OptIn(androidx.media3.common.util.UnstableApi::class)
class MusicService : MediaSessionService(), MediaSession.Callback {

    val playerCache: SimpleCache by inject(named("PlayerCache"))

    val downloaderCache: SimpleCache by inject(named("DownloadCache"))

    val retrieveAccessTokenUseCase: RetrieveAccessTokenUseCase by inject()

    val streamsService: StreamsService by inject()

    private var mediaSession: MediaSession? = null

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaSession

    override fun onCreate() {
        super.onCreate()

        val playbackAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .build()

        val player = ExoPlayer.Builder(this)
            .setMediaSourceFactory(createMediaSourceFactory())
            .setAudioAttributes(playbackAttributes, true)
            .build()
        mediaSession = MediaSession
            .Builder(this, player)
            .setCallback(this)
            .build()
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    private fun createMediaSourceFactory() = DefaultMediaSourceFactory(createDataSourceFactory())

    private fun createDataSourceFactory(): DataSource.Factory {
        val songUrlCache = HashMap<String, Pair<String, Long>>()

        return ResolvingDataSource.Factory(
            CacheDataSource
                .Factory()
                .setCache(downloaderCache)
                .setUpstreamDataSourceFactory(
                    CacheDataSource.Factory()
                        .setCache(playerCache)
                        .setUpstreamDataSourceFactory(DefaultDataSource.Factory(this))
                )
        ) { dataSpec ->
//            if (downloaderCache.isCached(
//                    mediaId,
//                    dataSpec.position,
//                    if (dataSpec.length >= 0) dataSpec.length else 1
//                ) ||
//                playerCache.isCached(mediaId, dataSpec.position, 512 * 1024L)
//            ) {
//                return@Factory dataSpec
//            }
//
//            songUrlCache[mediaId]?.takeIf { it.second < System.currentTimeMillis() }?.let {
//                return@Factory dataSpec.withUri(it.first.toUri())
//            }

            val uri = dataSpec.uri

            if (uri.toString().startsWith("https://")) {
                return@Factory dataSpec
            }

            // refresh possible needed, also in case of missed url
            // need to fetch API request
            val token = runBlocking(Dispatchers.IO) {
                retrieveAccessTokenUseCase.invoke().getOrNull()
            }

            val streamsResponse = runBlocking(Dispatchers.IO) {
                streamsService.getStreams(dataSpec.uri.toString())
            }

            val streamUrl = streamsResponse.hlsMp3128Url
                ?: streamsResponse.previewMp3128Url
                ?: error("No stream URL available for track ${dataSpec.uri}")

            dataSpec.withUri(streamUrl.toUri())
                .withAdditionalHeaders(
                    mapOf("Authorization" to "OAuth $token")
                )

        }
    }

}
