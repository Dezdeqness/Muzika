package com.dezdeqness.shared.di

import com.dezdeqness.shared.data.mapper.SongMapper
import org.koin.dsl.module

val sharedModule = module {
    single {
        SongMapper()
    }
}
