package com.chansax.videoplayer.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer

/**
 * Created by chandan on 2019-09-04.
 */

interface VideoPlayer {

    fun play(url: String)

    fun getPlayerImpl(): ExoPlayer?

    fun releasePlayer()

//    fun setMediaSessionState()
}