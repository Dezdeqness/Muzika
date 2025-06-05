package com.dezdeqness.core.network.di

import com.dezdeqness.core.network.core.CoreConstants
import com.dezdeqness.core.network.data.interceptors.AuthTokenInterceptor
import com.dezdeqness.core.network.data.interceptors.RefreshTokenInterceptor
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
import org.koin.dsl.module

val coreNetworkModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            prettyPrint = true
            isLenient = true
        }
    }

    single {
        AuthTokenInterceptor(
            useCase = get()
        )
    }

    single {
        RefreshTokenInterceptor(
            isRefreshedTokenUseCase = get(),
        )
    }

    single<HttpClient>(qualifier = Qualifiers.defaultClientQualifier) {
        HttpClient {
            install(ContentNegotiation) {
                json(get<Json>())
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.Companion.SIMPLE
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

    single<HttpClient>(qualifier = Qualifiers.withAuthClientQualifier) {
        HttpClient {
            install(ContentNegotiation) {
                json(get<Json>())
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
                get<RefreshTokenInterceptor>().intercept()
                get<AuthTokenInterceptor>().intercept(context)
                proceed()
            }
        }
    }

    single<Ktorfit>(qualifier = Qualifiers.sharedKtorfitQualified) {
        Ktorfit
            .Builder()
            .baseUrl(CoreConstants.API_URL)
            .httpClient(get<HttpClient>(Qualifiers.withAuthClientQualifier))
            .converterFactories(ResponseConverterFactory())
            .build()
    }

}
