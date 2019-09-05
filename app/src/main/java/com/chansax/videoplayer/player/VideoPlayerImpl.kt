package com.chansax.videoplayer.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util

/**
 * Created by chandan on 2019-09-03.
 *  Implementation of video player based on ExoPlayer.
 */

class VideoPlayerImpl(private val context: Context) : VideoPlayer {

    private var userAgent: String = Util.getUserAgent(context, "Test-Player")
    private lateinit var currentUrl: String

    private var startAutoPlay: Boolean = false
    private var startWindow: Int = C.INDEX_UNSET
    private var startPosition: Long = C.TIME_UNSET
    private val eventListener = PlayerEventListener()
    private var callback:PlayerCallback? = null


    private var exoPlayer: ExoPlayer? = null

    override fun play(url: String) {
        currentUrl = url
        val mediaSource = ExtractorMediaSource.Factory(DefaultDataSourceFactory(context, userAgent))
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(url))

        val hasStartPosition = startWindow != C.INDEX_UNSET
        if (hasStartPosition) {
            exoPlayer?.seekTo(startWindow, startPosition)
        }
        exoPlayer?.playWhenReady = true
        exoPlayer?.prepare(mediaSource, !hasStartPosition, false)
    }

    override fun getPlayerImpl(): ExoPlayer? {
        initPlayer()
        exoPlayer?.let { return it }
        return null
    }

    override fun releasePlayer() {
        exoPlayer?.let {
            startPosition = it.currentPosition
            startWindow = it.currentWindowIndex
            startAutoPlay = it.playWhenReady
        }
        exoPlayer?.removeListener(eventListener)
        exoPlayer?.stop(true)
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun setCallback(cb: PlayerCallback) {
        cb?.let {
            this.callback = cb
        }
    }

    private fun updateStartPosition() {
        exoPlayer?.let {
            startAutoPlay = it.playWhenReady
            startWindow = it.currentWindowIndex
            startPosition = Math.max(0, it.contentPosition)
        }
    }

    private fun clearStartPosition() {
        startAutoPlay = true
        startWindow = C.INDEX_UNSET
        startPosition = C.TIME_UNSET
    }

    private fun initPlayer() {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(
            context,
            DefaultRenderersFactory(context),
            DefaultTrackSelector(),
            DefaultLoadControl()
        )
            .apply {

                /*  Here we can add EventListener, Video Event Listener & Audio Event Listener
                    & Analytics Listener.
                 */

                addListener(eventListener)
            }
    }

    private inner class PlayerEventListener : Player.EventListener {
        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        }

        override fun onSeekProcessed() {
        }

        override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        }

        override fun onPlayerError(error: ExoPlaybackException?) {
            error?.let {
                Log.e("Test-Player", it.message, it)
            }
        }

        override fun onLoadingChanged(isLoading: Boolean) {
        }

        override fun onPositionDiscontinuity(reason: Int) {
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        }

        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when(playbackState) {
                Player.STATE_ENDED -> callback?.playEnded()
            }
        }
    }
}

/*
exoVideoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);.
 */