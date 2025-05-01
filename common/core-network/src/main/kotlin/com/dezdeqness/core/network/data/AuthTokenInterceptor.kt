package com.dezdeqness.core.network.data

import com.dezdeqness.core.network.domain.RetrieveAccessTokenUseCase
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header

class AuthTokenInterceptor(
    private val useCase: RetrieveAccessTokenUseCase
) {
    suspend fun intercept(builder: HttpRequestBuilder) {
        useCase
            .invoke()
            .onSuccess { token ->
                builder.header("Authorization", "Bearer $token")
            }
    }
}
