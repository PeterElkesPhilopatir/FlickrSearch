package com.peter.flickrsearch.business.repositories

import com.peter.flickrsearch.business.models.Photo
import com.peter.flickrsearch.framework.datasource.SearchSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PhotosRepo {
    suspend fun getPhotos(query: String,page : Int) : Flow<List<Photo>>
}

class PhotosRepoImpl @Inject constructor(private val search: SearchSource) : PhotosRepo {
    override suspend fun getPhotos(query: String,page : Int) =
        search.searchForPhoto(query,page)
}