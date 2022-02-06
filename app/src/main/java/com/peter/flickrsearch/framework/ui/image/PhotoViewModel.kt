package com.peter.flickrsearch.framework.ui.image

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.peter.flickrsearch.business.models.Photo

class PhotoViewModel(@Suppress("UNUSED_PARAMETER") app: Application) :
    AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<Photo>()
    val selectedProperty: LiveData<Photo>
        get() = _selectedProperty

    fun setData(photo: Photo){
        _selectedProperty.value = photo
    }
}