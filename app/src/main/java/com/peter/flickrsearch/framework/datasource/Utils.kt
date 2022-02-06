package com.peter.flickrsearch.framework.datasource

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.peter.flickrsearch.business.models.Photo
import com.peter.flickrsearch.framework.database.PhotosDatabase
import com.peter.flickrsearch.framework.datasource.Utils.parseToPlainObject
import com.peter.flickrsearch.framework.datasource.responses.PhotoResponse

object Utils {
    fun PhotoResponse.parseToPlainObject() = Photo(
        id = this.id ?: "",
        title = this.title ?: "",
        farm = this.farm ?: "",
        isFamily = this.isFamily == 1,
        isFriend = this.isFriend == 1,
        isPublic = this.isPublic == 1,
        owner = this.owner ?: "",
        secret = this.secret ?: "",
        server = this.server ?: "",
    )

}