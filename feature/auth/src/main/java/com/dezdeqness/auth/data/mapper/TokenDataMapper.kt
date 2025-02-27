package com.dezdeqness.auth.data.mapper

import com.dezdeqness.auth.data.model.api.response.TokenAPI
import com.dezdeqness.auth.domain.model.TokenEntity

class TokenDataMapper {

    fun from(tokenAPI: TokenAPI) = TokenEntity(
        accessToken = tokenAPI.accessToken,
        refreshToken = tokenAPI.refreshToken,
        createdIn = System.currentTimeMillis() / 1000,
        expiresIn = tokenAPI.expiresIn,
    )

}
