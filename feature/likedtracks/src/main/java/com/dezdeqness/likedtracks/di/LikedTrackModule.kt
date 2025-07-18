package com.dezdeqness.likedtracks.di

import android.content.Context
import androidx.room.Room
import com.dezdeqness.core.network.di.Qualifiers
import com.dezdeqness.likedtracks.data.api.LikedService
import com.dezdeqness.likedtracks.data.api.createLikedService
import com.dezdeqness.likedtracks.data.db.LikedDatabase
import com.dezdeqness.likedtracks.presentation.mapper.LikedTrackMapper
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.dezdeqness.likedtracks")
class LikedTracksModule {
    @Single
    fun provideLikedDatabase(@Provided context: Context): LikedDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = LikedDatabase::class.java,
            name = "liked.db"
        ).build()
    }

    @Single
    fun provideLikedTrackMapper() = LikedTrackMapper()

    @Single
    fun provideLikedService(@Named(Qualifiers.sharedKtorfitQualified) ktorfit: Ktorfit): LikedService {
        return ktorfit.createLikedService()
    }
}
