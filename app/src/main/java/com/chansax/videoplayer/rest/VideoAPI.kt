package com.chansax.videoplayer.rest.calls

import com.chansax.videoplayer.data.VideoInfo
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by chandan on 2019-09-04.
 */

/*
    This interface defines the interface to the video endpoint to download the video list.
 */

interface VideoApi {
    @GET("android-team/code-challenge-manifest.json")
    fun fetchVideo(): Call<List<VideoInfo>>
}