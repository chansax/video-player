package com.chansax.videoplayer.player

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView

/**
 * Created by chandan on 2019-09-03.
 */


data class PlayerState(var window: Int  = 0,
                       var position: Long = 0,
                       var whenReady: Boolean = true)

class VideoPlayer(val context: Context,
                  val playerState: PlayerState,
                  val playerView: PlayerView) {

    private val exoPlayer: ExoPlayer

    init {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
            .also {
                playerView.player = it
            }
    }


    fun start() {
        exoPlayer.prepare()

    }

    fun pause() {

        with(exoPlayer) {
            with(playerState) {
                position = currentPosition
                window = currentWindowIndex
                whenReady = playWhenReady
            }

            stop(true)
        }
    }

    fun release() {
        exoPlayer.release()
    }
}

/*
exoVideoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);.

 */