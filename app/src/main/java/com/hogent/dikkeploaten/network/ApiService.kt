package com.hogent.dikkeploaten.network

import com.hogent.dikkeploaten.database.DatabaseAlbum
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET

private const val BASE_URL = "https://dikke-ploaten-backend.herokuapp.com/api/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getAlbumList] method
 */
interface ApiService {
    /**
     * Returns a Coroutine [Deferred] [List] of [DatabaseAlbum] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "albums" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("albums")
    suspend fun getAlbumList(): List<DatabaseAlbum>

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}