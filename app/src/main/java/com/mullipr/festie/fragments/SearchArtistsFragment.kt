package com.mullipr.festie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mullipr.festie.api.ApiService
import com.mullipr.festie.api.endpoints.SearchResource
import com.mullipr.festie.databinding.SearchArtistsFragmentBinding
import com.mullipr.festie.viewModel.SearchArtistsViewModel

class SearchArtistsFragment : Fragment(){
    private lateinit var binding : SearchArtistsFragmentBinding

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
        return binding.root
    }
}