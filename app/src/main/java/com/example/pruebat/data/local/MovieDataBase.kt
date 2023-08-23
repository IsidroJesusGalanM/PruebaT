package com.example.pruebat.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pruebat.models.MovieModel
import com.example.pruebat.models.MovieSaved

//class for init the database with ROOM
@Database(entities = [MovieSaved::class,MovieModel::class], version = 2, exportSchema = false)
abstract class MovieDataBase: RoomDatabase(){

    abstract fun dao(): MovieDao
    abstract fun daoMovieApi(): MovieApiDao

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
                ).fallbackToDestructiveMigration().build()

                INSTANCE =instance
                return instance
            }
        }
    }
}