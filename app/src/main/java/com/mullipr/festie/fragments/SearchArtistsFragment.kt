package com.mullipr.festie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mullipr.festie.adapters.ArtistAdapter
import com.mullipr.festie.api.ApiService
import com.mullipr.festie.api.endpoints.SearchResource
import com.mullipr.festie.databinding.SearchArtistsFragmentBinding
import com.mullipr.festie.viewModel.SearchArtistsViewModel
import kotlinx.coroutines.launch

class SearchArtistsFragment : Fragment(){
    private lateinit var binding : SearchArtistsFragmentBinding
    private lateinit var artistAdapter : ArtistAdapter

    private val viewModel : SearchArtistsViewModel by viewModels {
        val res = ApiService(requireContext()).get().create(SearchResource::class.java)
        SearchArtistsViewModel.Factory(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchArtists()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchArtistsFragmentBinding.inflate(layoutInflater)

        artistAdapter = ArtistAdapter(listOf()).also {
            binding.artistsView.adapter = it
        }
        binding.artistsView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect {
                    artistAdapter.list = it.artists
                    artistAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}