package com.mullipr.festie.viewModel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mullipr.festie.auth.OAuthService
import com.mullipr.festie.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(private val oAuthService : OAuthService,
                     loggingOut : Boolean) : ViewModel(){
    private val _uiState : MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState(false, false))
    val uiState : StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
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

    class Factory(private val oAuthService: OAuthService,
                  private val loggingOut : Boolean) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass : Class<T>) : T {
            return LoginViewModel(oAuthService, loggingOut) as T
        }
    }
}