package com.submission.dicodingevent.ui.UpcommingEventDicoding

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
import com.submission.dicodingevent.databinding.UpcommingEventBinding
import com.submission.dicodingevent.ui.adapter.adapter
import com.submission.dicodingevent.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcommingEventFrg : Fragment() {

    private var _binding: UpcommingEventBinding? = null
    private val binding get() = _binding
    private var eventAdapter: adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UpcommingEventBinding.inflate(inflater, container, false)
        val root: View? = _binding?.root
        setupRecyclerView()
        fetchEventDataUpcomming()
        return _binding?.root
    }

    private fun setupRecyclerView() {
        eventAdapter = adapter(emptyList()){selectedEvent ->
            navToDetail(selectedEvent)
        }
        binding?.rvUpcomming?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventAdapter
        }
    }

    private fun navToDetail(event: ListEventsItem){
        val inten= Intent(context, DetailActivity::class.java).apply {
            putExtra("EventId",event.id)
            putExtra("eventImg",event.mediaCover)
            putExtra("EventCtg",event.category)
            putExtra("EventBt",event.beginTime)
            putExtra("EventNm",event.name)
            putExtra("EventCnm",event.cityName)
            putExtra("EventDsc",event.description)
            putExtra("EventEdt",event.endTime)
            putExtra("EventLnk",event.link)
            putExtra("EventOwnNm",event.ownerName)
            putExtra("EventQt",event.quota)
            putExtra("EventSmry",event.summary)
            putExtra("EventReg",event.registrants)
            putExtra("EventImlg",event.imageLogo)
        }
        startActivity(inten)
    }

    private fun fetchEventDataUpcomming() {
        showLoading(true)
        val client = APIKonfig.getApiService().getEvnt(1)
        client.enqueue(object : Callback<EventFinishedRes> {
            override fun onResponse(
                call: Call<EventFinishedRes>,
                response: Response<EventFinishedRes>
            ) {
                showLoading(false)
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()
                    if (responseBody?.listEvents != null && responseBody.listEvents.isNotEmpty()) {
                        eventAdapter = adapter(responseBody.listEvents){
                                selectedEvent -> navToDetail(selectedEvent) }
                        binding?.rvUpcomming?.adapter = eventAdapter
                    } else {
                        Toast.makeText(context, "No events found !", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Failed to get response", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EventFinishedRes>, t: Throwable) {
                showLoading(false)
                Toast.makeText(context, "Failed to load events: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}