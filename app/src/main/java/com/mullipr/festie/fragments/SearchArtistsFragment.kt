package com.mullipr.festie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mullipr.festie.R
import com.mullipr.festie.adapters.ArtistAdapter
import com.mullipr.festie.databinding.SearchArtistsFragmentBinding
import com.mullipr.festie.model.Artist
import com.mullipr.festie.model.SearchArtistsUiState
import com.mullipr.festie.viewModel.SearchArtistsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchArtistsFragment : Fragment(){
    private lateinit var binding : SearchArtistsFragmentBinding
    private lateinit var artistAdapter : ArtistAdapter

    private lateinit var lastState : SearchArtistsUiState

    private val viewModel : SearchArtistsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null){
            val artists = savedInstanceState.getParcelableArrayList<Artist>("artists")?.toList() ?: listOf()
            val selectedArtists = savedInstanceState.getParcelableArrayList<Artist>("selected_artists")?.toList() ?: listOf()
            val count = selectedArtists.size
            viewModel.restoreUiState(SearchArtistsUiState(artists, false, count, selectedArtists))
            return
        }

        val parcels = arguments?.getParcelableArray("artists") ?: return

        val artists = mutableListOf<Artist>()
        for (parcel in parcels) {
            artists.add(parcel as Artist)
        }
        viewModel.restoreUiState(SearchArtistsUiState(listOf(), false, artists.size, artists))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("artists", ArrayList<Artist>(lastState.artists))
        outState.putParcelableArrayList("selected_artists", ArrayList<Artist>(viewModel.selectedArtists))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchArtistsFragmentBinding.inflate(layoutInflater)

        val artistClickedListener = { artist : Artist -> viewModel.artistSelected(artist) }

        artistAdapter = ArtistAdapter(requireContext(), listOf(), artistClickedListener).also {
            binding.artistsView.adapter = it
        }
        binding.artistsView.layoutManager = LinearLayoutManager(requireContext())

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                viewModel.searchArtists(text)
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                return false
            }
        })

        binding.selectionIndicatorCardview.setOnClickListener {
            val bundle = bundleOf("artists" to viewModel.selectedArtists.toTypedArray())
            findNavController().navigate(R.id.action_searchArtistsFragment_to_selectedArtistsFragment, bundle)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect {
                    lastState = it

                    artistAdapter.list = it.artists
                    artistAdapter.notifyDataSetChanged()

                    binding.selectionIndicatorCardview.visibility =
                        if(it.selectedArtistsCount > 0) VISIBLE
                        else GONE
                    binding.selectionIndicatorText.text = it.selectedArtistsCount.toString()
                }
            }
        }
    }
}