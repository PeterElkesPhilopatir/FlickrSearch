package com.peter.flickrsearch.framework.datasource

import androidx.paging.PagingSource
import com.peter.flickrsearch.business.models.ApiStatus
import com.peter.flickrsearch.business.models.Photo
import com.peter.flickrsearch.business.usecases.PhotosUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException
import java.net.HttpRetryException

const val START_PAGE = 1

class PhotosPagingSource(
    private val useCase: PhotosUseCase,
    private val query: String
) : PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: START_PAGE
        var list = ArrayList<Photo>()
        return try {
            useCase.getPhotos(query, position)
                .collectLatest {
                    list = it as ArrayList<Photo>
                }
            LoadResult.Page(
                data = list,
                prevKey = if (position == START_PAGE) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1
            )
        } catch (ex : IOException){
            LoadResult.Error(ex)
        } catch (ex : Exception){
            LoadResult.Error(ex)
        }
    }

}