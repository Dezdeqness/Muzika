package com.dezdeqness.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dezdeqness.auth.core.AuthConstants
import com.dezdeqness.auth.data.datasource.AuthDatasource
import com.dezdeqness.auth.data.provider.AuthorizationUrlProvider
import com.dezdeqness.auth.utils.PKCEUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class AuthViewModel(
    private val authDatasource: AuthDatasource,
    private val authUrlProvider: AuthorizationUrlProvider,
    private val utils: PKCEUtils,
) : ViewModel() {

    private val _events = Channel<AuthEvent>()
    val events = _events.receiveAsFlow()

    private var verifier = ""
    private var challenge = ""
    private var secureString = ""

    fun onAuthorizedClick() {
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
            viewModelScope.launch(Dispatchers.IO) {
                authDatasource
                    .obtainToken(authCode = code, codeVerifier = verifier)
                    .onSuccess {

                    }
                    .onFailure {

                    }

            }
        }
    }

    companion object {
        private const val CODE_KEY = "code"
        private const val STATE_KEY = "state"
    }


}

//dezdeqness://
// aqua/auth?
// code=eyJlbmMiOiJBMTI4Q0JDLUhTMjU2IiwiYWxnIjoiQTI1NktXIn0.I4uQlvzbl7pvtJnq3XX77L9HBJG2da5lxmuA8UnvTGOxNx1bTSYcwA.TAc6ajjTnJ2saacIQ4cLng._Z0oNyKk8UaQpBnWKr4l2Qu1uIGNoj1zfH6GrBiOUE_Cw4cimKRHJhCSL7zGoJHFo2wNiQq944zzt12o2SbVxvJVXrYmTVGwvvYTXbC9VfuVrvSnT3fFAERlYNpVtc3ikwt_3-6XVVtldM_8FixJfKvaVHVaT-clAv_Msn12xUAvmkjNAV6gVbpldimDskKRV4ICo06YTMm3YSxX6piZwKrpTfA5Z2N2Gi7Jo5a8xD6-OfFXghpoRYKrc_ZwYrPl.n5MeIRQYJDZxkS4WpirDrg
// &state=xyXN1Of2YnfGYiGP8i4osM3qfWO0qTHGOqutZkmrnwXexsFPjmwvDbglrQtXBAqj

// dezdeqness://aqua/auth?code=eyJlbmMiRQYJDZxkS4WpirDrg&state=xyXN1Of2YwvDbglrQtXBAqj