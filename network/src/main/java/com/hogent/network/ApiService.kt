package com.hogent.network

import com.hogent.network.models.NetworkAlbum
import retrofit2.http.GET

/**
 * A public interface that exposes the [getAlbumList] method
 */
internal interface ApiService {
    /**
     * Returns a Coroutine suspend [List] of [NetworkAlbum].The @GET annotation indicates
     * that the "albums" endpoint will be requested with the GET HTTP method
     */
    @GET("albums")
    suspend fun getAlbumList(): List<NetworkAlbum>
}
