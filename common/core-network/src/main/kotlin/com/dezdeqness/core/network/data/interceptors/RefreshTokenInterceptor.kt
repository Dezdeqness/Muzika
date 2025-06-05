package com.dezdeqness.core.network.data.interceptors

import com.dezdeqness.core.network.domain.IsRefreshedTokenUseCase
import com.dezdeqness.core.network.exception.RefreshTokenException

class RefreshTokenInterceptor(
    private val isRefreshedTokenUseCase: IsRefreshedTokenUseCase,
) {
    suspend fun intercept() {
        isRefreshedTokenUseCase
            .invoke()
            .onFailure {
                throw RefreshTokenException(it.toString())
            }
    }
}
