package com.codinginflow.imagesearchapp.api

import retrofit2.http.GET


interface PhotosApi {

    companion object {
        const val BASE_URL = "https://dev-tasks.alef.im/"
    }

    @GET("task-m-001/list.php")
    suspend fun searchPhotos(): List<String>
}