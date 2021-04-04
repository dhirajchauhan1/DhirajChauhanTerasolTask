package com.indian.dhirajchauhanterasoltask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    tableName = "saved_movies"
)
data class MoviesItem(
    val info: Info,
    val title: String,
    val year: Int
): Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
