package com.dezdeqness.core.network.domain

interface RetrieveAccessTokenUseCase {
    suspend operator fun invoke(): Result<String>
}
