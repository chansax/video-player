package com.chansax.videoplayer.data

import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by chandan on 2019-09-05.
 */

class VideoInfoTest {

    private lateinit var videoInfo: VideoInfo
    private val title = "Test Title"
    private val url = "https://com.chansax/media/test.mp4"

    @Before
    fun setUp() {
        videoInfo = VideoInfo(title, url)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getTitle() {
        assertThat(videoInfo.title, `is`(title))
    }

    @Test
    fun getUrl() {
        assertThat(videoInfo.url, `is`(url))
    }
}