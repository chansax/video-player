package com.chansax.videoplayer.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import com.chansax.videoplayer.data.VideoInfo
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

    fun getPlayerState(): LiveData<ExoplayerState> = playerState
    fun getPlayer(): LiveData<ExoPlayer> = exoPlayer
    fun getVideoList(): LiveData<List<VideoInfo>> = videoList

    fun fetchVideoList() {
        FetchVideo(object: FetchVideo.VideoCallback {
            override fun apiResponse(videoItems: List<VideoInfo>?) {
                videoItems?.let {
                    videoList.postValue(videoItems)
                }
            }
        }).download()
    }

    fun initializePlayer() {
        val player = videoPlayer.getPlayerImpl()
        player?.let {
            exoPlayer.postValue(player)
        }
    }

    fun playUrl(url: String) {
//        val link = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        videoPlayer.play(url)
    }

    fun releasePlayer() {
        videoPlayer.releasePlayer()
    }
}
 