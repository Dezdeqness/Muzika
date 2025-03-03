package com.dezdeqness.muzika.di.navigation

import com.dezdeqness.auth.navigation.AuthNavigation
import com.dezdeqness.muzika.navigation.ApplicationNavigation
import org.koin.dsl.module

val navigationModule = module {
    single {
        ApplicationNavigation()
    }

    single<AuthNavigation> {
        get<ApplicationNavigation>()
    }
}
