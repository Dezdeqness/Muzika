package com.dezdeqness.core.network.data.interceptors

import com.dezdeqness.core.network.domain.IsRefreshedTokenUseCase
import com.dezdeqness.core.network.event.AppEvent
import com.dezdeqness.core.network.event.AppEventHandler
import com.dezdeqness.core.network.exception.RefreshTokenException

class RefreshTokenInterceptor(
    private val isRefreshedTokenUseCase: IsRefreshedTokenUseCase,
    private val appEventHandler: AppEventHandler,
) {
    suspend fun intercept() {
        isRefreshedTokenUseCase
            .invoke()
            .onFailure {
                appEventHandler.emit(AppEvent.SessionExpired)
                throw RefreshTokenException(it.toString())
            }
    }
}
