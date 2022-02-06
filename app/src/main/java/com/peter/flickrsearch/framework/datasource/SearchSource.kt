package com.peter.flickrsearch.framework.datasource

import android.util.Log
import androidx.lifecycle.Transformations
import com.peter.flickrsearch.BuildConfig.API_KEY
import com.peter.flickrsearch.Constants.FORMAT
import com.peter.flickrsearch.Constants.METHOD
import com.peter.flickrsearch.Constants.NO_JSON_CALLBACK
import com.peter.flickrsearch.Constants.OK
import com.peter.flickrsearch.Constants.PER_PAGE
import com.peter.flickrsearch.business.models.Photo
import com.peter.flickrsearch.framework.database.PhotoDao
import com.peter.flickrsearch.framework.database.PhotosDatabase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.peter.flickrsearch.framework.datasource.Utils.parseToPlainObject

interface SearchSource {
    suspend fun searchForPhoto(
        query: String, page: Int
    ): Flow<List<Photo>>
}

class SearchSourceImpl @Inject constructor(
    private val api: ApiService,
    private val dao: PhotoDao
) : SearchSource {

    override suspend fun searchForPhoto(query: String, page: Int): Flow<List<Photo>> =
        flow {
            try {
                val response =
                    api.getPhotosAsync(
                        method = METHOD,
                        format = FORMAT,
                        nojsoncallback = NO_JSON_CALLBACK,
                        query = query,
                        page = page,
                        per_page = PER_PAGE,
                        apikey = API_KEY
                    ).await()
                try {
                    val list = response.photos!!.photo
                    if (response.stat.equals(OK)) {
                        emit(list!!.filter { it.title != null }
                            .map { it.parseToPlainObject() })

                        dao.insertAll(list.map { it.parseToPlainObject() })
                    }
                } catch (e: Exception) {
                    Log.e("NETWORK_ERROR", e.message.toString())
                    val photos = dao.getFiltered(query)
                    emit(photos)
                }
            } catch (e: Throwable) {
                val photos = dao.getFiltered(query)
                emit(photos)
            }

        }.flowOn(IO)
}






