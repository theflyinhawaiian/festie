package com.mullipr.festie.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mullipr.festie.R
import com.mullipr.festie.databinding.MainPageFragmentBinding
import com.mullipr.festie.viewModel.LoginViewModel

class LoginFragment : Fragment() {
    companion object {
        fun newInstance() = LoginFragment()
        val RC_AUTH = 100;
    }

    private lateinit var binding: MainPageFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainPageFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val loggingOut = arguments?.getBoolean("loggingOut") ?: false

        binding.button.setOnClickListener {
            val authIntent = viewModel.getAuthIntent()
            startActivityForResult(authIntent, RC_AUTH)
        }

        if(loggingOut){
            viewModel.invalidateAuthentication()
            return
        }

        if(viewModel.isUserAuthenticated()){
            findNavController().navigate(R.id.action_loginFragment_to_searchArtistsFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode != RC_AUTH || data == null)
            return

        val authenticated = viewModel.processAuthResponse(data)
        if(authenticated){
            findNavController().navigate(R.id.action_loginFragment_to_searchArtistsFragment)
        }
    }

}