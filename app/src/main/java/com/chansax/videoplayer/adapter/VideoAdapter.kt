package com.chansax.videoplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.chansax.videoplayer.R
import com.chansax.videoplayer.data.VideoInfo
import com.chansax.videoplayer.glide.GlideApp
import com.chansax.videoplayer.glide.GlideOptions

/**
 * Created by chandan on 2019-08-31.
 */

class VideoAdapter(
    private val context: Context,
    private var itemList: List<VideoInfo>?
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private val glideOptions = GlideOptions().frame(10000000)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList?.get(position)
        holder.itemName?.text = item?.title

        GlideApp.with(context).load(item?.url).apply(glideOptions)
            .centerCrop()
//            .transform(RoundedCornersTransformation(8, 10))
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    init {
        if (itemList == null) {
            itemList = listOf()
        }
    }

    fun setItems(items: List<VideoInfo>?) {
        if (items == null || items.isEmpty()) {
            return
        } else {
            itemList = items
        }
        notifyDataSetChanged()
    }

    fun addItems(items: List<VideoInfo>?) {
        if (items == null || items.isEmpty())
            return

        itemList = itemList?.plus(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.video_image)
        lateinit var itemImage: ImageView

        @BindView(R.id.video_name)
        lateinit var itemName: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}