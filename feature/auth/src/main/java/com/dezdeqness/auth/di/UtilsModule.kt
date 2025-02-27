package com.dezdeqness.auth.di

import com.dezdeqness.auth.utils.PKCEUtils
import org.koin.dsl.module

val utilsModule = module {
    single { PKCEUtils() }
}
