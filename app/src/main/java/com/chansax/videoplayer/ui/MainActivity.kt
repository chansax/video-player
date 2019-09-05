package com.chansax.videoplayer.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chansax.videoplayer.R
import com.chansax.videoplayer.adapter.GridItemDecoration
import com.chansax.videoplayer.adapter.VideoAdapter
import com.chansax.videoplayer.data.VideoInfo
import com.chansax.videoplayer.viewmodel.PlayerViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        when (toolbar) {
            null -> {hideSystemUi()}
            else -> {setSupportActionBar(toolbar)}
        }

        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)
        playerViewModel.getPlayer().observe(this, Observer<ExoPlayer> {
            playerView?.player = it
        })

        setupVideoList()
    }

    private fun onClick(item: Any?) {
        val index = item as Int?
        index?.let {
            playerViewModel.playIndex(index)
        }
    }

    private fun setupVideoList() {
        recyclerView?.apply {
            val videoAdapter = VideoAdapter(this@MainActivity) { item -> onClick(item) }
            adapter = videoAdapter
            addItemDecoration(GridItemDecoration(16))

            playerViewModel.getVideoList().observe(this@MainActivity, Observer<List<VideoInfo>> {
                videoAdapter.setItems(it)
                if (it.isNotEmpty()) {
                    onClick(0)
                }
            })

            playerViewModel.fetchVideoList()
        }
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

        if (Util.SDK_INT <= 23) {
            playerViewModel.initializePlayer()
        }
    }

    private fun hideSystemUi() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }
}
