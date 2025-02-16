package com.dezdeqness.muzika.di.core

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            prettyPrint = true
            isLenient = true
        }
    }

    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(get<Json>())
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

    single<Ktorfit> {
        Ktorfit
            .Builder()
//            .baseUrl(Constants.Endpoints.BASE_URL)
            .httpClient(get<HttpClient>())
            .converterFactories(ResponseConverterFactory())
            .build()
    }

}
