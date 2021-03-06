package com.codinginflow.imagesearchapp.data

import android.content.Context
import androidx.paging.PagingSource
import com.codinginflow.imagesearchapp.api.PhotosApi
import com.codinginflow.imagesearchapp.model.Photo
import com.deitel.redtesttask1_dollarcoursechecker.db.PhotosDatabase
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class PhotosPagingSource(
    private val photosApi: PhotosApi,
    private val context: Context
) : PagingSource<Int, String>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        val position = params.key ?: STARTING_PAGE_INDEX

        val photosDao = PhotosDatabase.getInstance(context).photosDao()

        return try {
            val photos = photosApi.searchPhotos().toMutableList()

            val photosObjs = mutableListOf<Photo>()

            photos.forEach {
                val photo = Photo(null, it)
                photosObjs.add(photo)
            }

            photosDao.clearPhotos()
            photosDao.insertAll(photosObjs)

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}