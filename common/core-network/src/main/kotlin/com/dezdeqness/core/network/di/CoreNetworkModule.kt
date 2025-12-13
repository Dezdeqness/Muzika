package com.dezdeqness.core.network.di

import com.dezdeqness.core.network.core.CoreConstants
import com.dezdeqness.core.network.data.interceptors.AuthTokenInterceptor
import com.dezdeqness.core.network.data.interceptors.RefreshTokenInterceptor
import com.dezdeqness.core.network.domain.IsRefreshedTokenUseCase
import com.dezdeqness.core.network.domain.RetrieveAccessTokenUseCase
import com.dezdeqness.core.network.event.AppEventHandler
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Module
class CoreNetworkModule {
    @Single
    fun provideAppEventHandler(): AppEventHandler = AppEventHandler()

    @Single
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        prettyPrint = true
        isLenient = true
    }

    @Single
    fun provideAuthTokenInterceptor(
        @Provided useCase: RetrieveAccessTokenUseCase
    ): AuthTokenInterceptor = AuthTokenInterceptor(useCase)

    @Single
    fun provideRefreshTokenInterceptor(
        @Provided isRefreshedTokenUseCase: IsRefreshedTokenUseCase,
        @Provided appEventHandler: AppEventHandler,
    ): RefreshTokenInterceptor = RefreshTokenInterceptor(isRefreshedTokenUseCase, appEventHandler)

    @Single
    @Named(Qualifiers.defaultClientQualifier)
    fun provideDefaultHttpClient(json: Json) =
        HttpClient {
            install(ContentNegotiation) {
                json(json)
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.Companion.SIMPLE
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }


    @Single
    @Named(Qualifiers.withAuthClientQualifier)
    fun provideHttpClientWithAuth(
        json: Json,
        refreshTokenInterceptor: RefreshTokenInterceptor,
        authTokenInterceptor: AuthTokenInterceptor
    ) =
        HttpClient {
            install(ContentNegotiation) {
                json(json)
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.Companion.SIMPLE
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }.apply {
            requestPipeline.intercept(HttpRequestPipeline.State) {
                refreshTokenInterceptor.intercept()
                authTokenInterceptor.intercept(context)
                proceed()
            }
        }


    @Single
    @Named(Qualifiers.sharedKtorfitQualified)
    fun provideKtorfit(@Named(Qualifiers.withAuthClientQualifier) httpClient: HttpClient) =
        Ktorfit
            .Builder()
            .baseUrl(CoreConstants.API_URL)
            .httpClient(httpClient)
            .converterFactories(ResponseConverterFactory())
            .build()

}
