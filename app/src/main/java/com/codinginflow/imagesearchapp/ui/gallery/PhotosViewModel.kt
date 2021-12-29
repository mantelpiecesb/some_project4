package com.codinginflow.imagesearchapp.ui.gallery

import android.content.Context
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codinginflow.imagesearchapp.data.PhotosRepository
import java.io.IOException

class PhotosViewModel @ViewModelInject constructor(
    private val repository: PhotosRepository,
) : ViewModel() {

    lateinit var context: Context

    val currentQuery = MutableLiveData("")


    var photos: MutableLiveData<PagingData<String>> = currentQuery.switchMap { queryString ->
        repository.getSearchResults(context).cachedIn(viewModelScope)
    } as MutableLiveData<PagingData<String>>

    fun reloadPhotos() {
        photos = currentQuery.switchMap { queryString ->
            repository.getSearchResults(context).cachedIn(viewModelScope)
        } as MutableLiveData<PagingData<String>>
    }




}