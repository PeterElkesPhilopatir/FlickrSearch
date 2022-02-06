package com.peter.flickrsearch.framework.datasource

import com.peter.flickrsearch.business.models.Photo
import com.peter.flickrsearch.framework.datasource.responses.MainResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface ApiService {

    @GET("services/rest")
    fun getPhotosAsync(
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: Int,
        @Query("text") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("api_key") apikey: String
    ):
            Deferred<MainResponse>
}