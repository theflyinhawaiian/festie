package com.mullipr.festie.viewModel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.mullipr.festie.auth.IOAuthService
import com.mullipr.festie.model.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val oAuthService : IOAuthService) : ViewModel(){
    private val _uiState : MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState(false, false))
    val uiState : StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun initialize(loggingOut : Boolean){
        if(loggingOut){
            oAuthService.invalidateTokens()
        }

        if(oAuthService.isUserAuthenticated()){
            _uiState.update {
                it.copy(loginSucceeded = true, loginFinished = true)
            }
        }
    }

    fun processAuthResponse(data : Intent?) = oAuthService.processAuthResponse(data) {
        _uiState.update {
            it.copy(loginSucceeded = true, loginFinished = true)
        }
    }
}