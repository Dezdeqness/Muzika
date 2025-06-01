package com.dezdeqness.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dezdeqness.auth.core.AuthConstants
import com.dezdeqness.auth.data.provider.AuthorizationUrlProvider
import com.dezdeqness.auth.utils.PKCEUtils
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.dezdeqness.auth.domain.repository.AuthRepository
import com.dezdeqness.auth.domain.usecase.LoginUseCase
import com.dezdeqness.core.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val authRepository: AuthRepository,
    private val authUrlProvider: AuthorizationUrlProvider,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val utils: PKCEUtils,
) : ViewModel() {

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    private val _events = Channel<AuthEvent>()
    val events = _events.receiveAsFlow()

    private var verifier = ""
    private var challenge = ""
    private var secureString = ""

    init {
        viewModelScope.launch(coroutineDispatcherProvider.io()) {
            if (authRepository.isLoggedIn()) {
                delay(1000)
                _events.send(AuthEvent.NavigateMainFlow)
            } else {
                _authState.update {
                    it.copy(
                        state = ScreenState.NotLoggedIn,
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun onAuthorizedClick() {
        if (_authState.value.isLoading) return

        verifier = utils.generateCodeVerifier()
        challenge = utils.generateCodeChallenge(verifier)
        secureString = utils.randomString(64)

        val url = authUrlProvider.composeAuthUrl(codeChallenge = challenge, state = secureString)

        viewModelScope.launch {
            _events.send(AuthEvent.OpenUrl(url))
        }
    }

    fun onHandleDeeplink(data: String) {
        val uri = data.toUri()
        val code = uri.getQueryParameter(CODE_KEY)
        val state = uri.getQueryParameter(STATE_KEY)

        if (data.contains(AuthConstants.REDIRECT_URI)
            && state == secureString
            && code.isNullOrEmpty().not()
        ) {
            _authState.update {
                it.copy(isLoading = true)
            }
            viewModelScope.launch(coroutineDispatcherProvider.io()) {
                loginUseCase
                    .invoke(authCode = code, codeVerifier = verifier)
                    .onSuccess {
                        _authState.update {
                            it.copy(isLoading = false)
                        }
                        _events.send(AuthEvent.NavigateMainFlow)
                    }
                    .onFailure {
                        _authState.update {
                            it.copy(isLoading = false)
                        }
                        _events.send(AuthEvent.Failure)
                    }

            }
        }
    }

    companion object {
        private const val CODE_KEY = "code"
        private const val STATE_KEY = "state"
    }

}
