package com.peter.flickrsearch.business.usecases

import com.peter.flickrsearch.business.models.Photo
import com.peter.flickrsearch.business.repositories.PhotosRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PhotosUseCase {
    suspend fun getPhotos(query: String,page : Int) : Flow<List<Photo>>
}

class PhotosUseCaseImpl @Inject constructor(private val repo: PhotosRepo) :
    PhotosUseCase {
    override suspend fun getPhotos(query: String,page : Int) =
        repo.getPhotos(query,page)
}