package com.hogent.database.utilities

import android.content.Context
import com.hogent.database.ApplicationDatabase
import com.hogent.database.DatabaseDataSource
import com.hogent.domain.sources.DatabaseSource

object DatabaseInjector {

    private fun provideDatabase(context: Context) =
        ApplicationDatabase.getInstance(context.applicationContext)

    private fun provideAlbumDao(context: Context) = provideDatabase(
        context
    ).albumDao()

    private fun provideUserAlbumDao(context: Context) = provideDatabase(
        context
    ).userAlbumDao()

    fun provideDatabaseDataSource(context: Context): DatabaseSource {

        val db = provideDatabase(
            context
        )

        return DatabaseDataSource(
            db.albumDao(),
            db.userAlbumDao()
        )
    }
}