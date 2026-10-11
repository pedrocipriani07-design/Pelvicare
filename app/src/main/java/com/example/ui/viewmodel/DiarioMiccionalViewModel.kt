package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.DiarioMiccionalEpisode
import com.example.data.local.DiarioMiccionalLocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DiarioMiccionalStats(
  val totalVoidVolumeMl: Int = 0,
  val voidCount: Int = 0,
  val averageVolumeMl: Int = 0,
  val averageFrequencyIntervalFormatted: String = "—",
  val leakageCount: Int = 0,
  val urgencyLevel1Count: Int = 0,
  val urgencyLevel2Count: Int = 0,
  val urgencyLevel3Count: Int = 0,
  val urgencyLevel4Count: Int = 0
)

class DiarioMiccionalViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = DiarioMiccionalLocalRepository.getRepository(application)

  private val _selectedDay = MutableStateFlow(2)
  val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

  val allEpisodes: StateFlow<List<DiarioMiccionalEpisode>> = repository.allEpisodes
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList()
    )

  val currentDayEpisodes: StateFlow<List<DiarioMiccionalEpisode>> = combine(allEpisodes, _selectedDay) { episodes, day ->
    episodes.filter { it.dayOfProtocol == day }
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = emptyList()
  )

  val stats: StateFlow<DiarioMiccionalStats> = currentDayEpisodes.combine(_selectedDay) { episodes, _ ->
    calculateStats(episodes)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = DiarioMiccionalStats()
  )

  fun selectDay(day: Int) {
    if (day in 1..3) {
      _selectedDay.value = day
    }
  }

  fun registerEpisode(
    volumeMl: Int,
    urgencyLevel: Int,
    hadLeakage: Boolean,
    leakageIntensity: String = if (hadLeakage) "Gotas" else "Nenhuma",
    activityContext: String = "Repouso",
    episodeType: String = "MICCAO",
    notes: String = ""
  ) {
    viewModelScope.launch {
      repository.insertEpisode(
        volumeMl = volumeMl,
        urgencyLevel = urgencyLevel,
        dayOfProtocol = _selectedDay.value,
        hadLeakage = hadLeakage,
        leakageIntensity = leakageIntensity,
        activityContext = activityContext,
        episodeType = episodeType,
        notes = notes
      )
    }
  }

  fun deleteEpisode(id: Long) {
    viewModelScope.launch {
      repository.deleteEpisode(id)
    }
  }

  private fun calculateStats(episodes: List<DiarioMiccionalEpisode>): DiarioMiccionalStats {
    val voids = episodes.filter { it.episodeType == "MICCAO" }
    val totalVolume = voids.sumOf { it.volumeMl }
    val voidCount = voids.size
    val averageVolume = if (voidCount > 0) totalVolume / voidCount else 0

    val leakCount = episodes.count { it.hadLeakage || it.episodeType == "ESCAPE" }

    val u1 = episodes.count { it.urgencyLevel == 1 }
    val u2 = episodes.count { it.urgencyLevel == 2 }
    val u3 = episodes.count { it.urgencyLevel == 3 }
    val u4 = episodes.count { it.urgencyLevel == 4 }

    // Frequência: cálculo do intervalo médio entre episódios de micção
    val sortedTimestamps = voids.map { it.timestamp }.sorted()
    val frequencyFormatted = if (sortedTimestamps.size >= 2) {
      val intervals = mutableListOf<Long>()
      for (i in 1 until sortedTimestamps.size) {
        intervals.add(sortedTimestamps[i] - sortedTimestamps[i - 1])
      }
      val avgIntervalMs = intervals.average().toLong()
      val minutes = (avgIntervalMs / (1000 * 60)).toInt()
      val hours = minutes / 60
      val remMinutes = minutes % 60
      if (hours > 0) "${hours}h ${remMinutes}m" else "${minutes}m"
    } else if (voidCount == 1) {
      "1 registro"
    } else {
      "—"
    }

    return DiarioMiccionalStats(
      totalVoidVolumeMl = totalVolume,
      voidCount = voidCount,
      averageVolumeMl = averageVolume,
      averageFrequencyIntervalFormatted = frequencyFormatted,
      leakageCount = leakCount,
      urgencyLevel1Count = u1,
      urgencyLevel2Count = u2,
      urgencyLevel3Count = u3,
      urgencyLevel4Count = u4
    )
  }
}
