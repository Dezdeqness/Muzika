package com.dezdeqness.core.network.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

enum class AppEvent {
    SessionExpired,
}

class AppEventHandler {

    private val _events = MutableSharedFlow<AppEvent>(
        extraBufferCapacity = 1,
        replay = 0,
    )
    val events: SharedFlow<AppEvent> = _events

    suspend fun emit(event: AppEvent) {
        _events.emit(event)
    }
}
