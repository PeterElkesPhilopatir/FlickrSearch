package com.peter.flickrsearch.business.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "photo")
data class Photo(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "owner")var owner: String,
    @ColumnInfo(name = "secret")var secret: String,
    @ColumnInfo(name = "server")var server: String,
    @ColumnInfo(name = "farm")var farm: String,
    @ColumnInfo(name = "title")var title: String,
    @ColumnInfo(name = "isPublic")var isPublic: Boolean,
    @ColumnInfo(name = "isFriend")var isFriend: Boolean,
    @ColumnInfo(name = "isFamily")var isFamily: Boolean,

) : Parcelable {
    fun getPhotoLink() = "http://farm$farm.static.flickr.com/$server/" + id + "_" + secret + ".jpg"

}
