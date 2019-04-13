package com.nitin.codetime.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nitin.codetime.data.db.entity.ContestEntry

@Database(
    entities = [ContestEntry::class],
    version = 1,
    exportSchema = false
)
abstract class ContestDatabase : RoomDatabase() {
    abstract fun contestDao(): ContestDao

    companion object {
        @Volatile
        private var instance: ContestDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ContestDatabase::class.java,
                "contest.db"
            ).build()
    }
}