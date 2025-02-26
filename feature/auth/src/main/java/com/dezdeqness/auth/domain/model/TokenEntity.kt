package com.dezdeqness.auth.domain.model

data class TokenEntity(
    val accessToken: String,
    val refreshToken: String,
    val createdIn: Long,
    val expiresIn: Long,
) {
    val isExpired: Boolean
        get() {
            val currentTime = System.currentTimeMillis() / 1000
            return currentTime >= createdIn + expiresIn + TIME_SHIFT
        }

    companion object {
        private const val TIME_SHIFT = 60
    }
}
