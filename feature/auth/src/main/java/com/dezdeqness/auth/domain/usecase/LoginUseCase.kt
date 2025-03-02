package com.dezdeqness.auth.domain.usecase

import com.dezdeqness.auth.data.provider.TokenDataProvider
import com.dezdeqness.auth.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val tokenDataProvider: TokenDataProvider,
) {

    suspend operator fun invoke(authCode: String, codeVerifier: String): Result<Boolean> {
        val tokenResult = authRepository.obtainToken(authCode = authCode, codeVerifier = codeVerifier)

        tokenResult.onFailure { throwable ->
            return Result.failure(throwable)
        }

        tokenResult.onSuccess {
            tokenDataProvider.setTokenData(it)
        }

        return Result.success(true)
    }

}
