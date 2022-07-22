package com.mullipr.festie.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
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
import com.mullipr.festie.databinding.SelectedArtistsFragmentBinding
import com.mullipr.festie.model.Artist
import com.mullipr.festie.model.SelectedArtistsUiState
import com.mullipr.festie.viewModel.SelectedArtistsViewModel
import kotlinx.coroutines.launch


class SelectedArtistsFragment : Fragment() {

    private lateinit var binding : SelectedArtistsFragmentBinding
    private val viewModel : SelectedArtistsViewModel by viewModels{
        SelectedArtistsViewModel.Factory()
    }

    private lateinit var artistAdapter: ArtistAdapter

    companion object {
        fun newInstance() = SelectedArtistsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if(savedInstanceState != null){
            val startingList = savedInstanceState.getParcelableArrayList<Artist>("artists") ?: listOf()
            val currentList = savedInstanceState.getParcelableArrayList<Artist>("selected_artists") ?: listOf()
            viewModel.restoreUiState(SelectedArtistsUiState(startingList, currentList, currentList.size))
            return
        }

        val parcels = arguments?.getParcelableArray("artists") ?: return
        val artists = mutableListOf<Artist>()

        for (parcel in parcels) {
            artists.add(parcel as Artist)
        }
        viewModel.restoreUiState(SelectedArtistsUiState(artists, artists, artists.size))
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
                    if(artistAdapter.list.isEmpty())
                        artistAdapter.list = it.startingList

                    artistAdapter.updateSelected(it.selectedArtists)
                    artistAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.selected_artists_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search_artists -> {
                val bundle = bundleOf("artists" to viewModel.currentList.toTypedArray())
                findNavController().navigate(R.id.action_selectedArtistsFragment_to_searchArtistsFragment, bundle)
                true
            }
            R.id.action_create_playlist -> {
                Toast.makeText(requireActivity(), "Clear call log", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}