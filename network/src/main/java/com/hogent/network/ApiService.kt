package com.hogent.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET



/**
 * A public interface that exposes the [getAlbumList] method
 */
internal interface ApiService {
    /**
     * Returns a Coroutine [Deferred] [List] of [DatabaseAlbum] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "albums" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("albums")
    suspend fun getAlbumList(): List<NetworkAlbum>

}