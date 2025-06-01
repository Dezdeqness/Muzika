package com.dezdeqness.auth.domain.usecase

import com.dezdeqness.auth.data.provider.TokenDataProvider
import com.dezdeqness.core.network.domain.RetrieveAccessTokenUseCase

class RetrieveAccessTokenUseCaseImpl(
    private val tokenDataProvider: TokenDataProvider,
) : RetrieveAccessTokenUseCase {

    override suspend fun invoke() =
        Result.success(tokenDataProvider.getTokenData()?.accessToken.orEmpty())

}
