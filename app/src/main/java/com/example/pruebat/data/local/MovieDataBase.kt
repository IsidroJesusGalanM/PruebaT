package com.example.pruebat.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pruebat.models.MovieSaved

@Database(entities = [MovieSaved::class], version = 1, exportSchema = false)
abstract class MovieDataBase: RoomDatabase(){

    abstract fun dao(): MovieDao

    companion object{
        @Volatile
        private var INSTANCE : com.example.pruebat.data.local.MovieDataBase? = null

        fun getDatabase(context: Context): MovieDataBase{
            val instance = INSTANCE
            if (instance != null){
                return instance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    MovieDataBase::class.java,"movies"
                ).build()

                INSTANCE =instance
                return instance
            }
        }
    }
}