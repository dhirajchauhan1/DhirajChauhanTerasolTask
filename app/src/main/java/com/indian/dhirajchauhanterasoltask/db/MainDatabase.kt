package com.indian.dhirajchauhanterasoltask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.indian.dhirajchauhanterasoltask.model.MoviesItem

@Database(
    entities = [MoviesItem::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

    companion object{
        @Volatile
        private var instance: MainDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MainDatabase::class.java,
                "main_db.db"
            ).build()
    }
}