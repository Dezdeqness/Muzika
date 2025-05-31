package com.dezdeqness.auth.data.api

import com.dezdeqness.auth.core.AuthConstants.API_SIGN_OUT
import com.dezdeqness.auth.core.AuthConstants.API_TOKEN
import com.dezdeqness.auth.data.model.api.response.TokenAPI
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.FieldMap
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST(API_TOKEN)
    suspend fun obtainToken(
        @Header("accept") accept: String = "application/json; charset=utf-8",
        @Header("Content-Type") contentType: String = "application/x-www-form-urlencoded",
        @FieldMap map: Map<String, Any>,
    ): Response<TokenAPI>

    @FormUrlEncoded
    @POST(API_TOKEN)
    suspend fun refreshToken(
        @Header("accept") accept: String = "application/json; charset=utf-8",
        @Header("Content-Type") contentType: String = "application/x-www-form-urlencoded",
        @FieldMap map: Map<String, Any>,
    ): Response<TokenAPI>

    @POST(API_SIGN_OUT)
    suspend fun signOut(): Response<Any>

}
