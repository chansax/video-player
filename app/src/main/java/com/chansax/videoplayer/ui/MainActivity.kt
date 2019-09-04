package com.chansax.videoplayer.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chansax.videoplayer.R
import com.chansax.videoplayer.adapter.GridItemDecoration
import com.chansax.videoplayer.adapter.VideoAdapter
import com.chansax.videoplayer.common.VIDEOINFO
import com.chansax.videoplayer.data.VideoInfo
import com.chansax.videoplayer.viewmodel.ExoplayerState
import com.chansax.videoplayer.viewmodel.PlayerViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.util.Util
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)
        playerViewModel.getPlayerState().observe(this, Observer<ExoplayerState> {

        })

        playerViewModel.getPlayer().observe(this, Observer<ExoPlayer> {
            playerView?.player = it
        })

        setupVideoList()
    }

    private fun setupVideoList() {
//        val videoInfo: List<VideoInfo> = Gson().fromJson(VIDEOINFO, object : TypeToken<List<VideoInfo>>() {}.type)
        val videoAdapter = VideoAdapter(this, null)
        recyclerView?.apply {
            adapter = videoAdapter
            addItemDecoration(GridItemDecoration(8))
        }

        playerViewModel.getVideoList().observe(this, Observer<List<VideoInfo>> {
            videoAdapter.setItems(it)
        })

        playerViewModel.fetchVideoList()
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            playerViewModel.initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            playerViewModel.releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            playerViewModel.releasePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
//        hideSystemUi()

        if (Util.SDK_INT <= 23 || player == null) {
            playerViewModel.initializePlayer()
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
//        player = ExoPlayerFactory.newSimpleInstance(
//            this,
//            DefaultRenderersFactory(this),
//            DefaultTrackSelector(), DefaultLoadControl()
//        )
//
//        playerView?.player = player
//        player?.seekTo(0, 0)
//
//        val uri = Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
////        val uri = Uri.parse(getString(R.string.media_url))
//        val mediaSource = buildMediaSource(uri)
//        player?.prepare(mediaSource, true, false)
    }
}
