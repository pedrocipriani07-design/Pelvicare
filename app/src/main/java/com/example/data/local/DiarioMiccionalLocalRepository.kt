package com.example.data.local

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Repositório para abstrair o acesso ao DAO do Diário Miccional.
 * Segue o padrão Repository Pattern exigido pela diretriz do Room.
 */
class DiarioMiccionalLocalRepository(
  private val dao: DiarioMiccionalDao,
  private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

  val allEpisodes: Flow<List<DiarioMiccionalEpisode>> = dao.getAllEpisodes()

  init {
    // Inicialização preventiva com dados prévios caso a tabela esteja vazia
    coroutineScope.launch {
      if (dao.getCount() == 0) {
        seedInitialEpisodes()
      }
    }
  }

  fun getEpisodesForDay(day: Int): Flow<List<DiarioMiccionalEpisode>> {
    return dao.getEpisodesByDay(day)
  }

  suspend fun insertEpisode(
    volumeMl: Int,
    urgencyLevel: Int,
    dayOfProtocol: Int,
    hadLeakage: Boolean,
    leakageIntensity: String = if (hadLeakage) "Gotas" else "Nenhuma",
    activityContext: String = "Repouso",
    episodeType: String = "MICCAO",
    notes: String = "",
    timestamp: Long = System.currentTimeMillis()
  ): Long {
    val timeFormat = SimpleDateFormat("HH:mm", Locale("pt", "BR"))
    val formattedTime = timeFormat.format(Date(timestamp))

    val episode = DiarioMiccionalEpisode(
      timestamp = timestamp,
      formattedTime = formattedTime,
      dayOfProtocol = dayOfProtocol,
      volumeMl = volumeMl,
      urgencyLevel = urgencyLevel,
      hadLeakage = hadLeakage,
      leakageIntensity = leakageIntensity,
      activityContext = activityContext,
      episodeType = episodeType,
      notes = notes
    )
    return dao.insertEpisode(episode)
  }

  suspend fun deleteEpisode(id: Long) {
    dao.deleteEpisodeById(id)
  }

  private suspend fun seedInitialEpisodes() {
    val now = System.currentTimeMillis()
    val hour = 3600 * 1000L
    val initialData = listOf(
      DiarioMiccionalEpisode(
        timestamp = now - (hour * 1),
        formattedTime = "14:15",
        dayOfProtocol = 2,
        volumeMl = 250,
        urgencyLevel = 1,
        hadLeakage = false,
        leakageIntensity = "Nenhuma",
        activityContext = "Repouso",
        episodeType = "MICCAO",
        notes = "Micção voluntária com fluxo livre e sem desconforto"
      ),
      DiarioMiccionalEpisode(
        timestamp = now - (hour * 3),
        formattedTime = "11:20",
        dayOfProtocol = 2,
        volumeMl = 180,
        urgencyLevel = 3,
        hadLeakage = true,
        leakageIntensity = "Gotas",
        activityContext = "Tosse / Espirro",
        episodeType = "ESCAPE",
        notes = "Escape leve ao tossir antes de alcançar o sanitário"
      ),
      DiarioMiccionalEpisode(
        timestamp = now - (hour * 5),
        formattedTime = "09:10",
        dayOfProtocol = 2,
        volumeMl = 320,
        urgencyLevel = 2,
        hadLeakage = false,
        leakageIntensity = "Nenhuma",
        activityContext = "Ao acordar",
        episodeType = "MICCAO",
        notes = "Primeira micção da manhã, coloração clara"
      ),
      DiarioMiccionalEpisode(
        timestamp = now - (hour * 24 + hour * 3),
        formattedTime = "16:45",
        dayOfProtocol = 1,
        volumeMl = 220,
        urgencyLevel = 1,
        hadLeakage = false,
        leakageIntensity = "Nenhuma",
        activityContext = "Caminhada",
        episodeType = "MICCAO",
        notes = "Sensação de esvaziamento completo da bexiga"
      )
    )
    dao.insertAll(initialData)
  }

  companion object {
    @Volatile
    private var INSTANCE: DiarioMiccionalLocalRepository? = null

    fun getRepository(context: Context): DiarioMiccionalLocalRepository {
      return INSTANCE ?: synchronized(this) {
        val database = PelviCareDatabase.getDatabase(context)
        val instance = DiarioMiccionalLocalRepository(database.diarioMiccionalDao())
        INSTANCE = instance
        instance
      }
    }
  }
}
