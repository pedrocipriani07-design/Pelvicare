package com.example.ui.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.data.PelviCareRepository
import com.example.data.local.DiarioMiccionalEpisode
import com.example.ui.components.GlacierButton
import com.example.ui.components.GlacierCard
import com.example.ui.components.GlacierLinearBar
import com.example.ui.components.GlacierOutlineButton
import com.example.ui.components.GlacierPill
import com.example.ui.theme.GlacierError
import com.example.ui.theme.GlacierErrorContainer
import com.example.ui.theme.GlacierOnPrimary
import com.example.ui.theme.GlacierOnSurface
import com.example.ui.theme.GlacierOnSurfaceVariant
import com.example.ui.theme.GlacierPrimary
import com.example.ui.theme.GlacierPrimaryContainer
import com.example.ui.theme.GlacierSecondary
import com.example.ui.theme.GlacierSecondaryContainer
import com.example.ui.theme.GlacierSurfaceContainer
import com.example.ui.theme.GlacierSurfaceContainerHigh
import com.example.ui.theme.GlacierSurfaceContainerHighest
import com.example.ui.theme.GlacierSurfaceContainerLow
import com.example.ui.theme.GlacierTertiary
import com.example.ui.theme.GlacierTertiaryContainer
import com.example.ui.theme.GlassBorder
import com.example.ui.viewmodel.DiarioMiccionalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiarioMiccionalScreen(
  onExportReport: () -> Unit,
  onOpenDashboard: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val application = context.applicationContext as Application
  val viewModel: DiarioMiccionalViewModel = viewModel(
    factory = object : androidx.lifecycle.ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return DiarioMiccionalViewModel(application) as T
      }
    }
  )

  val selectedDay by viewModel.selectedDay.collectAsState()
  val currentEpisodes by viewModel.currentDayEpisodes.collectAsState()
  val stats by viewModel.stats.collectAsState()

  val waterIntake by PelviCareRepository.waterIntakeMl.collectAsState()
  val targetWater by PelviCareRepository.waterTargetMl.collectAsState()

  var showNewEpisodeSheet by remember { mutableStateOf(false) }
  var syncFeedback by remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Navegador de Dias do Protocolo de 72h (Room Local)
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("diario_nav_card"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.75f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(GlacierSurfaceContainerHigh)
                .clickable {
                  if (selectedDay > 1) viewModel.selectDay(selectedDay - 1)
                },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Dia Anterior",
                tint = if (selectedDay > 1) GlacierPrimary else GlacierOnSurfaceVariant.copy(alpha = 0.4f),
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = when (selectedDay) {
                    1 -> "Dia 1 • 17 de Março"
                    2 -> "Hoje, 18 de Março"
                    else -> "Dia 3 • 19 de Março"
                  },
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnSurface
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                  modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(GlacierPrimary)
                )
              }
              Text(
                text = "Protocolo Clínico 72h • Persistência Room",
                fontSize = 10.sp,
                color = GlacierPrimary
              )
            }
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            GlacierPill(
              text = "Dia $selectedDay de 3",
              leadingIcon = Icons.Default.Timer,
              containerColor = GlacierPrimary.copy(alpha = 0.15f),
              contentColor = GlacierPrimary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(GlacierSurfaceContainerHigh)
                .clickable {
                  if (selectedDay < 3) viewModel.selectDay(selectedDay + 1)
                },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Próximo Dia",
                tint = if (selectedDay < 3) GlacierPrimary else GlacierOnSurfaceVariant.copy(alpha = 0.4f),
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }

        // Seletor de Abas de Dias
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          (1..3).forEach { day ->
            val isSelected = selectedDay == day
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) GlacierPrimary.copy(alpha = 0.2f) else GlacierSurfaceContainerHigh)
                .border(
                  width = 1.dp,
                  color = if (isSelected) GlacierPrimary else GlassBorder,
                  shape = RoundedCornerShape(8.dp)
                )
                .clickable { viewModel.selectDay(day) }
                .padding(vertical = 6.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "Dia $day",
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) GlacierPrimary else GlacierOnSurfaceVariant
              )
            }
          }
        }

        val dayProgress = when (selectedDay) {
          1 -> 0.33f
          2 -> 0.66f
          else -> 1.0f
        }
        GlacierLinearBar(progress = dayProgress)

        if (onOpenDashboard != null) {
          Spacer(modifier = Modifier.height(4.dp))
          GlacierOutlineButton(
            text = "Visualizar Histórico Completo no Dashboard (D3 / Recharts)",
            onClick = onOpenDashboard,
            icon = Icons.Default.ShowChart,
            modifier = Modifier.fillMaxWidth().testTag("btn_diario_abrir_dashboard")
          )
        }
      }
    }

    // 2. Cartão de Ação Principal: Registro de Volume, Frequência e Urgência
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("fast_logging_card"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.85f),
      borderColor = GlacierPrimary.copy(alpha = 0.3f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierPrimaryContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                tint = GlacierPrimary,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "Novo Episódio Miccional",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierOnSurface
              )
              Text(
                text = "Volume, Urgência e Intervalo",
                fontSize = 10.sp,
                color = GlacierOnSurfaceVariant
              )
            }
          }

          GlacierPill(
            text = "Banco Room",
            leadingIcon = Icons.Default.Storage,
            containerColor = GlacierSecondary.copy(alpha = 0.15f),
            contentColor = GlacierSecondary
          )
        }

        // Botão de Destaque: Abrir Formulário Detalhado
        GlacierButton(
          text = "Registrar com Volume e Urgência Detalhados",
          onClick = { showNewEpisodeSheet = true },
          icon = Icons.Default.Add,
          modifier = Modifier.fillMaxWidth().testTag("btn_abrir_form_episodio")
        )

        // Ações Rápidas de 1 Toque (Salvamento Direto no Room)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Micção 200ml Normal
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSurfaceContainerHigh)
              .clickable {
                viewModel.registerEpisode(
                  volumeMl = 200,
                  urgencyLevel = 1,
                  hadLeakage = false,
                  activityContext = "Repouso",
                  episodeType = "MICCAO",
                  notes = "Registro rápido: Micção normal sem urgência"
                )
                Toast.makeText(context, "Micção de 200 ml registrada no Room!", Toast.LENGTH_SHORT).show()
              }
              .padding(10.dp)
              .testTag("btn_quick_miccao_200ml")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(30.dp)
                  .clip(CircleShape)
                  .background(GlacierPrimary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
              }
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(text = "+200 ml", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
                Text(text = "Micção Normal", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              }
            }
          }

          // Ingestão Hídrica
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSurfaceContainerHigh)
              .clickable {
                PelviCareRepository.addWater(250)
                viewModel.registerEpisode(
                  volumeMl = 250,
                  urgencyLevel = 1,
                  hadLeakage = false,
                  activityContext = "Ingestão hídrica",
                  episodeType = "HIDRATACAO",
                  notes = "Copo de água mineral (250 ml)"
                )
                Toast.makeText(context, "Hidratação de +250 ml salva!", Toast.LENGTH_SHORT).show()
              }
              .padding(10.dp)
              .testTag("btn_quick_agua_250ml")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(30.dp)
                  .clip(CircleShape)
                  .background(GlacierSecondaryContainer),
                contentAlignment = Alignment.Center
              ) {
                Icon(imageVector = Icons.Default.LocalDrink, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(16.dp))
              }
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(text = "+250 ml Água", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierSecondary)
                Text(text = "Hidratação", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              }
            }
          }
        }
      }
    }

    // 3. Painel de Indicadores Clínicos (Bento Grid com Volume, Frequência e Urgência)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "INDICADORES CLÍNICOS • DIA $selectedDay",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierOnSurfaceVariant
        )
        Text(
          text = "${currentEpisodes.size} episódios no Room",
          fontSize = 10.sp,
          color = GlacierPrimary
        )
      }

      // Três Cartões Superiores: Frequência, Volume Eliminado e Urgência Média
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Frequência
        GlacierCard(
          modifier = Modifier.weight(1f),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "Frequência", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
              Text(
                text = "${stats.voidCount}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierPrimary
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "micções", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Int: ${stats.averageFrequencyIntervalFormatted}",
              fontSize = 9.sp,
              color = GlacierSecondary
            )
          }
        }

        // Volume Total & Médio
        GlacierCard(
          modifier = Modifier.weight(1f),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "Volume", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
              Text(
                text = "${stats.totalVoidVolumeMl}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierOnSurface
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "ml", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Média: ${stats.averageVolumeMl} ml",
              fontSize = 9.sp,
              color = GlacierSecondary
            )
          }
        }

        // Escapes / Urgência Severa
        GlacierCard(
          modifier = Modifier.weight(1f),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "Escapes", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
              Text(
                text = "${stats.leakageCount}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (stats.leakageCount == 0) GlacierPrimary else GlacierTertiary
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "perdas", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = if (stats.leakageCount == 0) "Sem perdas ✓" else "Urgência/Tosse",
              fontSize = 9.sp,
              color = if (stats.leakageCount == 0) GlacierPrimary else GlacierTertiary
            )
          }
        }
      }

      // Distribuição da Escala de Urgência (Níveis 1 a 4)
      GlacierCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.6f)
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Distribuição da Escala de Urgência",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
            Text(
              text = "Meta: Predomínio Nível 1",
              fontSize = 9.sp,
              color = GlacierPrimary
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            // Nível 1
            UrgencySummaryChip(
              level = 1,
              label = "Normal",
              count = stats.urgencyLevel1Count,
              color = GlacierPrimary,
              modifier = Modifier.weight(1f)
            )
            // Nível 2
            UrgencySummaryChip(
              level = 2,
              label = "Leve",
              count = stats.urgencyLevel2Count,
              color = GlacierSecondary,
              modifier = Modifier.weight(1f)
            )
            // Nível 3
            UrgencySummaryChip(
              level = 3,
              label = "Moderada",
              count = stats.urgencyLevel3Count,
              color = GlacierTertiary,
              modifier = Modifier.weight(1f)
            )
            // Nível 4
            UrgencySummaryChip(
              level = 4,
              label = "Máxima",
              count = stats.urgencyLevel4Count,
              color = GlacierError,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }

    // 4. Histórico Local em Tempo Real (Room Database)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.History, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(15.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "REGISTROS DO DIA (BANCO LOCAL ROOM)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierOnSurfaceVariant
          )
        }
        Text(
          text = "${currentEpisodes.size} cadastrados",
          fontSize = 10.sp,
          color = GlacierOnSurfaceVariant
        )
      }

      if (currentEpisodes.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(GlacierSurfaceContainerHigh.copy(alpha = 0.5f))
            .padding(24.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = GlacierOnSurfaceVariant, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Nenhum episódio registrado para o Dia $selectedDay", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Toque em 'Novo Episódio Miccional' acima para cadastrar.", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          }
        }
      } else {
        currentEpisodes.forEach { episode ->
          EpisodeRoomCard(
            episode = episode,
            onDelete = {
              viewModel.deleteEpisode(episode.id)
              Toast.makeText(context, "Episódio removido do banco local.", Toast.LENGTH_SHORT).show()
            }
          )
        }
      }
    }

    // 5. Card Educativo Clínico: Biofeedback da Fisioterapia
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(GlacierSurfaceContainerHigh)
        .padding(12.dp)
    ) {
      Row(verticalAlignment = Alignment.Top) {
        Box(
          modifier = Modifier
            .size(30.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierTertiaryContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(imageVector = Icons.Default.TipsAndUpdates, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(16.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "Orientação Fisioterapêutica", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            Text(text = "DRA. LUANA", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierTertiary)
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "O diário miccional de 72 horas permite quantificar a capacidade funcional vesical e identificar se os escapes ocorrem por esforço ou urgência. Cada registro auxilia na calibração dos exercícios de Kegel e do tônus perineal.",
            fontSize = 11.sp,
            color = GlacierOnSurfaceVariant,
            lineHeight = 15.sp
          )
        }
      }
    }

    // 6. Exportação & Sincronização com Relatório Clínico
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("sync_report_card"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.8f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .border(1.dp, GlacierPrimary, CircleShape)
            ) {
              AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBi_wSJ-Y8hPvLt0qzZzKlZYzWvavn8wLrGCsry6wADEahzSqmCqplGcHETdF4844rnVi9Dhn37kWrYCMnvXt1PFpZUkOiHiTjk61IUHuIZzhJyM5Y3ZxDAIJSvM7Em1hq2Cp3KTwfavsJCQ1lzjmlCLCbisin7d16490ouiAAjOH08jXm9dyI7LOdlduVkeLINIjiRHT4sl_AUfv9K-2fa76kSn4jNLWHDS_zL8gxL0v-QuMmnpW4",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(text = "Dra. Luana Silva", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "CREFITO-3/284910-F • Fisioterapeuta Pélvica", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
          }
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(GlacierPrimary)
          )
        }

        GlacierButton(
          text = if (syncFeedback) "Diário Sincronizado no Relatório!" else "Exportar para Relatório Clínico (PDF)",
          onClick = {
            syncFeedback = true
            onExportReport()
          },
          icon = Icons.Default.Sync,
          modifier = Modifier.fillMaxWidth().testTag("btn_exportar_relatorio")
        )
      }
    }
  }

  // BottomSheet Modal: Formulário Completo com Volume, Frequência e Urgência
  if (showNewEpisodeSheet) {
    ModalBottomSheet(
      onDismissRequest = { showNewEpisodeSheet = false },
      sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
      containerColor = GlacierSurfaceContainerLow,
      tonalElevation = 16.dp
    ) {
      NewEpisodeFormSheetContent(
        selectedDay = selectedDay,
        onDismiss = { showNewEpisodeSheet = false },
        onSave = { volume, urgency, hadLeak, intensity, contextStr, type, notes ->
          viewModel.registerEpisode(
            volumeMl = volume,
            urgencyLevel = urgency,
            hadLeakage = hadLeak,
            leakageIntensity = intensity,
            activityContext = contextStr,
            episodeType = type,
            notes = notes
          )
          showNewEpisodeSheet = false
          Toast.makeText(context, "Episódio salvo com sucesso no banco de dados local (Room)!", Toast.LENGTH_SHORT).show()
        }
      )
    }
  }
}

/**
 * Conteúdo do BottomSheet para entrada dos campos de Volume, Frequência e Urgência
 */
@Composable
fun NewEpisodeFormSheetContent(
  selectedDay: Int,
  onDismiss: () -> Unit,
  onSave: (volumeMl: Int, urgencyLevel: Int, hadLeakage: Boolean, leakageIntensity: String, activityContext: String, episodeType: String, notes: String) -> Unit
) {
  var volumeInput by remember { mutableStateOf("200") }
  var urgencyLevel by remember { mutableIntStateOf(1) }
  var episodeType by remember { mutableStateOf("MICCAO") }
  var hadLeakage by remember { mutableStateOf(false) }
  var selectedLeakIntensity by remember { mutableStateOf("Gotas") }
  var selectedActivity by remember { mutableStateOf("Repouso") }
  var notesInput by remember { mutableStateOf("") }

  val predefinedVolumes = listOf(50, 100, 150, 200, 250, 300, 350, 400)
  val activities = listOf("Repouso", "Tosse / Espirro", "Riso", "Esforço Físico", "A caminho do banheiro")

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 12.dp)
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Registrar no Diário Miccional",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierOnSurface
        )
        Text(
          text = "Dia $selectedDay • Persistência Local Room",
          fontSize = 11.sp,
          color = GlacierPrimary
        )
      }
      IconButton(onClick = onDismiss) {
        Icon(imageVector = Icons.Default.Close, contentDescription = "Fechar", tint = GlacierOnSurfaceVariant)
      }
    }

    // 1. Tipo de Registro
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Text(
        text = "Tipo de Registro",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = GlacierOnSurfaceVariant
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf(
          "MICCAO" to "Micção Normal",
          "ESCAPE" to "Perda / Escape",
          "HIDRATACAO" to "Hidratação"
        ).forEach { (type, label) ->
          val isSelected = episodeType == type
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(if (isSelected) GlacierPrimary.copy(alpha = 0.2f) else GlacierSurfaceContainerHigh)
              .border(
                width = 1.dp,
                color = if (isSelected) GlacierPrimary else GlassBorder,
                shape = RoundedCornerShape(10.dp)
              )
              .clickable {
                episodeType = type
                if (type == "ESCAPE") hadLeakage = true
              }
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = label,
              fontSize = 10.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              color = if (isSelected) GlacierPrimary else GlacierOnSurface
            )
          }
        }
      }
    }

    // 2. Campo de Volume (ml)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Volume Estimado ou Medido (ml)",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierOnSurface
        )
        Text(
          text = "${volumeInput.toIntOrNull() ?: 0} ml selecionados",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierPrimary
        )
      }

      // Chips Rápidos de Volume
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        predefinedVolumes.take(4).forEach { vol ->
          val isSel = volumeInput == vol.toString()
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSel) GlacierPrimary else GlacierSurfaceContainerHigh)
              .clickable { volumeInput = vol.toString() }
              .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "$vol ml",
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (isSel) GlacierOnPrimary else GlacierOnSurface
            )
          }
        }
      }
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        predefinedVolumes.drop(4).forEach { vol ->
          val isSel = volumeInput == vol.toString()
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSel) GlacierPrimary else GlacierSurfaceContainerHigh)
              .clickable { volumeInput = vol.toString() }
              .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "$vol ml",
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (isSel) GlacierOnPrimary else GlacierOnSurface
            )
          }
        }
      }

      // Campo de Texto para digitação livre de Volume
      OutlinedTextField(
        value = volumeInput,
        onValueChange = { volumeInput = it.filter { ch -> ch.isDigit() } },
        label = { Text("Volume personalizado (ml)") },
        modifier = Modifier.fillMaxWidth().testTag("input_volume_ml"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = GlacierPrimary,
          unfocusedBorderColor = GlassBorder,
          focusedContainerColor = GlacierSurfaceContainerHigh,
          unfocusedContainerColor = GlacierSurfaceContainerHigh,
          focusedTextColor = GlacierOnSurface,
          unfocusedTextColor = GlacierOnSurface
        ),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
    }

    // 3. Campo de Urgência (Escala Clínica de 1 a 4)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Grau de Urgência Miccional",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierOnSurface
        )
        Text(
          text = "Nível $urgencyLevel de 4",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = when (urgencyLevel) {
            1 -> GlacierPrimary
            2 -> GlacierSecondary
            3 -> GlacierTertiary
            else -> GlacierError
          }
        )
      }

      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        listOf(
          1 to ("Nível 1 • Normal / Sem Urgência" to "Bexiga confortavelmente cheia, sem pressa"),
          2 to ("Nível 2 • Urgência Leve" to "Vontade clara, adiável sem grande esforço"),
          3 to ("Nível 3 • Urgência Moderada" to "Vontade súbita e intensa, desconforto pélvico"),
          4 to ("Nível 4 • Urgência Severa / Máxima" to "Imperiosa, difícil contenção, risco de perda")
        ).forEach { (lvl, pair) ->
          val (title, desc) = pair
          val isSelected = urgencyLevel == lvl
          val activeColor = when (lvl) {
            1 -> GlacierPrimary
            2 -> GlacierSecondary
            3 -> GlacierTertiary
            else -> GlacierError
          }

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(if (isSelected) activeColor.copy(alpha = 0.15f) else GlacierSurfaceContainerHigh)
              .border(
                width = if (isSelected) 1.5.dp else 0.5.dp,
                color = if (isSelected) activeColor else GlassBorder,
                shape = RoundedCornerShape(10.dp)
              )
              .clickable { urgencyLevel = lvl }
              .padding(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = title,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) activeColor else GlacierOnSurface
                )
                Text(
                  text = desc,
                  fontSize = 9.sp,
                  color = GlacierOnSurfaceVariant
                )
              }
              if (isSelected) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = activeColor,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }
        }
      }
    }

    // 4. Perda Involuntária (Escape)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Houve Perda ou Escape de Urina?",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierOnSurface
          )
          Text(
            text = "Incontinência de esforço ou urgência",
            fontSize = 9.sp,
            color = GlacierOnSurfaceVariant
          )
        }
        Switch(
          checked = hadLeakage,
          onCheckedChange = { hadLeakage = it },
          colors = SwitchDefaults.colors(
            checkedThumbColor = GlacierOnPrimary,
            checkedTrackColor = GlacierPrimary,
            uncheckedThumbColor = GlacierOnSurfaceVariant,
            uncheckedTrackColor = GlacierSurfaceContainerHigh
          )
        )
      }

      if (hadLeakage) {
        Text(text = "Intensidade da Perda", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          listOf("Gotas", "Moderada", "Intensa").forEach { intensity ->
            val isSel = selectedLeakIntensity == intensity
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSel) GlacierTertiary.copy(alpha = 0.2f) else GlacierSurfaceContainerHigh)
                .border(
                  width = 1.dp,
                  color = if (isSel) GlacierTertiary else GlassBorder,
                  shape = RoundedCornerShape(8.dp)
                )
                .clickable { selectedLeakIntensity = intensity }
                .padding(vertical = 6.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = intensity,
                fontSize = 10.sp,
                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                color = if (isSel) GlacierTertiary else GlacierOnSurface
              )
            }
          }
        }
      }
    }

    // 5. Atividade / Contexto no Momento
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Text(
        text = "Atividade no Momento do Episódio",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = GlacierOnSurfaceVariant
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        activities.take(3).forEach { act ->
          val isSel = selectedActivity == act
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSel) GlacierPrimary.copy(alpha = 0.2f) else GlacierSurfaceContainerHigh)
              .border(
                width = 1.dp,
                color = if (isSel) GlacierPrimary else GlassBorder,
                shape = RoundedCornerShape(8.dp)
              )
              .clickable { selectedActivity = act }
              .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = act,
              fontSize = 9.sp,
              fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
              color = if (isSel) GlacierPrimary else GlacierOnSurface
            )
          }
        }
      }
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        activities.drop(3).forEach { act ->
          val isSel = selectedActivity == act
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSel) GlacierPrimary.copy(alpha = 0.2f) else GlacierSurfaceContainerHigh)
              .border(
                width = 1.dp,
                color = if (isSel) GlacierPrimary else GlassBorder,
                shape = RoundedCornerShape(8.dp)
              )
              .clickable { selectedActivity = act }
              .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = act,
              fontSize = 9.sp,
              fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
              color = if (isSel) GlacierPrimary else GlacierOnSurface
            )
          }
        }
      }
    }

    // 6. Observações Adicionais
    OutlinedTextField(
      value = notesInput,
      onValueChange = { notesInput = it },
      label = { Text("Observação ou sintoma (opcional)") },
      placeholder = { Text("Ex: Jato forte, sem dor, sensação de esvaziamento total") },
      modifier = Modifier.fillMaxWidth().height(80.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = GlacierPrimary,
        unfocusedBorderColor = GlassBorder,
        focusedContainerColor = GlacierSurfaceContainerHigh,
        unfocusedContainerColor = GlacierSurfaceContainerHigh,
        focusedTextColor = GlacierOnSurface,
        unfocusedTextColor = GlacierOnSurface
      ),
      shape = RoundedCornerShape(10.dp)
    )

    // Botões de Ação
    Row(
      modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      GlacierOutlineButton(
        text = "Cancelar",
        onClick = onDismiss,
        modifier = Modifier.weight(1f)
      )

      GlacierButton(
        text = "Salvar no Room",
        onClick = {
          val vol = volumeInput.toIntOrNull() ?: 200
          onSave(
            vol,
            urgencyLevel,
            hadLeakage,
            selectedLeakIntensity,
            selectedActivity,
            episodeType,
            notesInput
          )
        },
        icon = Icons.Default.Check,
        modifier = Modifier.weight(1.5f).testTag("btn_confirmar_salvar_room")
      )
    }
  }
}

/**
 * Chip para resumo de cada nível de urgência
 */
@Composable
fun UrgencySummaryChip(
  level: Int,
  label: String,
  count: Int,
  color: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(GlacierSurfaceContainerHigh)
      .padding(horizontal = 6.dp, vertical = 6.dp)
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(text = "Nv. $level", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = color)
      Text(text = "$count", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
      Text(text = label, fontSize = 8.sp, color = GlacierOnSurfaceVariant)
    }
  }
}

/**
 * Cartão de exibição do episódio persistido no Room
 */
@Composable
fun EpisodeRoomCard(
  episode: DiarioMiccionalEpisode,
  onDelete: () -> Unit
) {
  val urgencyColor = when (episode.urgencyLevel) {
    1 -> GlacierPrimary
    2 -> GlacierSecondary
    3 -> GlacierTertiary
    else -> GlacierError
  }

  GlacierCard(
    modifier = Modifier.fillMaxWidth(),
    backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.65f),
    borderColor = if (episode.hadLeakage) GlacierTertiary.copy(alpha = 0.3f) else GlassBorder
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.Top
    ) {
      // Indicador Lateral com Horário e Tipo
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(48.dp)
      ) {
        Text(
          text = episode.formattedTime.ifEmpty { "12:00" },
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = urgencyColor
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
          modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(urgencyColor)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = when (episode.episodeType) {
                "MICCAO" -> "Micção Voluntária"
                "ESCAPE" -> "Perda / Escape"
                "HIDRATACAO" -> "Hidratação Ingerida"
                else -> episode.episodeType
              },
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
            Spacer(modifier = Modifier.width(6.dp))
            // Badge Urgência
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(urgencyColor.copy(alpha = 0.15f))
                .padding(horizontal = 5.dp, vertical = 2.dp)
            ) {
              Text(
                text = "Urgência ${episode.urgencyLevel}",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = urgencyColor
              )
            }
          }

          // Volume em Destaque
          Text(
            text = "${episode.volumeMl} ml",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierPrimary
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Contexto e Escape
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Contexto: ${episode.activityContext}",
            fontSize = 10.sp,
            color = GlacierOnSurfaceVariant
          )

          if (episode.hadLeakage) {
            Text(text = "•", fontSize = 10.sp, color = GlacierTertiary)
            Text(
              text = "Escape (${episode.leakageIntensity})",
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = GlacierTertiary
            )
          }
        }

        if (episode.notes.isNotEmpty()) {
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = "“${episode.notes}”",
            fontSize = 10.sp,
            color = GlacierOnSurfaceVariant.copy(alpha = 0.9f)
          )
        }
      }

      // Botão de Exclusão
      IconButton(
        onClick = onDelete,
        modifier = Modifier.size(28.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Delete,
          contentDescription = "Excluir Episódio",
          tint = GlacierOnSurfaceVariant.copy(alpha = 0.5f),
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}
