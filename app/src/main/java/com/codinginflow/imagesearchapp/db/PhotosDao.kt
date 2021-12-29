package com.codinginflow.imagesearchapp.db

import android.provider.Contacts
import androidx.paging.PagingSource
import androidx.room.*
import com.codinginflow.imagesearchapp.model.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<Photo>)

    @Query("SELECT * FROM photos")
    suspend fun getAllPhotos(): List<Photo>

    @Query("DELETE FROM photos")
    suspend fun clearPhotos()
}