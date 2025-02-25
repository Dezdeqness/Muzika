package com.dezdeqness.auth.data.api

interface AuthService {

}
//
//dezdeqness://aqua/auth
//$ https://secure.soundcloud.com/authorize \
//     ?client_id=YOUR_CLIENT_ID \
//     &redirect_uri=YOUR_REDIRECT_URI \
//     &response_type=code \
//     &code_challenge=CODE_CHALLENGE \
//     &code_challenge_method=S256 \
//     &state=STATE
//3. Obtain Access Token
//If the user approves your authorization request, they will be sent to the redirect_uri you specified when registering your app. Your application should extract the code parameter from the query string and use it to obtain an access token.
//
//Curl
//# obtain the access token
//
//$ curl -X POST "https://secure.soundcloud.com/oauth/token" \
//     -H  "accept: application/json; charset=utf-8" \
//     -H  "Content-Type: application/x-www-form-urlencoded" \
//     --data-urlencode "grant_type=authorization_code" \
//     --data-urlencode "client_id=YOUR_CLIENT_ID" \
//     --data-urlencode "client_secret=YOUR_CLIENT_SECRET" \
//     --data-urlencode "redirect_uri=YOUR_REDIRECT_URI" \
//     --data-urlencode "code_verifier=YOUR_PKCE_GENERATED_CODE_VERIFIER" \
//     --data-urlencode "code=YOUR_CODE"

//Refreshing Tokens
//As the access tokens expire you will need to periodically refresh them. Currently a token lives around 1 hour. You can set an automatic process that checks the expiration time of a current token and updates it using the provided refresh_token. Each refresh token can only be used once.
//
//Note: Currently, all clients are treated as confidential rather than public, meaning that a secret is required to obtain a token.
//
//Curl
//# refresh token
//
//$ curl -X POST "https://secure.soundcloud.com/oauth/token" \
//     -H  "accept: application/json; charset=utf-8" \
//     -H  "Content-Type: application/x-www-form-urlencoded" \
//     --data-urlencode "grant_type=refresh_token" \
//     --data-urlencode "client_id=YOUR_CLIENT_ID" \
//     --data-urlencode "client_secret=YOUR_CLIENT_SECRET" \
//     --data-urlencode "refresh_token=YOUR_TOKEN" \

//display=popup

//$ curl -X POST "https://secure.soundcloud.com/sign-out"
//
//# JSON body:
//  {
//    "access_token": "the-application-access-token"
//  }