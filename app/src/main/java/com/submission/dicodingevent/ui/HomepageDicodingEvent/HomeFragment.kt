package com.submission.dicodingevent.ui.HomepageDicodingEvent

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
import com.submission.dicodingevent.databinding.FragmentHomeBinding
import com.submission.dicodingevent.ui.adapter.adapter
import com.submission.dicodingevent.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var eventAdapterForFn: adapter
    private lateinit var eventAdapterForUp: adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchEventDataHMUPEV()
        fetchEventDataHMFNEV()
    }

    private fun setupRecyclerView() {
        eventAdapterForUp = adapter(emptyList()) { selectedEvent -> navToDetail(selectedEvent) }
        eventAdapterForFn = adapter(emptyList()) { selectedEvent -> navToDetail(selectedEvent) }

        binding?.rvhmupev?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = eventAdapterForUp
        }

        binding?.rvhmfnev?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = eventAdapterForFn
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

    private fun fetchEventDataHMUPEV() {
        showLoading(isLoading = true, isUpcoming = true)
        val client1 = APIKonfig.getApiService().getEvnt(1)
        client1.enqueue(object : Callback<EventFinishedRes> {
            override fun onResponse(
                call: Call<EventFinishedRes>,
                response: Response<EventFinishedRes>
            ) {
                showLoading(isLoading = false, isUpcoming = true)
                if (_binding == null) return
                if (response.isSuccessful && response.body() != null) {
                    val responseBodyUp = response.body()
                    if (responseBodyUp?.listEvents != null && responseBodyUp.listEvents.isNotEmpty()) {
                        eventAdapterForUp = adapter(responseBodyUp.listEvents) { selectedEvent ->
                            navToDetail(selectedEvent)
                        }
                        binding?.rvhmupev?.adapter = eventAdapterForUp
                    } else {
                        Toast.makeText(context, "No events found!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Failed to get response", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EventFinishedRes>, t: Throwable) {
                showLoading(isLoading = false, isUpcoming = true)
                if (_binding != null) {
                    Toast.makeText(context, "Failed to load events: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun fetchEventDataHMFNEV() {
        showLoading(isLoading = true, isUpcoming = false)
        val client2 = APIKonfig.getApiService().getEvnt(0)
        client2.enqueue(object : Callback<EventFinishedRes> {
            override fun onResponse(
                call: Call<EventFinishedRes>,
                response: Response<EventFinishedRes>
            ) {
                showLoading(isLoading = false, isUpcoming = false)
                if (_binding == null) return
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()
                    if (responseBody?.listEvents != null && responseBody.listEvents.isNotEmpty()) {
                        eventAdapterForFn = adapter(responseBody.listEvents) { selectedEvent ->
                            navToDetail(selectedEvent)
                        }
                        binding?.rvhmfnev?.adapter = eventAdapterForFn
                    } else {
                        Toast.makeText(context, "No events found!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Failed to get response", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EventFinishedRes>, t: Throwable) {
                showLoading(isLoading = false, isUpcoming = false)
                if (_binding != null) {
                    Toast.makeText(context, "Failed to load events: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean, isUpcoming: Boolean) {
        if (isUpcoming) {
            binding?.progressBarUpevent?.visibility = if (isLoading) View.VISIBLE else View.GONE
        } else {
            binding?.progressBarEndevent?.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

