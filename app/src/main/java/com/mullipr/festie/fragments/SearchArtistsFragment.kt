package com.mullipr.festie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mullipr.festie.databinding.SearchArtistsFragmentBinding
import com.mullipr.festie.viewModel.SearchArtistsViewModel

class SearchArtistsFragment : Fragment(){
    private lateinit var binding : SearchArtistsFragmentBinding
    private lateinit var viewModel : SearchArtistsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchArtistsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchArtistsViewModel::class.java]
    }
}