package com.dezdeqness.auth.data.datasource

import com.dezdeqness.auth.domain.model.TokenEntity

interface AuthDatasource {
    suspend fun obtainToken(authCode: String, codeVerifier: String): Result<TokenEntity>
    suspend fun refreshToken(refreshToken: String): Result<TokenEntity>
    suspend fun signOut(): Result<Boolean>
}
