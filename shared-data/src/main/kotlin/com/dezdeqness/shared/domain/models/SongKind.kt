package com.dezdeqness.shared.domain.models

enum class SongKind {
    TRACK,
    UNKNOWN;

    companion object {
        fun from(value: String): SongKind = when (value.lowercase()) {
            "track" -> TRACK
            else -> UNKNOWN
        }
    }
}
