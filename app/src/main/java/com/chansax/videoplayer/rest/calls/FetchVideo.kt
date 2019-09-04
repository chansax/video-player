package com.chansax.videoplayer.rest.calls

import com.chansax.videoplayer.data.VideoInfo
import com.chansax.videoplayer.rest.RestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by chandan on 2019-09-04.
 */

class FetchVideo(val callback: VideoCallback) {
    fun download() {
        RestApi.fetchVideos().enqueue(object : Callback<List<VideoInfo>> {
            override fun onResponse(call: Call<List<VideoInfo>>, response: Response<List<VideoInfo>>) {
                if (response.isSuccessful && response.body() != null) {
                    callback.apiResponse(response.body())
                }
            }

            override fun onFailure(call: Call<List<VideoInfo>>, t: Throwable) {
                callback.apiResponse(listOf())
            }
        })
    }

    interface VideoCallback {
        fun apiResponse(videoItems: List<VideoInfo>?)
    }

}