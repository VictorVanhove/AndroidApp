package com.hogent.database

import android.content.Context
import com.hogent.database.dao.AlbumDao

object DatabaseInjector {

    private fun provideDatabase(context: Context) = ApplicationDatabase.getInstance(context.applicationContext)

    private fun provideAlbumDao(context: Context) = provideDatabase(context).albumDao()

    private fun provideUserAlbumDao(context: Context) = provideDatabase(context).userAlbumDao()

    fun provideDatabaseDataSource(context: Context): DatabaseDataSource {

        val db = provideDatabase(context)

        return DatabaseDataSource(db.albumDao(), db.userAlbumDao())
    }
}