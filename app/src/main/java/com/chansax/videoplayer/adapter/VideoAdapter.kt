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
 *
 * This class is Adapter implementation for the Recycler View.
 */

class VideoAdapter(
    private val context: Context,
    val adapterOnClick: (Any) -> Unit?
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private var itemList: List<VideoInfo> = listOf()
    private val glideOptions = GlideOptions().frame(10000000)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList.get(position)
        holder.itemName.text = item.title

        item.url.let {
            holder.index = position
            GlideApp.with(context).load(it).apply(glideOptions)
                .placeholder(R.drawable.ic_shortcut_featured_video)
                .centerCrop()
                .into(holder.itemImage)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItems(items: List<VideoInfo>?) {
        if (items == null || items.isEmpty()) {
            return
        } else {
            itemList = items
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.video_image)
        lateinit var itemImage: ImageView

        @BindView(R.id.video_name)
        lateinit var itemName: TextView

        var index: Int = -1

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { adapterOnClick(index as Any) }
        }
    }
}