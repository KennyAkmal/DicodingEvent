package com.submission.dicodingevent.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.submission.dicodingevent.R
import com.submission.dicodingevent.data.respon.ListEventsItem

class adapter(
    private var eventList: List<ListEventsItem>,
    private val onItemClick: (ListEventsItem) -> Unit
) : RecyclerView.Adapter<adapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.title_image)
        val tvEventTittle: TextView = itemView.findViewById(R.id.tvTittle)
        val tvEventSummary: TextView = itemView.findViewById(R.id.tvSummary)
        val imageProgressBar: ProgressBar = itemView.findViewById(R.id.progressBarImg)

        fun bindItem(eventsItem: ListEventsItem) {
            tvEventTittle.text = eventsItem.name
            tvEventSummary.text = eventsItem.summary
            imageProgressBar.visibility = View.VISIBLE
            Glide.with(itemView.context)
                .load(eventsItem.mediaCover)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageProgressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(img)

            itemView.setOnClickListener {
                onItemClick(eventsItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lsitem, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val eventItem = eventList[position]
        holder.bindItem(eventItem)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}
