package com.codinginflow.imagesearchapp.data

import android.content.Context
import androidx.paging.PagingSource
import com.deitel.redtesttask1_dollarcoursechecker.db.PhotosDatabase
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class DbPagingSource(
    private val context: Context
) : PagingSource<Int, String>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        val position = params.key ?: STARTING_PAGE_INDEX
        val photosDao = PhotosDatabase.getInstance(context).photosDao()

        return try {

            val photos1 = photosDao.getAllPhotos().toMutableList()

            val photos = mutableListOf<String>()

            photos1.forEach {
                photos.add(it.photo_url)
            }

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}