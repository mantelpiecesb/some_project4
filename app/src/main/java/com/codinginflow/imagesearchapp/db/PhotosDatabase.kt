package com.deitel.redtesttask1_dollarcoursechecker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codinginflow.imagesearchapp.db.PhotosDao
import com.codinginflow.imagesearchapp.model.Photo

@Database(
    entities = [Photo::class],
    version = 1,
    exportSchema = false
)

abstract class PhotosDatabase : RoomDatabase() {

    abstract fun photosDao(): PhotosDao

    companion object {
        @Volatile
        private var sINSTANCE: PhotosDatabase? = null

        fun getInstance(context: Context): PhotosDatabase =
            sINSTANCE ?: synchronized(this) {
                sINSTANCE
                    ?: buildDatabase(context).also { sINSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PhotosDatabase::class.java, "Photos.db"
            )
                .build()
    }
}