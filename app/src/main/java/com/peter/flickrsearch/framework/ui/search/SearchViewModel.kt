package com.peter.flickrsearch.framework.ui.search

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.peter.flickrsearch.Constants.PER_PAGE
import com.peter.flickrsearch.business.models.ApiStatus
import com.peter.flickrsearch.business.models.Photo
import com.peter.flickrsearch.business.usecases.PhotosUseCase
import com.peter.flickrsearch.framework.datasource.PhotosPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(private val usecase: PhotosUseCase) :
    ViewModel() {


    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _selectedItem = MutableLiveData<Photo?>()
    val selectedItem: LiveData<Photo?>
        get() = _selectedItem


    val query = MutableLiveData<String>()
    var listData = Pager(
        config = PagingConfig(
            pageSize = PER_PAGE,
        ),
        pagingSourceFactory = { PhotosPagingSource(usecase, "") }
    ).flow

    fun displayPropertyDetails(property: Photo) {
        _selectedItem.value = property
    }

    fun displayPropertyDetailsComplete() {
        _selectedItem.value = null
    }

    fun setQuery(query: String) {
        this.query.value = query
        search()
    }

    fun search() {
        viewModelScope.launch {
            listData = query.asFlow()
                .flatMapLatest {
                    Pager(
                        config = PagingConfig(
                            pageSize = PER_PAGE,
                        ),
                        pagingSourceFactory = { PhotosPagingSource(usecase, it) }
                    ).flow
                }
        }

    }

    init {
        search()
    }
}