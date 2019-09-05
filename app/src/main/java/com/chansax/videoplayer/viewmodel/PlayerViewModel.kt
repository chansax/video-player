package com.chansax.videoplayer.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import com.chansax.videoplayer.data.VideoInfo
import com.chansax.videoplayer.player.PlayerCallback
import com.chansax.videoplayer.player.VideoPlayer
import com.chansax.videoplayer.player.VideoPlayerImpl
import com.chansax.videoplayer.rest.calls.FetchVideo
import com.google.android.exoplayer2.ExoPlayer

/**
 * Created by chandan on 2019-09-03.
 */

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private var videoPlayer: VideoPlayer = VideoPlayerImpl(context)
    private val playerState = MutableLiveData<ExoplayerState>()
    private val exoPlayer = MutableLiveData<ExoPlayer>()
    private val videoList = MutableLiveData<List<VideoInfo>>()
    private var playingIndex = 0

    fun getPlayerState(): LiveData<ExoplayerState> = playerState
    fun getPlayer(): LiveData<ExoPlayer> = exoPlayer
    fun getVideoList(): LiveData<List<VideoInfo>> = videoList

    fun fetchVideoList() {
        playingIndex = -1
        FetchVideo(object : FetchVideo.VideoCallback {
            override fun apiResponse(videoItems: List<VideoInfo>?) {
                videoItems?.let {
                    videoList.postValue(videoItems)
                }
            }
        }).download()
    }

    private fun resumeCurrent() {
        if (playingIndex >= 0) {
            videoList.value?.size?.compareTo(playingIndex)?.let {
                if (it > 0) {
                    playIndex(playingIndex)
                }
            }
        }
    }

    fun initializePlayer() {
        val player = videoPlayer.getPlayerImpl()
        player?.let {
            exoPlayer.postValue(player)
            videoPlayer.setCallback(object : PlayerCallback {
                override fun playEnded() {
                    // play next video
                    videoList.value?.size?.compareTo(playingIndex)?.let {
                        if (it > 0) {
                            playingIndex++
                            playIndex(playingIndex)
                        }
                    }
                }
            })
            resumeCurrent()
        }
    }

    fun playIndex(index: Int) {
        videoList.value?.get(index)?.url?.let {
            playingIndex = index
            videoPlayer.play(it)
        }
    }

    fun playUrl(url: String) {
        videoPlayer.play(url)
    }

    fun releasePlayer() {
        videoPlayer.releasePlayer()
    }
}
 