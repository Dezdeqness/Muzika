package com.dezdeqness.auth.di

import com.dezdeqness.auth.core.AuthConstants
import com.dezdeqness.core.network.di.Qualifiers
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val authKtorfitQualified = named("AuthKtorfit")

internal val networkModule = module {
    single<Ktorfit>(qualifier = authKtorfitQualified) {
        Ktorfit
            .Builder()
            .baseUrl(AuthConstants.BASE_URL)
            .httpClient(get<HttpClient>(Qualifiers.defaultClientQualifier))
            .converterFactories(ResponseConverterFactory())
            .build()
    }

}
