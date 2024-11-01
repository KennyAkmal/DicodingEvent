package com.submission.dicodingevent.ui.detail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.engine.GlideException
import com.submission.dicodingevent.data.db.faventity
import com.submission.dicodingevent.databinding.ActivityDetailBinding
import com.submission.dicodingevent.ui.Sharedvm

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var eventId: Int = 0
    private lateinit var eventImageUrl: String
    private val sharedViewModel: Sharedvm by viewModels()
    private val viewModel: vmdetail by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBarData.visibility = View.VISIBLE

        eventId = intent.getIntExtra("EventId", 0)
        val eventName = intent.getStringExtra("EventNm").orEmpty()
        eventImageUrl = intent.getStringExtra("eventImg").orEmpty()
        val eventOwnerName = intent.getStringExtra("EventOwnNm").orEmpty()
        val eventLink = intent.getStringExtra("EventLnk")
        val eventBeginTime = intent.getStringExtra("EventBt").orEmpty()
        val eventQuota = intent.getIntExtra("EventQt", 0)
        val eventRegistered = intent.getIntExtra("EventReg", 0)
        val eventDescription = intent.getStringExtra("EventDsc").orEmpty()
        val eventSummary = intent.getStringExtra("EventSmry").orEmpty()
        val imageLogoEvent = intent.getStringExtra("EventImlg").orEmpty()
        val citynameEvent = intent.getStringExtra("EventCnm").orEmpty()
        val categoryEvent = intent.getStringExtra("EventCtg").orEmpty()
        val endEvent = intent.getStringExtra("EventEdt").orEmpty()

        binding.nmev.text = eventName
        binding.nmown.text = eventOwnerName
        binding.tmev.text = eventBeginTime
        binding.qtev.text = (eventQuota - eventRegistered).toString()
        binding.dscev.text = HtmlCompat.fromHtml(eventDescription, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.progressBarData.visibility = View.GONE

        binding.progressBarImg.visibility = View.VISIBLE
        Glide.with(this)
            .load(eventImageUrl)
            .listener(object : RequestListener<android.graphics.drawable.Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBarImg.visibility = View.GONE
                    Toast.makeText(this@DetailActivity, "Failed to load image", Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBarImg.visibility = View.GONE
                    return false
                }
            })
            .into(binding.imgev)

        viewModel.checkIfFavorite(eventId)
        viewModel.isFavorite.observe(this) { isFavorite ->
            updateFavoriteButton(isFavorite)
        }

        binding.favbtn.setOnClickListener {
            viewModel.toggleFavorite(
                faventity(
                    id = eventId,
                    name = eventName,
                    description = eventDescription,
                    mediaCover = eventImageUrl,
                    quota = eventQuota,
                    ownerName = eventOwnerName,
                    beginTime = eventBeginTime,
                    summary = eventSummary,
                    imageLogo = imageLogoEvent,
                    cityName = citynameEvent,
                    category = categoryEvent,
                    endTime = endEvent,
                    link = eventLink,
                    registrants = eventRegistered
                )
            )
        }

        binding.openLinkButton.setOnClickListener {
            eventLink?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            }
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        binding.favbtn.text = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
    }
}
