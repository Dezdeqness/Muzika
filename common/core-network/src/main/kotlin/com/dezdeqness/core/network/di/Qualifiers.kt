package com.dezdeqness.core.network.di

import org.koin.core.qualifier.named

object Qualifiers {
    val defaultClientQualifier = named("HttpClient_default")
    val withAuthClientQualifier = named("HttpClient_withAuth")
    val sharedKtorfitQualified = named("SharedKtorfit")
}
