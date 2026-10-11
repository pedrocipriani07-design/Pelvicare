package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para o Diário Miccional.
 * Retorna Flow<List<DiarioMiccionalEpisode>> para atualização reativa em tempo real na interface Compose.
 */
@Dao
interface DiarioMiccionalDao {

  @Query("SELECT * FROM diario_miccional_episodios ORDER BY timestamp DESC")
  fun getAllEpisodes(): Flow<List<DiarioMiccionalEpisode>>

  @Query("SELECT * FROM diario_miccional_episodios WHERE dayOfProtocol = :day ORDER BY timestamp DESC")
  fun getEpisodesByDay(day: Int): Flow<List<DiarioMiccionalEpisode>>

  @Query("SELECT * FROM diario_miccional_episodios WHERE episodeType = 'MICCAO' ORDER BY timestamp DESC")
  fun getVoidingEpisodes(): Flow<List<DiarioMiccionalEpisode>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertEpisode(episode: DiarioMiccionalEpisode): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(episodes: List<DiarioMiccionalEpisode>)

  @Query("DELETE FROM diario_miccional_episodios WHERE id = :id")
  suspend fun deleteEpisodeById(id: Long)

  @Query("SELECT COUNT(*) FROM diario_miccional_episodios")
  suspend fun getCount(): Int

  @Query("DELETE FROM diario_miccional_episodios")
  suspend fun clearAll()
}
