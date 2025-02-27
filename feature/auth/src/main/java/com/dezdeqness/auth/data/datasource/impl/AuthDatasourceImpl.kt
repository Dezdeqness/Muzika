package com.dezdeqness.auth.data.datasource.impl

import com.dezdeqness.auth.core.AuthConstants
import com.dezdeqness.auth.data.api.AuthService
import com.dezdeqness.auth.data.datasource.AuthDatasource
import com.dezdeqness.auth.data.mapper.TokenDataMapper
import com.dezdeqness.auth.domain.model.TokenEntity

class AuthDatasourceImpl(
    private val authService: AuthService,
    private val tokenDataMapper: TokenDataMapper,
) : AuthDatasource {

    override suspend fun obtainToken(
        authCode: String,
        codeVerifier: String
    ) = tryWithCatch {
        val response = authService.obtainToken(
            map = mapOf(
                "grant_type" to "authorization_code",
                "client_id" to AuthConstants.CLIENT_ID,
                "client_secret" to AuthConstants.CLIENT_SECRET,
                "redirect_uri" to AuthConstants.REDIRECT_URI,
                "code_verifier" to codeVerifier,
                "code" to authCode,
            )
        )

        if (response.isSuccessful) {
            val body = response.body()
                ?: return@tryWithCatch Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))

            Result.success(tokenDataMapper.from(body))
        } else {
            // TODO: custom APIException
            Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))
        }
    }

    override suspend fun refreshToken(refreshToken: String) = tryWithCatch {
        val response = authService.refreshToken(
            map = mapOf(
                "grant_type" to "refresh_token",
                "client_id" to AuthConstants.CLIENT_ID,
                "refresh_token" to refreshToken,
            )
        )
        if (response.isSuccessful) {
            val body = response.body()
                ?: return@tryWithCatch Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))


            Result.success(TokenEntity("", "", 0, 0))

        } else {
            // TODO: custom APIException
            Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))
        }
    }

    override suspend fun signOut() = tryWithCatch {
        val response = authService.signOut()

        if (response.isSuccessful) {
            val body = response.body()
                ?: return@tryWithCatch Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))

            Result.success(true)
        } else {
            // TODO: custom APIException
            Result.failure(Throwable("Code: ${response.code}\nError: ${response.errorBody()}"))
        }
    }


    private suspend fun <T> tryWithCatch(block: suspend () -> Result<T>) = try {
        block()
    } catch (throwable: Throwable) {
        Result.failure(throwable)
    }

}
