package com.dicoding.submission2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    companion object{
        @Volatile
        private var INSTANCE : UserDatabase? = null

        @JvmStatic
        fun getDatabase(ctx: Context): UserDatabase {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(ctx.applicationContext, UserDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as UserDatabase
        }
    }

    abstract fun favoriteUserDao(): FavoriteUserDao
}