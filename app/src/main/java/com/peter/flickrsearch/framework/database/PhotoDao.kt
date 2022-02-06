package com.peter.flickrsearch.framework.database

import androidx.room.*
import com.peter.flickrsearch.business.models.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo WHERE title LIKE '%' || :query || '%'")
    fun getFiltered(query: String): List<Photo>

    @Query("SELECT * FROM photo ")
    fun getAll(): List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Photo>)
}