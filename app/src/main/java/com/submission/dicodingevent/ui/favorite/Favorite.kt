package com.submission.dicodingevent.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicodingevent.data.db.faventity
import com.submission.dicodingevent.data.respon.ListEventsItem
import com.submission.dicodingevent.databinding.FragmentFavoriteBinding
import com.submission.dicodingevent.ui.Sharedvm
import com.submission.dicodingevent.ui.adapter.AdapterFav
import com.submission.dicodingevent.ui.detail.DetailActivity

class Favorite : Fragment() {
    private lateinit var favoriteAdapter: AdapterFav
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: vmfav
    private lateinit var sharedViewModel: Sharedvm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        sharedViewModel = ViewModelProvider(requireActivity()).get(Sharedvm::class.java)
        sharedViewModel.favorites.observe(viewLifecycleOwner) { favoriteList ->
            val listEventsItems = favoriteList.map { convertToListEventsItem(it) }
            favoriteAdapter.submitList(listEventsItems)
        }
    }

    private fun setupRecyclerView() {
        binding?.rvfav?.layoutManager = LinearLayoutManager(requireContext())
        favoriteAdapter = AdapterFav { favorite -> navToDetail(favorite) }
        binding?.rvfav?.adapter = favoriteAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(vmfav::class.java)

        viewModel.favorites.observe(viewLifecycleOwner) { favoriteList ->
            val listEventsItems = favoriteList.map { convertToListEventsItem(it) }
            if (listEventsItems.isNotEmpty()) {
                favoriteAdapter.submitList(listEventsItems)
            }
        }
    }

    private fun navToDetail(event: ListEventsItem) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("EventId", event.id)
            putExtra("EventNm", event.name)
            putExtra("EventBt", event.beginTime)
            putExtra("EventDsc", event.description)
            putExtra("eventImg", event.mediaCover)
            putExtra("EventOwnNm", event.ownerName)
            putExtra("EventLnk", event.link)
            putExtra("EventQt", event.quota)
            putExtra("EventReg", event.registrants)
        }
        startActivity(intent)
    }

    private fun convertToListEventsItem(favorite: faventity): ListEventsItem {
        return ListEventsItem(
            id = favorite.id,
            name = favorite.name ?: "",
            summary = favorite.description ?: "",
            mediaCover = favorite.mediaCover ?: "",
            category = favorite.category ?: "",
            beginTime = favorite.beginTime ?: "",
            endTime = favorite.endTime ?: "",
            link = favorite.link ?: "",
            cityName = favorite.cityName ?: "",
            ownerName = favorite.ownerName ?: "",
            registrants = favorite.registrants ?: 0,
            quota = favorite.quota ?: 0,
            description = favorite.description ?: "",
            imageLogo = favorite.imageLogo ?: ""
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
