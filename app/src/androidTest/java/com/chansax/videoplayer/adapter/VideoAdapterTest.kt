package com.chansax.videoplayer.adapter

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.chansax.videoplayer.common.VIDEOINFO
import com.chansax.videoplayer.data.VideoInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by chandan on 2019-09-05.
 */

class VideoAdapterTest {

    private lateinit var videoAdapter: VideoAdapter
    lateinit var instrumentationContext: Context
    lateinit var videoInfo: List<VideoInfo>

    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        videoAdapter = VideoAdapter(instrumentationContext) {item -> onClick(item)}
        videoInfo = Gson().fromJson(VIDEOINFO, object : TypeToken<List<VideoInfo>>() {}.type)
        videoAdapter.setItems(videoInfo)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getItemCount() {
        assertThat(videoAdapter.itemCount, CoreMatchers.`is`(videoInfo.size))
    }

    private fun onClick(item: Any?) {
        val index = item as Int?
        index?.let {
        }
    }
}