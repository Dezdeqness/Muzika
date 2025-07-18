package com.dezdeqness.auth.di

import com.dezdeqness.auth.core.AuthConstants
import com.dezdeqness.auth.data.api.AuthService
import com.dezdeqness.auth.data.api.createAuthService
import com.dezdeqness.core.network.di.Qualifiers
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import io.ktor.client.HttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private const val authKtorfit = "AuthKtorfit"

@Module
@ComponentScan("com.dezdeqness.auth")
class AuthModule {

    @Single
    @Named(authKtorfit)
    fun provideAuthKtorfit(@Named(Qualifiers.defaultClientQualifier) client: HttpClient) =
        Ktorfit
            .Builder()
            .baseUrl(AuthConstants.BASE_URL)
            .httpClient(client)
            .converterFactories(ResponseConverterFactory())
            .build()

    @Single
    fun provideAuthService(
        @Named(authKtorfit) ktorfit: Ktorfit
    ): AuthService = ktorfit.createAuthService()
}
