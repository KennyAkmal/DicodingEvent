package com.submission.dicodingevent.ui.FinishedEventDicoding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicodingevent.data.respon.EventFinishedRes
import com.submission.dicodingevent.data.respon.ListEventsItem
import com.submission.dicodingevent.data.retroit.APIKonfig
import com.submission.dicodingevent.databinding.FinishedEventBinding
import com.submission.dicodingevent.ui.adapter.adapter
import com.submission.dicodingevent.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedEventFrg : Fragment() {

    private var _binding: FinishedEventBinding? = null
    private val binding get() = _binding
    private var eventAdapter: adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FinishedEventBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchEventDataFinished()
    }

    private fun setupRecyclerView() {
        eventAdapter = adapter(emptyList()) { selectedEvent ->
            navToDetail(selectedEvent)
        }
        binding?.rvFinished?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventAdapter
        }
    }

    private fun navToDetail(event: ListEventsItem) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra("EventId", event.id)
            putExtra("eventImg", event.mediaCover)
            putExtra("EventCtg", event.category)
            putExtra("EventBt", event.beginTime)
            putExtra("EventNm", event.name)
            putExtra("EventCnm", event.cityName)
            putExtra("EventDsc", event.description)
            putExtra("EventEdt", event.endTime)
            putExtra("EventLnk", event.link)
            putExtra("EventOwnNm", event.ownerName)
            putExtra("EventQt", event.quota)
            putExtra("EventSmry", event.summary)
            putExtra("EventReg", event.registrants)
            putExtra("EventImlg", event.imageLogo)
        }
        startActivity(intent)
    }

    private fun fetchEventDataFinished() {
        showLoading(true)
        val client = APIKonfig.getApiService().getEvnt(0)
        client.enqueue(object : Callback<EventFinishedRes> {
            override fun onResponse(
                call: Call<EventFinishedRes>,
                response: Response<EventFinishedRes>
            ) {
                showLoading(false)
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()
                    val events = responseBody?.listEvents ?: emptyList()
                    if (events.isNotEmpty()) {
                        eventAdapter = adapter(events) { selectedEvent ->
                            navToDetail(selectedEvent)
                        }
                        binding?.rvFinished?.adapter = eventAdapter
                    } else {
                        showToast("No events found!")
                    }
                } else {
                    showToast("Failed to get response")
                }
            }

            override fun onFailure(call: Call<EventFinishedRes>, t: Throwable) {
                showLoading(false)
                showToast("Failed to load events: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}