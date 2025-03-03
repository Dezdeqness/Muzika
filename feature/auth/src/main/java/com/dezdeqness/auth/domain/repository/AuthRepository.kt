package com.dezdeqness.auth.domain.repository

import com.dezdeqness.auth.domain.model.TokenEntity

interface AuthRepository {
    suspend fun obtainToken(authCode: String, codeVerifier: String): Result<TokenEntity>
    suspend fun refreshToken(): Result<TokenEntity>
    suspend fun signOut(): Result<Boolean>
    suspend fun isTokenExpired(): Boolean
    suspend fun isLoggedIn(): Boolean
}