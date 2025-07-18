package com.dezdeqness.auth.data.provider

import com.dezdeqness.auth.core.AuthConstants
import org.koin.core.annotation.Single
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Single
class AuthorizationUrlProvider {

    fun composeAuthUrl(codeChallenge: String, state: String) = buildString {
        val encodedRedirectUri = URLEncoder.encode(AuthConstants.REDIRECT_URI, StandardCharsets.UTF_8.toString())
        append(AuthConstants.BASE_URL)
        append(AuthConstants.AUTHORIZE_URL)
        append("?${AuthConstants.CLIENT_ID_KEY}=${AuthConstants.CLIENT_ID}")
        append("&${AuthConstants.REDIRECT_URI_KEY}=${AuthConstants.REDIRECT_URI}")
        append("&response_type=code")
        append("&code_challenge=$codeChallenge")
        append("&code_challenge_method=S256")
        append("&state=$state")
        append("&display=popup")

    }

}
