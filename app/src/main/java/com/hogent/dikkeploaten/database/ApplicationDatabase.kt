package com.hogent.dikkeploaten.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DatabaseUser::class, DatabaseAlbum::class, UserAlbum::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun userAlbumDao(): UserAlbumDao
    abstract fun albumDao(): AlbumDao

   companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ApplicationDatabase::class.java,
                        "application_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}