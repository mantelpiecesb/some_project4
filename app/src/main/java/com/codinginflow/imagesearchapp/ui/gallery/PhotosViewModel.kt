package com.codinginflow.imagesearchapp.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.codinginflow.imagesearchapp.data.PhotosRepository

class PhotosViewModel @ViewModelInject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    val currentQuery = MutableLiveData("")

    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults().cachedIn(viewModelScope)
    }


}