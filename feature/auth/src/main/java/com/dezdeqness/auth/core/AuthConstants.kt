package com.dezdeqness.auth.core

object AuthConstants {
    const val BASE_URL = "https://secure.soundcloud.com/"
    const val API_TOKEN = "oauth/token"
    const val AUTHORIZE_URL = "authorize"

    const val CLIENT_ID = "akSf2mgMTEtaPGqRtfGbg6e1HaQa5ONv"
    const val CLIENT_SECRET = "nwuyeWnynThN6N3Em2DzxWbCy86b7sdj"

    const val SECRET_ID_KEY = "secret_id"
    const val CLIENT_SECRET_KEY = "client_secret"
}

//
// https://secure.soundcloud.com/authorize?client_id=akSf2mgMTEtaPGqRtfGbg6e1HaQa5ONv&redirect_uri=&response_type=code&code_challenge=fae538f5715ff8d613795bf3155bbfca7234286fe44be0b4437fbdb0&code_challenge_method=S256&state=aqua"

// ver = fae538f5715ff8d613795bf3155bbfca7234286fe44be0b4437fbdb0
// chal = fae538f5715ff8d613795bf3155bbfca7234286fe44be0b4437fbdb0