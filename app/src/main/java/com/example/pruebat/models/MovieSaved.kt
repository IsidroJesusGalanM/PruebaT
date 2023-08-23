package com.example.pruebat.models

import androidx.room.Entity
import androidx.room.PrimaryKey

//Model for movies from database
@Entity(tableName = "movieSaved")
data class MovieSaved(
    @PrimaryKey
    val id: String,
    val title: String,
    val poster: String,
    val desc:String,
    val rate:String
)