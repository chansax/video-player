package com.chansax.videoplayer.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer

/**
 * Created by chandan on 2019-09-04.
 * This is the interface to access the ExoPlayer based video implementation
 */

interface VideoPlayer {

    fun play(url: String)

    fun getPlayerImpl(): ExoPlayer?

    fun releasePlayer()

    fun setCallback(callback: PlayerCallback)

}

/*
   This interface is used by the ExoPlayer to indicate the video being played has ended.
 */

interface PlayerCallback{
    fun playEnded()
}