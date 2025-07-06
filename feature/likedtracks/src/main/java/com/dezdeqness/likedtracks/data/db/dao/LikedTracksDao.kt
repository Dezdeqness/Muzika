package com.dezdeqness.likedtracks.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dezdeqness.likedtracks.data.db.model.SongLocal

@Dao
interface LikedTracksDao {
    @Query("SELECT * FROM liked_tracks ORDER BY orderInResponse ASC")
    fun getPagedTracks(): PagingSource<Int, SongLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<SongLocal>)

    @Query("DELETE FROM liked_tracks")
    suspend fun clearAll()

    @Query("SELECT MAX(orderInResponse) FROM liked_tracks")
    suspend fun getMaxOrder(): Int?
}
