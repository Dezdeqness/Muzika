package com.dezdeqness.auth.domain.usecase

import com.dezdeqness.auth.data.provider.TokenDataProvider
import com.dezdeqness.auth.domain.repository.AuthRepository
import com.dezdeqness.core.network.domain.IsRefreshedTokenUseCase
import org.koin.core.annotation.Single

@Single(binds = [IsRefreshedTokenUseCase::class])
class IsRefreshedTokenUseCaseImpl(
    private val authRepository: AuthRepository,
    private val tokenDataProvider: TokenDataProvider,
) : IsRefreshedTokenUseCase {
    override suspend operator fun invoke(): Result<Boolean> {

        if (authRepository.isTokenExpired().not()) {
            return Result.success(false)
        }

        return authRepository
            .refreshToken()
            .onSuccess { tokenDataProvider.setTokenData(it) }
            .map { true }
    }

}
