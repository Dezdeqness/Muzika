package com.dezdeqness.core.network.domain

interface IsRefreshedTokenUseCase {
    suspend operator fun invoke(): Result<Boolean>
}
