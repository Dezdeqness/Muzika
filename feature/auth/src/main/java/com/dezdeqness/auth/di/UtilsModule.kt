package com.dezdeqness.auth.di

import com.dezdeqness.auth.utils.PKCEUtils
import org.koin.dsl.module

internal val utilsModule = module {
    single { PKCEUtils() }
}
