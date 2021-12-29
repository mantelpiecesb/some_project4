package com.codinginflow.imagesearchapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.SyncStateContract
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.codinginflow.imagesearchapp.MainActivity
import com.codinginflow.imagesearchapp.api.PhotosApi
import com.codinginflow.imagesearchapp.model.Photo
import com.deitel.redtesttask1_dollarcoursechecker.db.PhotosDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(private val photosApi: PhotosApi) {

    fun getSearchResults(context: Context): LiveData<PagingData<String>> {

        val connectivityManager =
            ContextCompat.getSystemService(context, ConnectivityManager::class.java)
        val activeNetwork: NetworkInfo? = connectivityManager?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (isConnected) {
            Log.d("abas", "Trying to connect...")
            return Pager(
                config = PagingConfig(
                    enablePlaceholders = false,
                    pageSize = 4
                ),
                pagingSourceFactory = { PhotosPagingSource(photosApi, context) }
            ).liveData
        } else {
            Log.d("abas", "Trying to connect to DB...")
            Toast.makeText(
                context,
                "Похоже, нет подключения к Интернету. Показаны последние загруженные данные",
                Toast.LENGTH_LONG
            ).show()
            return Pager(
                config = PagingConfig(
                    enablePlaceholders = false,
                    pageSize = 4
                ),
                pagingSourceFactory = { DbPagingSource(context) }
            ).liveData
        }
    }
}