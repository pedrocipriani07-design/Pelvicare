package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.PelviCareRepository
import com.example.ui.components.GlacierButton
import com.example.ui.components.GlacierCard
import com.example.ui.components.GlacierLinearBar
import com.example.ui.components.GlacierOutlineButton
import com.example.ui.components.GlacierPill
import com.example.ui.theme.GlacierBackground
import com.example.ui.theme.GlacierOnPrimary
import com.example.ui.theme.GlacierOnSurface
import com.example.ui.theme.GlacierOnSurfaceVariant
import com.example.ui.theme.GlacierPrimary
import com.example.ui.theme.GlacierPrimaryContainer
import com.example.ui.theme.GlacierSecondary
import com.example.ui.theme.GlacierSurfaceContainer
import com.example.ui.theme.GlacierSurfaceContainerHigh
import com.example.ui.theme.GlacierSurfaceContainerHighest
import com.example.ui.theme.GlacierSurfaceContainerLow
import com.example.ui.theme.GlacierTertiary
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassSurface

@Composable
fun EvolucaoScreen(
  onExportPdf: () -> Unit,
  onNavigateTo34Weeks: () -> Unit,
  onNavigateToDashboard: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val feedbacks by PelviCareRepository.feedbacks.collectAsState()
  var selectedPeriod by remember { mutableStateOf("Últimos 7 dias") }
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header Title & Filters
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(GlacierPrimary)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "PRONTUÁRIO • CAMILA MENDONÇA",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierPrimary,
              letterSpacing = 0.5.sp
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Evolução & Histórico",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierOnSurface
          )
        }

        GlacierPill(
          text = "Supervisionado",
          leadingIcon = Icons.Default.Verified,
          containerColor = GlacierSurfaceContainerHigh,
          contentColor = GlacierPrimary
        )
      }

      Text(
        text = "Acompanhamento dos treinos, percepção de esforço e respostas clínicas em tempo real.",
        fontSize = 11.sp,
        color = GlacierOnSurfaceVariant
      )

      // Filter Pills
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        listOf("Últimos 7 dias", "30 dias", "3 meses", "Ver Tudo").forEach { period ->
          val isSelected = selectedPeriod == period
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(9999.dp))
              .background(if (isSelected) GlacierPrimary.copy(alpha = 0.2f) else GlacierSurfaceContainer.copy(alpha = 0.6f))
              .border(1.dp, if (isSelected) GlacierPrimary.copy(alpha = 0.4f) else GlassBorder, RoundedCornerShape(9999.dp))
              .clickable { selectedPeriod = period }
              .padding(horizontal = 12.dp, vertical = 6.dp)
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

      // Quick access to Recharts / D3 Dashboard
      GlacierCard(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onNavigateToDashboard() }
          .testTag("card_open_recharts_dashboard"),
        backgroundColor = GlacierPrimaryContainer.copy(alpha = 0.5f),
        borderColor = GlacierPrimary.copy(alpha = 0.4f)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierPrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.BarChart, contentDescription = null, tint = GlacierOnPrimary, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(text = "Dashboard Analítico Interativo", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "Gráficos Recharts & D3 de Treinos e Diário", fontSize = 10.sp, color = GlacierPrimary)
            }
          }
          GlacierPill(text = "Abrir", leadingIcon = Icons.Default.ShowChart)
        }
      }
    }

    // 2. Key Metrics Bento Grid
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Treinos Feitos
      GlacierCard(
        modifier = Modifier.weight(1f),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "TREINOS FEITOS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(GlacierPrimary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.TaskAlt, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(14.dp))
            }
          }
          Column {
            Row(verticalAlignment = Alignment.Bottom) {
              Text(text = "28", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = " / 30", fontSize = 11.sp, color = GlacierOnSurfaceVariant)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(12.dp))
              Spacer(modifier = Modifier.width(2.dp))
              Text(text = "+12%", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierPrimary)
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "vs mês ant.", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
            }
          }
          GlacierLinearBar(progress = 0.93f)
        }
      }

      // Borg Médio
      GlacierCard(
        modifier = Modifier.weight(1f),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "BORG MÉDIO", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(GlacierSecondary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(14.dp))
            }
          }
          Column {
            Row(verticalAlignment = Alignment.Bottom) {
              Text(text = "3.8", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = " / 10", fontSize = 11.sp, color = GlacierOnSurfaceVariant)
            }
            Text(text = "Leve a moderado", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierSecondary)
          }
          Text(text = "Fadiga controlada", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
        }
      }
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Escala EVA
      GlacierCard(
        modifier = Modifier.weight(1f),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "ESCALA EVA", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(GlacierTertiary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.SentimentSatisfied, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(14.dp))
            }
          }
          Column {
            Row(verticalAlignment = Alignment.Bottom) {
              Text(text = "0.4", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = " / 10", fontSize = 11.sp, color = GlacierOnSurfaceVariant)
            }
            Text(text = "96% sem dor", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierTertiary)
          }
          Text(text = "Conforto preservado", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
        }
      }

      // Dias s/ Escape
      GlacierCard(
        modifier = Modifier.weight(1f),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
      ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "DIAS S/ ESCAPE", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(GlacierPrimary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(14.dp))
            }
          }
          Column {
            Text(text = "88%", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            Text(text = "Evolução notável", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          }
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(11.dp))
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "Meta atingida", fontSize = 9.sp, color = GlacierPrimary)
          }
        }
      }
    }

    // 3. Interactive Progress Chart Section (Curva de Esforço & Conforto)
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("chart_section_card"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.8f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top
        ) {
          Column {
            Text(text = "Curva de Esforço & Conforto", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            Text(text = "Correlação Borg (esforço) vs. Dor (EVA) nas semanas", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          }
          GlacierPill(text = "Semana 6", leadingDot = true, containerColor = GlacierPrimary.copy(alpha = 0.15f), contentColor = GlacierPrimary)
        }

        // Weeks labels
        Row(
          modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          listOf("Sem 1", "Sem 2", "Sem 3", "Sem 4", "Sem 5", "Sem 6 (Atual)").forEachIndexed { index, label ->
            Text(
              text = label,
              fontSize = 9.sp,
              fontWeight = if (index == 5) FontWeight.Bold else FontWeight.Normal,
              color = if (index == 5) GlacierPrimary else GlacierOnSurfaceVariant
            )
          }
        }

        // Vector Curve Canvas
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
        ) {
          Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Horizontal dotted grid lines
            drawLine(color = Color.White.copy(alpha = 0.08f), start = Offset(0f, h * 0.25f), end = Offset(w, h * 0.25f), strokeWidth = 1f)
            drawLine(color = Color.White.copy(alpha = 0.08f), start = Offset(0f, h * 0.55f), end = Offset(w, h * 0.55f), strokeWidth = 1f)
            drawLine(color = Color.White.copy(alpha = 0.08f), start = Offset(0f, h * 0.85f), end = Offset(w, h * 0.85f), strokeWidth = 1f)

            // Borg Path (Descending: 6.5 -> 5.0 -> 3.8)
            val borgPath = Path().apply {
              moveTo(w * 0.05f, h * 0.26f)
              cubicTo(w * 0.25f, h * 0.35f, w * 0.45f, h * 0.48f, w * 0.5f, h * 0.52f)
              cubicTo(w * 0.65f, h * 0.6f, w * 0.85f, h * 0.68f, w * 0.95f, h * 0.72f)
            }

            // Fill area
            val fillPath = Path().apply {
              addPath(borgPath)
              lineTo(w * 0.95f, h * 0.95f)
              lineTo(w * 0.05f, h * 0.95f)
              close()
            }
            drawPath(
              path = fillPath,
              brush = Brush.verticalGradient(listOf(GlacierPrimary.copy(alpha = 0.3f), Color.Transparent))
            )
            drawPath(
              path = borgPath,
              color = GlacierPrimary,
              style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )

            // EVA Path (Flat along bottom, ~0)
            val evaPath = Path().apply {
              moveTo(w * 0.05f, h * 0.88f)
              cubicTo(w * 0.3f, h * 0.9f, w * 0.6f, h * 0.91f, w * 0.95f, h * 0.92f)
            }
            drawPath(
              path = evaPath,
              color = GlacierTertiary,
              style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )

            // Data Points
            drawCircle(color = GlacierBackground, radius = 5.dp.toPx(), center = Offset(w * 0.05f, h * 0.26f))
            drawCircle(color = GlacierPrimary, radius = 4.dp.toPx(), center = Offset(w * 0.05f, h * 0.26f))

            drawCircle(color = GlacierBackground, radius = 5.dp.toPx(), center = Offset(w * 0.5f, h * 0.52f))
            drawCircle(color = GlacierPrimary, radius = 4.dp.toPx(), center = Offset(w * 0.5f, h * 0.52f))

            drawCircle(color = GlacierPrimary, radius = 6.dp.toPx(), center = Offset(w * 0.95f, h * 0.72f))
          }
        }

        // Legend & Insight
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .border(width = 0.5.dp, color = GlassBorder, shape = RoundedCornerShape(0.dp))
            .padding(top = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(modifier = Modifier.size(width = 12.dp, height = 3.dp).background(GlacierPrimary, CircleShape))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "Esforço (Borg)", fontSize = 11.sp, color = GlacierOnSurface)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(modifier = Modifier.size(width = 12.dp, height = 3.dp).background(GlacierTertiary, CircleShape))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "Dor (EVA)", fontSize = 11.sp, color = GlacierTertiary)
            }
          }

          Text(
            text = "-41% fadiga perineal",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = GlacierPrimary,
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(GlacierPrimary.copy(alpha = 0.12f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }

        // Adherence badge
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerHigh)
            .padding(10.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.ElectricBolt, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(text = "Aderência Excelente", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "5 dias de sequência ativa nesta semana", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
          }
        }
      }
    }

    // 4. Feedbacks Clínicos Recentes
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "FEEDBACKS CLÍNICOS RECENTES", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clickable { }
        ) {
          Text(text = "Filtrar", fontSize = 11.sp, color = GlacierPrimary)
          Spacer(modifier = Modifier.width(2.dp))
          Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(13.dp))
        }
      }

      feedbacks.forEach { feedback ->
        GlacierCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
        ) {
          Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column {
                Text(text = feedback.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
                Text(text = "${feedback.date} (${feedback.sessionDuration})", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
              }
              GlacierPill(
                text = feedback.status,
                containerColor = GlacierPrimary.copy(alpha = 0.15f),
                contentColor = GlacierPrimary
              )
            }

            // Badges row
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Text(
                text = "Borg: ${feedback.borgScore}",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierPrimary,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(GlacierSurfaceContainerHigh)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
              Text(
                text = "EVA: ${feedback.evaScore}",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierPrimary,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(GlacierSurfaceContainerHigh)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
              Text(
                text = "Apneia: Zero ✓",
                fontSize = 10.sp,
                color = GlacierSecondary,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(GlacierSecondary.copy(alpha = 0.12f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }

            // Patient message
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierSurfaceContainerLow)
                .padding(8.dp)
            ) {
              Column {
                Text(text = "Relato de Camila:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
                Text(text = feedback.patientMessage, fontSize = 11.sp, color = GlacierOnSurface)
              }
            }

            // Doctor response box
            if (feedback.doctorResponse != null) {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierPrimaryContainer.copy(alpha = 0.4f))
                  .padding(8.dp)
              ) {
                Column {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Text(text = "Dra. Luana Silva", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
                      Spacer(modifier = Modifier.width(4.dp))
                      Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(12.dp))
                    }
                    Text(text = feedback.doctorResponseTime ?: "", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
                  }
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(text = feedback.doctorResponse, fontSize = 11.sp, color = GlacierOnSurface, lineHeight = 15.sp)
                }
              }
            }
          }
        }
      }
    }

    // 5. Conquistas & Marcos Clínicos
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "CONQUISTAS & MARCOS CLÍNICOS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
        Text(text = "3 de 4 metas", fontSize = 10.sp, color = GlacierPrimary)
      }

      listOf(
        Triple("100% Livre de Sintomas na Tosse", "Conquistado na Semana 5 sem perda urinária de esforço", Icons.Default.EmojiEvents),
        Triple("Sustentação Firme de 5s Atingida", "Progressão de 3s para 5s estáveis sem compensação", Icons.Default.Timer),
        Triple("72h de Diário Miccional Válido", "Meta do protocolo cumprida e enviada à fisioterapeuta", Icons.Default.FactCheck)
      ).forEach { (title, subtitle, icon) ->
        GlacierCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.6f)
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GlacierPrimary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = icon, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = subtitle, fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(18.dp))
          }
        }
      }
    }

    // 6. Bottom Actions
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      GlacierCard(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onExportPdf() }
          .testTag("btn_exportar_relatorio_pdf"),
        backgroundColor = GlacierPrimary
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().height(48.dp),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = GlacierOnPrimary, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "Exportar Relatório Clínico (PDF)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnPrimary)
        }
      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(GlacierSurfaceContainerHigh)
          .clickable { onNavigateTo34Weeks() }
          .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(text = "Ver Preparo da 34ª Semana", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = GlacierPrimary)
      }
    }
  }
}
