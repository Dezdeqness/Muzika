package com.dezdeqness.likedtracks.di

import androidx.room.Room
import com.dezdeqness.likedtracks.data.db.LikedDatabase
import org.koin.dsl.module

val databaseModule = module {

    single<LikedDatabase> {
        Room
            .databaseBuilder(
                context = get(),
                klass = LikedDatabase::class.java,
                name = "liked.db"
            )
            .build()
    }
}
