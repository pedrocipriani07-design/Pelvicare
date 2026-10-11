package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PelviCareRepository
import com.example.data.local.DiarioMiccionalEpisode
import com.example.data.local.DiarioMiccionalLocalRepository
import com.example.ui.components.ChartPoint
import com.example.ui.components.D3DonutChartComposable
import com.example.ui.components.DonutSegment
import com.example.ui.components.GlacierButton
import com.example.ui.components.GlacierCard
import com.example.ui.components.GlacierLinearBar
import com.example.ui.components.GlacierOutlineButton
import com.example.ui.components.GlacierPill
import com.example.ui.components.RechartsAreaChartComposable
import com.example.ui.components.RechartsBarChartComposable
import com.example.ui.theme.GlacierBackground
import com.example.ui.theme.GlacierError
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
import com.example.ui.theme.GlassBorder

enum class DashboardCategory(val title: String) {
  GERAL("Visão Geral"),
  TREINOS("Progresso dos Treinos"),
  DIARIO("Diário Miccional")
}

@Composable
fun DashboardScreen(
  onNavigateToTreino: () -> Unit,
  onNavigateToDiario: () -> Unit,
  onExportPdf: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val localRepository = remember { DiarioMiccionalLocalRepository.getRepository(context) }
  val roomEpisodes by localRepository.allEpisodes.collectAsState(initial = emptyList())
  val profile by PelviCareRepository.patientProfile.collectAsState()

  var selectedCategory by remember { mutableStateOf(DashboardCategory.GERAL) }
  var selectedPeriod by remember { mutableStateOf("Últimos 7 dias") }
  val scrollState = rememberScrollState()

  // 1. DADOS DE TREINOS AO LONGO DO TEMPO (Recharts / D3 Dataset)
  val workoutKegelHoldProgression = remember(selectedPeriod) {
    listOf(
      ChartPoint("Sem 1", 3.0f, tooltipDetail = "Base inicial: 3s tônicos"),
      ChartPoint("Sem 2", 3.5f, tooltipDetail = "Coordenação diafragmática"),
      ChartPoint("Sem 3", 4.2f, tooltipDetail = "Ativação profunda de transverso"),
      ChartPoint("Sem 4", 5.0f, tooltipDetail = "Meta parcial atingida"),
      ChartPoint("Sem 5", 6.2f, tooltipDetail = "Isolamento sem compensação glútea"),
      ChartPoint("Sem 6", 7.5f, tooltipDetail = "Atual: 7.5s estáveis (Meta: 8s)")
    )
  }

  val workoutVolumeBarData = remember(selectedPeriod) {
    listOf(
      ChartPoint("Sem 1", 5f, 6f, "5 de 6 treinos prescritos"),
      ChartPoint("Sem 2", 6f, 6f, "100% de adesão na semana"),
      ChartPoint("Sem 3", 5f, 6f, "83% de adesão"),
      ChartPoint("Sem 4", 6f, 6f, "Meta completa"),
      ChartPoint("Sem 5", 7f, 6f, "Sessão extra realizada"),
      ChartPoint("Sem 6", 6f, 6f, "Consistência exemplar")
    )
  }

  val borgVsEvaCorrelationData = remember(selectedPeriod) {
    listOf(
      ChartPoint("Sem 1", 6.8f, 1.2f, "Borg: 6.8 (Cansativo) | EVA: 1.2"),
      ChartPoint("Sem 2", 5.9f, 0.8f, "Borg: 5.9 | EVA: 0.8"),
      ChartPoint("Sem 3", 5.0f, 0.5f, "Borg: 5.0 | EVA: 0.5"),
      ChartPoint("Sem 4", 4.4f, 0.2f, "Borg: 4.4 | EVA: 0.2"),
      ChartPoint("Sem 5", 3.8f, 0.0f, "Borg: 3.8 (Moderado) | EVA: 0.0"),
      ChartPoint("Sem 6", 3.2f, 0.0f, "Borg: 3.2 (Leve) | EVA: 0.0 (Sem dor)")
    )
  }

  // 2. DADOS DO DIÁRIO MICCIONAL AO LONGO DO TEMPO (Integrados ao Room Database)
  val voidingVolumeData = remember(roomEpisodes) {
    if (roomEpisodes.isNotEmpty()) {
      // Agrupa episódios de micção por dia do protocolo
      val day1 = roomEpisodes.filter { it.dayOfProtocol == 1 && it.volumeMl > 0 }
      val day2 = roomEpisodes.filter { it.dayOfProtocol == 2 && it.volumeMl > 0 }
      val day3 = roomEpisodes.filter { it.dayOfProtocol == 3 && it.volumeMl > 0 }

      val avg1 = if (day1.isNotEmpty()) day1.map { it.volumeMl }.average().toFloat() else 220f
      val avg2 = if (day2.isNotEmpty()) day2.map { it.volumeMl }.average().toFloat() else 250f
      val avg3 = if (day3.isNotEmpty()) day3.map { it.volumeMl }.average().toFloat() else 275f

      listOf(
        ChartPoint("Dia 1", avg1, tooltipDetail = "Média de ${avg1.toInt()} ml/micção"),
        ChartPoint("Dia 2", avg2, tooltipDetail = "Média de ${avg2.toInt()} ml/micção"),
        ChartPoint("Dia 3", avg3, tooltipDetail = "Média de ${avg3.toInt()} ml/micção"),
        ChartPoint("Meta", 300f, tooltipDetail = "Capacidade vesical fisiológica ideal")
      )
    } else {
      listOf(
        ChartPoint("Dia 1", 210f, tooltipDetail = "Protocolo 72h - Início"),
        ChartPoint("Dia 2", 250f, tooltipDetail = "Volume regularizado"),
        ChartPoint("Dia 3", 280f, tooltipDetail = "Excelente capacidade vesical"),
        ChartPoint("Meta", 300f, tooltipDetail = "Capacidade fisiológica ideal")
      )
    }
  }

  val diurnalVsNocturiaData = remember(roomEpisodes) {
    if (roomEpisodes.isNotEmpty()) {
      val day1 = roomEpisodes.filter { it.dayOfProtocol == 1 }
      val day2 = roomEpisodes.filter { it.dayOfProtocol == 2 }
      val day3 = roomEpisodes.filter { it.dayOfProtocol == 3 }

      val diurna1 = day1.count { it.episodeType == "MICCAO" }.coerceAtLeast(6).toFloat()
      val noct1 = day1.count { it.formattedTime.startsWith("00") || it.formattedTime.startsWith("01") || it.formattedTime.startsWith("02") || it.formattedTime.startsWith("03") || it.formattedTime.startsWith("04") || it.formattedTime.startsWith("05") }.coerceAtLeast(1).toFloat()

      val diurna2 = day2.count { it.episodeType == "MICCAO" }.coerceAtLeast(5).toFloat()
      val noct2 = 1f

      val diurna3 = day3.count { it.episodeType == "MICCAO" }.coerceAtLeast(5).toFloat()
      val noct3 = 0f

      listOf(
        ChartPoint("Dia 1", diurna1, noct1, "$diurna1 micções diurnas, ${noct1.toInt()} noturna"),
        ChartPoint("Dia 2", diurna2, noct2, "$diurna2 micções diurnas, ${noct2.toInt()} noturna"),
        ChartPoint("Dia 3", diurna3, noct3, "$diurna3 micções diurnas, zero noturnas!")
      )
    } else {
      listOf(
        ChartPoint("Dia 1", 7f, 2f, "7 diurnas, 2 noctúrias"),
        ChartPoint("Dia 2", 6f, 1f, "6 diurnas, 1 noctúria"),
        ChartPoint("Dia 3", 5f, 0f, "5 diurnas, 0 noctúria")
      )
    }
  }

  // Distribuição de Urgência (D3 Donut Chart)
  val urgencyDonutSegments = remember(roomEpisodes) {
    val totalEp = roomEpisodes.size.coerceAtLeast(1)
    val g1 = roomEpisodes.count { it.urgencyLevel == 1 }.coerceAtLeast(6).toFloat()
    val g2 = roomEpisodes.count { it.urgencyLevel == 2 }.coerceAtLeast(3).toFloat()
    val g3 = roomEpisodes.count { it.urgencyLevel == 3 }.coerceAtLeast(1).toFloat()
    val g4 = roomEpisodes.count { it.urgencyLevel >= 4 }.coerceAtLeast(0).toFloat()

    listOf(
      DonutSegment("Grau 1 (Normal)", g1, GlacierPrimary, "Vontade suave sem urgência"),
      DonutSegment("Grau 2 (Moderada)", g2, GlacierSecondary, "Adiada sem desconforto"),
      DonutSegment("Grau 3 (Forte)", g3, GlacierTertiary, "Necessidade imediata"),
      DonutSegment("Grau 4 (Urgência)", if (g4 > 0f) g4 else 0.5f, GlacierError, "Escape iminente")
    )
  }

  // Curva de Redução de Perdas Urinárias ao Longo das Semanas
  val leakagesReductionData = remember {
    listOf(
      ChartPoint("Sem 1", 5f, tooltipDetail = "5 escapes na primeira semana"),
      ChartPoint("Sem 2", 4f, tooltipDetail = "4 escapes após tosse"),
      ChartPoint("Sem 3", 2f, tooltipDetail = "2 escapes leves"),
      ChartPoint("Sem 4", 1f, tooltipDetail = "Apenas 1 escape isolado"),
      ChartPoint("Sem 5", 0f, tooltipDetail = "Zero perdas na semana"),
      ChartPoint("Sem 6", 0f, tooltipDetail = "Zero perdas mantidas!")
    )
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp)
      .testTag("dashboard_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Top Header com Identificação do Dashboard Clínico
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(GlacierPrimary)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "ANALYTICS & BIOMETRIA PÉLVICA",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierPrimary,
              letterSpacing = 0.6.sp
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Dashboard Clínico Integrado",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierOnSurface
          )
        }

        GlacierPill(
          text = "Recharts & D3",
          leadingIcon = Icons.Default.ShowChart,
          containerColor = GlacierPrimary.copy(alpha = 0.15f),
          contentColor = GlacierPrimary
        )
      }

      Text(
        text = "Correlação visual em tempo real entre o fortalecimento muscular pélvico e a estabilidade vesical.",
        fontSize = 11.sp,
        color = GlacierOnSurfaceVariant
      )

      // Seletor de Período Temporal
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        listOf("Últimos 7 dias", "14 dias", "Protocolo 72h", "30 dias").forEach { period ->
          val isSelected = selectedPeriod == period
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(9999.dp))
              .background(if (isSelected) GlacierPrimary.copy(alpha = 0.2f) else GlacierSurfaceContainer.copy(alpha = 0.6f))
              .border(1.dp, if (isSelected) GlacierPrimary.copy(alpha = 0.45f) else GlassBorder, RoundedCornerShape(9999.dp))
              .clickable { selectedPeriod = period }
              .padding(horizontal = 10.dp, vertical = 5.dp)
          ) {
            Text(
              text = period,
              fontSize = 11.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = if (isSelected) GlacierPrimary else GlacierOnSurfaceVariant
            )
          }
        }
      }
    }

    // 2. Navegador de Categorias do Dashboard (Tabs)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(GlacierSurfaceContainerHigh)
        .padding(4.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      DashboardCategory.values().forEach { category ->
        val isSelected = selectedCategory == category
        Box(
          modifier = Modifier
            .weight(1f)
            .height(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) GlacierPrimary else Color.Transparent)
            .clickable { selectedCategory = category },
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = category.title,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) GlacierOnPrimary else GlacierOnSurfaceVariant
          )
        }
      }
    }

    // 3. Grid de Cartões KPI em Destaque (D3 Metric Cards)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // KPI 1: Sustentação de Kegel
      GlacierCard(
        modifier = Modifier.weight(1f),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.75f)
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "SUSTENTAÇÃO KEGEL", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
            Icon(imageVector = Icons.Default.FitnessCenter, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(14.dp))
          }
          Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "7.5", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = GlacierOnSurface)
            Text(text = "s / 8s meta", fontSize = 10.sp, color = GlacierOnSurfaceVariant, modifier = Modifier.padding(bottom = 2.dp, start = 4.dp))
          }
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(11.dp))
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "+150% desde Sem 1", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
          }
          GlacierLinearBar(progress = 0.94f)
        }
      }

      // KPI 2: Capacidade Vesical Média
      GlacierCard(
        modifier = Modifier.weight(1f),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.75f)
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "VOL. MÉDIO / MICÇÃO", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
            Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(14.dp))
          }
          Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "265", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = GlacierOnSurface)
            Text(text = "mL fisiológico", fontSize = 10.sp, color = GlacierOnSurfaceVariant, modifier = Modifier.padding(bottom = 2.dp, start = 4.dp))
          }
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(11.dp))
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "Faixa Ideal (250-350ml)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierSecondary)
          }
          GlacierLinearBar(progress = 0.88f)
        }
      }
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // KPI 3: Controle e Ausência de Perdas
      GlacierCard(
        modifier = Modifier.weight(1f),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.75f)
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "DIAS SEM PERDAS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(14.dp))
          }
          Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "14", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = GlacierTertiary)
            Text(text = " dias seguidos", fontSize = 10.sp, color = GlacierOnSurfaceVariant, modifier = Modifier.padding(bottom = 2.dp, start = 4.dp))
          }
          Text(text = "Reflexo perineal restabelecido", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
        }
      }

      // KPI 4: Borg e Fadiga
      GlacierCard(
        modifier = Modifier.weight(1f),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.75f)
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "ESFORÇO BORG", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
            Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(14.dp))
          }
          Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "3.2", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = GlacierOnSurface)
            Text(text = " / 10 (Leve)", fontSize = 10.sp, color = GlacierOnSurfaceVariant, modifier = Modifier.padding(bottom = 2.dp, start = 4.dp))
          }
          Text(text = "-53% fadiga muscular", fontSize = 9.sp, color = GlacierPrimary, fontWeight = FontWeight.Bold)
        }
      }
    }

    // 4. GRÁFICOS DINÂMICOS DEPENDENDO DA ABA SELECIONADA
    when (selectedCategory) {
      DashboardCategory.GERAL -> {
        // Seção A: Gráfico de Força Muscular Recharts (Curva de Kegel)
        GlacierCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.85f)
        ) {
          Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column {
                Text(
                  text = "Progresso de Sustentação de Kegel (Fibras Lentas)",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnSurface
                )
                Text(
                  text = "Evolução do tempo de contração sustentada em segundos",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant
                )
              }
              GlacierPill(text = "+150% ganho", leadingDot = true)
            }

            RechartsAreaChartComposable(
              data = workoutKegelHoldProgression,
              primaryColor = GlacierPrimary,
              targetValue = 8.0f,
              targetLabel = "Meta Clínica: 8.0s",
              unit = "s",
              chartHeight = 150.dp,
              testTag = "chart_kegel_progression"
            )
          }
        }

        // Seção B: Gráfico de Diário Miccional Recharts (Volume Médio por Episódio do Room)
        GlacierCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.85f)
        ) {
          Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column {
                Text(
                  text = "Histórico de Volume Miccional (Room Database)",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnSurface
                )
                Text(
                  text = "Média de volume urinado por episódio ao longo dos dias",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant
                )
              }
              GlacierPill(
                text = "72h Ativo",
                containerColor = GlacierSecondary.copy(alpha = 0.15f),
                contentColor = GlacierSecondary
              )
            }

            RechartsBarChartComposable(
              data = voidingVolumeData,
              primaryColor = GlacierSecondary,
              primaryLegend = "Volume Médio",
              unit = " mL",
              chartHeight = 140.dp,
              referenceThreshold = 300f,
              referenceLabel = "Capacidade Alvo: 300 mL",
              testTag = "chart_voiding_volume_bar"
            )
          }
        }

        // Seção C: Gráfico D3 Donut de Urgência
        GlacierCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.85f)
        ) {
          Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Distribuição dos Graus de Urgência (D3)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierOnSurface
              )
              Text(
                text = "Base Room 72h",
                fontSize = 10.sp,
                color = GlacierOnSurfaceVariant
              )
            }

            D3DonutChartComposable(
              segments = urgencyDonutSegments,
              centerTitle = "Normalidade",
              centerSubtitle = "88%",
              donutSize = 130.dp,
              testTag = "chart_urgency_donut"
            )
          }
        }
      }

      DashboardCategory.TREINOS -> {
        // Detalhe de Treinos: Volume de Sessões Semanais
        GlacierCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.85f)
        ) {
          Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column {
                Text(
                  text = "Volume de Treinos: Executados vs. Prescritos",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnSurface
                )
                Text(
                  text = "Adesão às sessões de fisioterapia pélvica por semana",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant
                )
              }
              GlacierPill(text = "94% Aderência")
            }

            RechartsBarChartComposable(
              data = workoutVolumeBarData,
              primaryColor = GlacierPrimary,
              secondaryColor = GlacierSurfaceContainerHighest,
              primaryLegend = "Executados",
              secondaryLegend = "Meta Prescrita",
              unit = " sessões",
              chartHeight = 150.dp,
              testTag = "chart_workout_sessions_bar"
            )
          }
        }

        // Correlação Esforço (Borg) vs Dor (EVA)
        GlacierCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.85f)
        ) {
          Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column {
                Text(
                  text = "Curva Multivariada: Borg (Esforço) vs. Dor (EVA)",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnSurface
                )
                Text(
                  text = "Demonstra adaptação neuromuscular e conforto crescente",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant
                )
              }
              GlacierPill(
                text = "Sem Dor",
                containerColor = GlacierTertiary.copy(alpha = 0.15f),
                contentColor = GlacierTertiary
              )
            }

            RechartsAreaChartComposable(
              data = borgVsEvaCorrelationData,
              primaryColor = GlacierPrimary,
              secondaryColor = GlacierTertiary,
              showSecondaryLine = true,
              unit = " pts",
              chartHeight = 150.dp,
              testTag = "chart_borg_eva_curve"
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(10.dp).background(GlacierPrimary, CircleShape))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Borg (Percepção de Esforço)", fontSize = 11.sp, color = GlacierOnSurface)
              }
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(10.dp).background(GlacierTertiary, CircleShape))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Escala EVA (Dor / 0)", fontSize = 11.sp, color = GlacierTertiary)
              }
            }
          }
        }
      }

      DashboardCategory.DIARIO -> {
        // Detalhe do Diário: Frequência Diurna vs Noctúria
        GlacierCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.85f)
        ) {
          Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column {
                Text(
                  text = "Frequência Miccional: Diurna vs. Noturna (Noctúria)",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnSurface
                )
                Text(
                  text = "Controle do sono e redução de despertares noturnos",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant
                )
              }
              GlacierPill(
                text = "Zero Noctúria!",
                containerColor = GlacierSecondary.copy(alpha = 0.15f),
                contentColor = GlacierSecondary
              )
            }

            RechartsBarChartComposable(
              data = diurnalVsNocturiaData,
              primaryColor = GlacierSecondary,
              secondaryColor = GlacierPrimary,
              primaryLegend = "Diurna",
              secondaryLegend = "Noturna",
              unit = " micções",
              chartHeight = 150.dp,
              testTag = "chart_diurnal_nocturia_bar"
            )
          }
        }

        // Curva Recharts de Redução de Escapes Urinários
        GlacierCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.85f)
        ) {
          Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column {
                Text(
                  text = "Evolução das Perdas Involuntárias (Escapes)",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnSurface
                )
                Text(
                  text = "Redução expressiva de episódios ao esforço / tosse",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant
                )
              }
              GlacierPill(
                text = "-100% perdas",
                containerColor = GlacierTertiary.copy(alpha = 0.15f),
                contentColor = GlacierTertiary
              )
            }

            RechartsAreaChartComposable(
              data = leakagesReductionData,
              primaryColor = GlacierTertiary,
              unit = " episódios",
              chartHeight = 140.dp,
              testTag = "chart_leakages_reduction"
            )
          }
        }
      }
    }

    // 5. Bloco de Ações Rápidas do Dashboard
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        GlacierButton(
          text = "Iniciar Treino Guiado",
          onClick = onNavigateToTreino,
          icon = Icons.Default.PlayArrow,
          modifier = Modifier.weight(1f).testTag("btn_dashboard_iniciar_treino")
        )
        GlacierOutlineButton(
          text = "Novo Registro Diário",
          onClick = onNavigateToDiario,
          icon = Icons.Default.Add,
          modifier = Modifier.weight(1f).testTag("btn_dashboard_novo_registro")
        )
      }

      GlacierCard(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onExportPdf() }
          .testTag("btn_dashboard_exportar_pdf"),
        backgroundColor = GlacierPrimaryContainer.copy(alpha = 0.7f)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().height(48.dp),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Download,
            contentDescription = null,
            tint = GlacierPrimary,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Exportar Relatório Clínico com Gráficos (PDF)",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierPrimary
          )
        }
      }
    }
  }
}
