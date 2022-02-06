package com.peter.flickrsearch.framework.database

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import com.peter.flickrsearch.business.models.Photo
import com.peter.flickrsearch.business.repositories.PhotosRepo
import com.peter.flickrsearch.business.usecases.PhotosUseCase
import javax.inject.Inject

@Database(entities = [Photo::class], version = 1)

abstract class PhotosDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}



