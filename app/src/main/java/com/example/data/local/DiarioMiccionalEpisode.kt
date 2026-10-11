package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade Room para persistência local dos episódios do Diário Miccional.
 * Armazena volume, frequência (timestamp/horário), urgência e contexto clínico.
 */
@Entity(tableName = "diario_miccional_episodios")
data class DiarioMiccionalEpisode(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val timestamp: Long = System.currentTimeMillis(),
  val formattedTime: String = "",
  val dayOfProtocol: Int = 2, // Dia 1, 2 ou 3 do protocolo de 72h
  val volumeMl: Int = 200, // Volume aferido ou estimado em ml
  val urgencyLevel: Int = 1, // 1 = Sem urgência (Normal), 2 = Leve, 3 = Moderada, 4 = Urgência Súbita/Máxima
  val hadLeakage: Boolean = false, // Teve perda involuntária
  val leakageIntensity: String = "Nenhuma", // Nenhuma, Gotas, Moderada, Intensa
  val activityContext: String = "Repouso", // Repouso, Tosse/Espirro, Esforço Físico, A caminho do banheiro
  val episodeType: String = "MICCAO", // "MICCAO", "ESCAPE", "HIDRATACAO"
  val notes: String = "" // Observação clínica adicional
)
