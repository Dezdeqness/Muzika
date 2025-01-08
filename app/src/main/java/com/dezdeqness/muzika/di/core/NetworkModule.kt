package com.dezdeqness.muzika.di.core

import com.dezdeqness.innertube.core.Constants
import com.dezdeqness.innertube.service.NextService
import com.dezdeqness.innertube.service.PlayerService
import com.dezdeqness.innertube.service.SearchService
import com.dezdeqness.innertube.service.createNextService
import com.dezdeqness.innertube.service.createPlayerService
import com.dezdeqness.innertube.service.createSearchService
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
            .baseUrl(Constants.Endpoints.BASE_URL)
            .httpClient(get<HttpClient>())
            .converterFactories(ResponseConverterFactory())
            .build()
    }

    single<NextService> {
        get<Ktorfit>().createNextService()
    }

    single<PlayerService> {
        get<Ktorfit>().createPlayerService()
    }

    single<SearchService> {
        get<Ktorfit>().createSearchService()
    }

}
