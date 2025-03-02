package com.dezdeqness.auth.data.repository

import com.dezdeqness.auth.data.datasource.AuthDatasource
import com.dezdeqness.auth.data.exception.TokenNotExistException
import com.dezdeqness.auth.data.provider.TokenDataProvider
import com.dezdeqness.auth.domain.model.TokenEntity
import com.dezdeqness.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authDatasource: AuthDatasource,
    private val tokenDataProvider: TokenDataProvider,
) : AuthRepository {
    override suspend fun obtainToken(authCode: String, codeVerifier: String) =
        authDatasource.obtainToken(authCode, codeVerifier)

    override suspend fun refreshToken(): Result<TokenEntity> {
        val tokenData =
            tokenDataProvider.getTokenData() ?: return Result.failure(TokenNotExistException())

        return authDatasource.refreshToken(refreshToken = tokenData.refreshToken)
    }

    override suspend fun signOut() = authDatasource.signOut()

    override suspend fun isTokenExpired() = tokenDataProvider.getTokenData()?.isExpired == true


}
