package com.codinginflow.imagesearchapp.api

import retrofit2.http.GET

interface PhotosApi {

    companion object {
        const val BASE_URL = "http://app.passport.com.ru/"
    }

    @GET("android-dev-task.php")
    suspend fun searchPhotos(): List<String>
}