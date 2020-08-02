package com.hogent.dikkeploaten.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.hogent.database.DatabaseInjector
import com.hogent.database.models.DatabaseAlbum
import com.hogent.database.models.AlbumAndUserAlbums
import com.hogent.dikkeploaten.repositories.AlbumRepository
import com.hogent.dikkeploaten.repositories.UserAlbumRepository
import com.hogent.dikkeploaten.viewmodels.*
import com.hogent.network.NetworkApiInjector

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getAlbumRepository(context: Context): AlbumRepository {
        return AlbumRepository.getInstance(DatabaseInjector.provideDatabaseDataSource(context), NetworkApiInjector.provideNetworDatakSource())
    }

    private fun getUserAlbumRepository(context: Context): UserAlbumRepository {
        return UserAlbumRepository.getInstance(DatabaseInjector.provideDatabaseDataSource(context))
    }

    fun provideUserAlbumViewModelFactory(
        context: Context
    ): UserAlbumListViewModelFactory {
        return UserAlbumListViewModelFactory(getUserAlbumRepository(context))
    }

    fun provideAlbumListViewModelFactory(fragment: Fragment): SearchViewModelFactory {
        return SearchViewModelFactory(getAlbumRepository(fragment.requireContext()), fragment)
    }

    fun provideAlbumDetailViewModelFactory(
        context: Context,
        album: DatabaseAlbum
    ): AlbumDetailViewModelFactory {
        return AlbumDetailViewModelFactory(getUserAlbumRepository(context), album)
    }

    fun provideUserAlbumDetailViewModelFactory(
        context: Context,
        album: AlbumAndUserAlbums
    ): UserAlbumDetailViewModelFactory {
        return UserAlbumDetailViewModelFactory(getUserAlbumRepository(context), album)
    }
}
