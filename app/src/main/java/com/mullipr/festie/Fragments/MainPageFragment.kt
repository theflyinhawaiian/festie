package com.mullipr.festie.Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mullipr.festie.R
import com.mullipr.festie.databinding.MainPageFragmentBinding

class MainPageFragment : Fragment() {
    companion object {
        fun newInstance() = MainPageFragment()
    }

    private lateinit var binding: MainPageFragmentBinding
    private lateinit var viewModel: MainPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainPageFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
        binding.button.setOnClickListener {
        }
    }

}