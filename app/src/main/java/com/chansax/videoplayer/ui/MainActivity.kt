package com.chansax.videoplayer.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.chansax.videoplayer.R
import com.chansax.videoplayer.adapter.GridItemDecoration
import com.chansax.videoplayer.adapter.VideoAdapter
import com.chansax.videoplayer.common.VIDEOINFO
import com.chansax.videoplayer.data.VideoInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

//    private val mediaSession: MediaSessionCompat by lazy { createMediaSession() }
//    private val mediaSessionConnector: MediaSessionConnector by lazy { createMediaSessionConnector() }
//    private val playerState by lazy { PlayerState() }
//    private lateinit var playerHolder: PlayerHolder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        volumeControlStream = AudioManager.STREAM_MUSIC
//        createMediaSession()
//        createPlayer()
//        createVideoList()
    }

    private fun createVideoList() {
        val videoInfo: List<VideoInfo> = Gson().fromJson(VIDEOINFO, object : TypeToken<List<VideoInfo>>() {}.type)
        val videoAdapter = VideoAdapter(this, videoInfo)
        recyclerView?.apply {
            adapter = videoAdapter
            addItemDecoration(GridItemDecoration(8))
        }
    }
    
    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
//        hideSystemUi()

        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
    }

    private fun releasePlayer() {
        if (player != null) {
//            playbackPosition = player?.getCurrentPosition()
//            currentWindow = player?.getCurrentWindowIndex()
//            playWhenReady = player?.getPlayWhenReady()
            player?.release()
            player = null
        }
    }

    private fun hideSystemUi() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    var player: ExoPlayer? = null

    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
            this,
            DefaultRenderersFactory(this),
            DefaultTrackSelector(), DefaultLoadControl()
        )

        playerView?.player = player
        player?.playWhenReady = true
        player?.seekTo(0, 0)

        val uri = Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
//        val uri = Uri.parse(getString(R.string.media_url))
        val mediaSource = buildMediaSource(uri)
        player?.prepare(mediaSource, true, false)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
//        return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory("lame").crea)
        return ExtractorMediaSource.Factory(
            DefaultHttpDataSourceFactory("exoplayer")
        ).createMediaSource(uri)
    }

//    override fun onStart() {
//        super.onStart()
//        startPlayer()
//        activateMediaSession()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        stopPlayer()
//        deactivateMediaSession()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        releasePlayer()
//        releaseMediaSession()
//    }
//
//    private fun createMediaSession() : MediaSessionCompat = MediaSessionCompat(this, packageName)
//
//    private fun createMediaSessionConnector(): MediaSessionConnector =
//            MediaSessionConnector(mediaSession).apply {
//
//            }
}
