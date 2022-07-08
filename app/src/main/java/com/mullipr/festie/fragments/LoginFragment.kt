package com.mullipr.festie.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mullipr.festie.R
import com.mullipr.festie.auth.OAuthService
import com.mullipr.festie.databinding.MainPageFragmentBinding
import com.mullipr.festie.viewModel.LoginViewModel
import kotlinx.coroutines.flow.collect

class LoginFragment : Fragment() {
    companion object {
        fun newInstance() = LoginFragment()
        val RC_AUTH = 100;
    }

    private lateinit var binding: MainPageFragmentBinding
    private lateinit var oAuthService : OAuthService
    private val viewModel : LoginViewModel by viewModels {
        LoginViewModel.Factory(oAuthService, arguments?.getBoolean("loggingOut") ?: false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainPageFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        oAuthService = OAuthService(requireContext())

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                if(it.loginSucceeded){
                    findNavController().navigate(R.id.action_loginFragment_to_searchArtistsFragment)
                }
            }
        }

        binding.button.setOnClickListener {
            val authIntent = oAuthService.getAuthIntent()
            startActivityForResult(authIntent, RC_AUTH)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode != RC_AUTH || data == null)
            return

        viewModel.processAuthResponse(data)
    }

}