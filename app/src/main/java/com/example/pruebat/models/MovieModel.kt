package com.example.pruebat.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//Model for Movies from API request and parce the data
@Entity(tableName = "moviesApi")
data class MovieModel(
    @SerializedName("id")
    @PrimaryKey var id: String,
    @SerializedName("original_title")
    var title: String,
    @SerializedName("poster_path")
    var poster:String,
    @SerializedName("overview")
    var desc:String,
    @SerializedName("vote_average")
    var vote:String,
)
