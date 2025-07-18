package com.dezdeqness.muzika.di.navigation

import com.dezdeqness.muzika.navigation.ApplicationNavigation
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.dezdeqness.muzika")
class NavigationModule {

    @Single
    fun provideAuthNavigation(navigation: ApplicationNavigation) =
        navigation
}
