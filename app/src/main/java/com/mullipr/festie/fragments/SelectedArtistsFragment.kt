package com.mullipr.festie.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mullipr.festie.adapters.ArtistAdapter
import com.mullipr.festie.databinding.SelectedArtistsFragmentBinding
import com.mullipr.festie.model.Artist
import com.mullipr.festie.viewModel.SelectedArtistsViewModel
import kotlinx.coroutines.launch

class SelectedArtistsFragment : Fragment() {

    private lateinit var binding : SelectedArtistsFragmentBinding
    private val viewModel : SelectedArtistsViewModel by viewModels{
        val parcels = arguments?.getParcelableArray("artists")
        val artists = mutableListOf<Artist>()
        if(parcels != null) {
            for (parcel in parcels) {
                artists.add(parcel as Artist)
            }
        }
        SelectedArtistsViewModel.Factory(artists)
    }

    private lateinit var artistAdapter: ArtistAdapter

    companion object {
        fun newInstance() = SelectedArtistsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = SelectedArtistsFragmentBinding.inflate(layoutInflater)

        val artistClickedListener = { artist : Artist -> viewModel.artistSelected(artist) }

        artistAdapter = ArtistAdapter(requireContext(), listOf(), artistClickedListener).also {
            binding.selectedArtistsRecyclerview.adapter = it
        }
        binding.selectedArtistsRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect {
                    artistAdapter.list = it.selectedArtists
                    artistAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}