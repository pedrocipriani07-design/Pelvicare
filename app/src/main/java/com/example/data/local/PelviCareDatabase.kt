package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Banco de dados Room para persistência local do PelviCare.
 */
@Database(
  entities = [DiarioMiccionalEpisode::class],
  version = 1,
  exportSchema = false
)
abstract class PelviCareDatabase : RoomDatabase() {

  abstract fun diarioMiccionalDao(): DiarioMiccionalDao

  companion object {
    @Volatile
    private var INSTANCE: PelviCareDatabase? = null

    fun getDatabase(context: Context): PelviCareDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          PelviCareDatabase::class.java,
          "pelvicare_room_database.db"
        )
        .fallbackToDestructiveMigration()
        .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
