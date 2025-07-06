package com.dezdeqness.likedtracks.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dezdeqness.likedtracks.data.db.converter.LocalConverter
import com.dezdeqness.likedtracks.data.db.dao.LikedTracksDao
import com.dezdeqness.likedtracks.data.db.model.SongLocal

@TypeConverters(LocalConverter::class)
@Database(entities = [SongLocal::class], version = 1)
abstract class LikedDatabase : RoomDatabase() {

    abstract fun likedTrackDao(): LikedTracksDao
}
