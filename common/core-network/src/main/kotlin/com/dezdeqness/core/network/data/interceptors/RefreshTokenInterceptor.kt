package com.dezdeqness.core.network.data.interceptors

import com.dezdeqness.core.network.domain.IsRefreshedTokenUseCase
import com.dezdeqness.core.network.domain.RetrieveAccessTokenUseCase
import com.dezdeqness.core.network.exception.RefreshTokenException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header

class RefreshTokenInterceptor(
    private val isRefreshedTokenUseCase: IsRefreshedTokenUseCase,
    private val retrieveAccessTokenUseCase: RetrieveAccessTokenUseCase
) {
    suspend fun intercept(builder: HttpRequestBuilder) {
        isRefreshedTokenUseCase
            .invoke()
            .onSuccess { isRefreshed ->
                if (isRefreshed) {
                    val token = retrieveAccessTokenUseCase.invoke().getOrNull()
                    builder.header("Authorization", "Bearer $token")
                }
            }
            .onFailure {
                throw RefreshTokenException(it.toString())
            }
    }
}
