package com.codinginflow.imagesearchapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.codinginflow.imagesearchapp.api.PhotosApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(private val photosApi: PhotosApi) {

    fun getSearchResults() =
        Pager(
            config = PagingConfig(
                pageSize = 4,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotosPagingSource(photosApi) }
        ).liveData
}