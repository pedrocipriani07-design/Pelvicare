package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GlacierBackground
import com.example.ui.theme.GlacierError
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
import kotlin.math.cos
import kotlin.math.sin

/**
 * Ponto de dados genérico para gráficos estilo Recharts / D3.
 */
data class ChartPoint(
  val label: String,
  val value: Float,
  val secondaryValue: Float? = null,
  val tooltipDetail: String? = null
)

/**
 * Segmento para Gráficos Donut estilo D3.
 */
data class DonutSegment(
  val label: String,
  val value: Float,
  val color: Color,
  val detail: String = ""
)

/**
 * 1. RECHARTS AREA CHART COMPOSABLE
 * Gráfico de área suavizada estilo Recharts com gradiente vertical,
 * linhas de grade pontilhadas sutis, linha de meta clínica (ReferenceLine)
 * e Tooltip interativo flutuante ao tocar em qualquer ponto.
 */
@Composable
fun RechartsAreaChartComposable(
  data: List<ChartPoint>,
  modifier: Modifier = Modifier,
  primaryColor: Color = GlacierPrimary,
  secondaryColor: Color = GlacierTertiary,
  targetValue: Float? = null,
  targetLabel: String? = null,
  unit: String = "",
  chartHeight: Dp = 160.dp,
  showSecondaryLine: Boolean = false,
  testTag: String = "recharts_area_chart"
) {
  if (data.isEmpty()) return

  var selectedIndex by remember { mutableStateOf<Int?>(null) }
  val maxValue = remember(data, targetValue) {
    val maxVal = data.maxOf { point ->
      if (showSecondaryLine && point.secondaryValue != null) {
        maxOf(point.value, point.secondaryValue)
      } else {
        point.value
      }
    }
    val candidate = maxOf(maxVal, targetValue ?: 0f)
    if (candidate > 0f) candidate * 1.2f else 10f
  }

  Column(modifier = modifier.testTag(testTag)) {
    // Tooltip flutuante superior no padrão Recharts Tooltip
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(34.dp)
        .padding(bottom = 4.dp),
      contentAlignment = Alignment.CenterStart
    ) {
      if (selectedIndex != null && selectedIndex!! in data.indices) {
        val selectedPoint = data[selectedIndex!!]
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerHighest)
            .border(1.dp, GlacierPrimary.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(primaryColor)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "${selectedPoint.label}: ${selectedPoint.value.toInt()}$unit",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierOnSurface
          )
          if (showSecondaryLine && selectedPoint.secondaryValue != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(secondaryColor)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Sec: ${selectedPoint.secondaryValue.toInt()}$unit",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = secondaryColor
            )
          }
          if (selectedPoint.tooltipDetail != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "• ${selectedPoint.tooltipDetail}",
              fontSize = 10.sp,
              color = GlacierOnSurfaceVariant
            )
          }
        }
      } else {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(start = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = GlacierOnSurfaceVariant,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Toque nos pontos da curva para inspecionar valores",
            fontSize = 10.sp,
            color = GlacierOnSurfaceVariant
          )
        }
      }
    }

    // Canvas do Gráfico Recharts
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(chartHeight)
        .pointerInput(data) {
          detectTapGestures { offset ->
            val w = size.width
            val step = if (data.size > 1) w / (data.size - 1) else w
            val touchedIndex = (offset.x / step + 0.5f).toInt().coerceIn(0, data.lastIndex)
            selectedIndex = if (selectedIndex == touchedIndex) null else touchedIndex
          }
        }
    ) {
      Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val paddingBottom = 20.dp.toPx()
        val graphHeight = h - paddingBottom

        // 1. Linhas de grade pontilhadas horizontais (Grid Recharts)
        val dashEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
        for (i in 1..3) {
          val y = graphHeight * (i / 4f)
          drawLine(
            color = Color.White.copy(alpha = 0.07f),
            start = Offset(0f, y),
            end = Offset(w, y),
            strokeWidth = 1f,
            pathEffect = dashEffect
          )
        }

        // 2. Linha de referência clínica (ReferenceLine) se houver
        if (targetValue != null) {
          val targetY = graphHeight * (1f - (targetValue / maxValue)).coerceIn(0f, 1f)
          drawLine(
            color = GlacierSecondary.copy(alpha = 0.5f),
            start = Offset(0f, targetY),
            end = Offset(w, targetY),
            strokeWidth = 1.5.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 6f), 0f)
          )
        }

        // 3. Cálculo de pontos da curva primária
        val points = data.mapIndexed { index, point ->
          val x = if (data.size > 1) (index.toFloat() / (data.size - 1)) * w else w / 2f
          val y = graphHeight * (1f - (point.value / maxValue)).coerceIn(0f, 1f)
          Offset(x, y)
        }

        // 4. Criação do Path Suavizado (Cubic Bézier Spline)
        val splinePath = Path().apply {
          if (points.isNotEmpty()) {
            moveTo(points.first().x, points.first().y)
            for (i in 0 until points.size - 1) {
              val p0 = points[i]
              val p1 = points[i + 1]
              val cx1 = p0.x + (p1.x - p0.x) / 2f
              val cy1 = p0.y
              val cx2 = p0.x + (p1.x - p0.x) / 2f
              val cy2 = p1.y
              cubicTo(cx1, cy1, cx2, cy2, p1.x, p1.y)
            }
          }
        }

        // 5. Gradiente de Preenchimento da Área (Recharts Area Fill)
        val areaPath = Path().apply {
          addPath(splinePath)
          if (points.isNotEmpty()) {
            lineTo(points.last().x, graphHeight)
            lineTo(points.first().x, graphHeight)
            close()
          }
        }

        drawPath(
          path = areaPath,
          brush = Brush.verticalGradient(
            colors = listOf(
              primaryColor.copy(alpha = 0.35f),
              primaryColor.copy(alpha = 0.02f)
            ),
            startY = 0f,
            endY = graphHeight
          )
        )

        // 6. Linha do gráfico
        drawPath(
          path = splinePath,
          color = primaryColor,
          style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        // 7. Curva secundária (se ativada, ex: Borg vs EVA)
        if (showSecondaryLine) {
          val secPoints = data.mapIndexed { index, point ->
            val x = if (data.size > 1) (index.toFloat() / (data.size - 1)) * w else w / 2f
            val y = graphHeight * (1f - ((point.secondaryValue ?: 0f) / maxValue)).coerceIn(0f, 1f)
            Offset(x, y)
          }
          val secPath = Path().apply {
            if (secPoints.isNotEmpty()) {
              moveTo(secPoints.first().x, secPoints.first().y)
              for (i in 0 until secPoints.size - 1) {
                val p0 = secPoints[i]
                val p1 = secPoints[i + 1]
                val cx1 = p0.x + (p1.x - p0.x) / 2f
                val cy1 = p0.y
                val cx2 = p0.x + (p1.x - p0.x) / 2f
                val cy2 = p1.y
                cubicTo(cx1, cy1, cx2, cy2, p1.x, p1.y)
              }
            }
          }
          drawPath(
            path = secPath,
            color = secondaryColor,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
          )
        }

        // 8. Marcadores circulares nos pontos (Dots Recharts)
        points.forEachIndexed { index, pt ->
          val isSelected = index == selectedIndex
          // Círculo de fundo
          drawCircle(
            color = GlacierBackground,
            radius = if (isSelected) 7.dp.toPx() else 4.dp.toPx(),
            center = pt
          )
          // Círculo colorido
          drawCircle(
            color = if (isSelected) Color.White else primaryColor,
            radius = if (isSelected) 5.dp.toPx() else 3.dp.toPx(),
            center = pt
          )
          // Anel exterior no ponto selecionado
          if (isSelected) {
            drawCircle(
              color = primaryColor,
              radius = 8.dp.toPx(),
              center = pt,
              style = Stroke(width = 2.dp.toPx())
            )
            // Linha guia vertical tracejada até o eixo X
            drawLine(
              color = primaryColor.copy(alpha = 0.5f),
              start = Offset(pt.x, pt.y + 8.dp.toPx()),
              end = Offset(pt.x, graphHeight),
              strokeWidth = 1.dp.toPx(),
              pathEffect = dashEffect
            )
          }
        }
      }
    }

    // Rótulos do Eixo X
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      data.forEachIndexed { index, point ->
        val isSelected = index == selectedIndex
        Text(
          text = point.label,
          fontSize = 9.sp,
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
          color = if (isSelected) GlacierPrimary else GlacierOnSurfaceVariant
        )
      }
    }

    // Legenda inferior
    if (targetLabel != null) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .width(14.dp)
              .height(2.dp)
              .background(GlacierSecondary.copy(alpha = 0.6f))
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = targetLabel,
            fontSize = 10.sp,
            color = GlacierSecondary
          )
        }
      }
    }
  }
}

/**
 * 2. RECHARTS BAR CHART COMPOSABLE
 * Gráfico de barras com suporte a barras simples ou duplas (agrupadas),
 * topos arredondados, realce interativo e exibição de valores nos eixos.
 */
@Composable
fun RechartsBarChartComposable(
  data: List<ChartPoint>,
  modifier: Modifier = Modifier,
  primaryColor: Color = GlacierPrimary,
  secondaryColor: Color = GlacierSecondary,
  primaryLegend: String = "Valor",
  secondaryLegend: String? = null,
  unit: String = "",
  chartHeight: Dp = 150.dp,
  referenceThreshold: Float? = null,
  referenceLabel: String? = null,
  testTag: String = "recharts_bar_chart"
) {
  if (data.isEmpty()) return

  var selectedIndex by remember { mutableStateOf<Int?>(null) }
  val maxValue = remember(data, referenceThreshold) {
    val maxVal = data.maxOf {
      if (secondaryLegend != null && it.secondaryValue != null) {
        maxOf(it.value, it.secondaryValue)
      } else {
        it.value
      }
    }
    val candidate = maxOf(maxVal, referenceThreshold ?: 0f)
    if (candidate > 0f) candidate * 1.25f else 10f
  }

  Column(modifier = modifier.testTag(testTag)) {
    // Tooltip flutuante superior
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(34.dp)
        .padding(bottom = 4.dp),
      contentAlignment = Alignment.CenterStart
    ) {
      if (selectedIndex != null && selectedIndex!! in data.indices) {
        val selectedPoint = data[selectedIndex!!]
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerHighest)
            .border(1.dp, primaryColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${selectedPoint.label}: ",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierOnSurface
          )
          Text(
            text = "${selectedPoint.value.toInt()}$unit ($primaryLegend)",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = primaryColor
          )
          if (secondaryLegend != null && selectedPoint.secondaryValue != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "• ${selectedPoint.secondaryValue.toInt()}$unit ($secondaryLegend)",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = secondaryColor
            )
          }
          if (selectedPoint.tooltipDetail != null) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "• ${selectedPoint.tooltipDetail}",
              fontSize = 10.sp,
              color = GlacierOnSurfaceVariant
            )
          }
        }
      } else {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(start = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = GlacierOnSurfaceVariant,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Toque em uma barra para detalhes",
            fontSize = 10.sp,
            color = GlacierOnSurfaceVariant
          )
        }
      }
    }

    // Canvas das barras
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(chartHeight)
        .pointerInput(data) {
          detectTapGestures { offset ->
            val w = size.width
            val barWidthTotal = w / data.size
            val touchedIndex = (offset.x / barWidthTotal).toInt().coerceIn(0, data.lastIndex)
            selectedIndex = if (selectedIndex == touchedIndex) null else touchedIndex
          }
        }
    ) {
      Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Linhas de grade sutis
        val dashEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
        for (i in 1..3) {
          val y = h * (i / 4f)
          drawLine(
            color = Color.White.copy(alpha = 0.06f),
            start = Offset(0f, y),
            end = Offset(w, y),
            strokeWidth = 1f,
            pathEffect = dashEffect
          )
        }

        // Linha de referência se houver
        if (referenceThreshold != null) {
          val refY = h * (1f - (referenceThreshold / maxValue)).coerceIn(0f, 1f)
          drawLine(
            color = GlacierTertiary.copy(alpha = 0.6f),
            start = Offset(0f, refY),
            end = Offset(w, refY),
            strokeWidth = 1.5.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 6f), 0f)
          )
        }

        val totalSlots = data.size
        val slotWidth = w / totalSlots
        val hasSecondary = secondaryLegend != null

        data.forEachIndexed { index, point ->
          val isSelected = index == selectedIndex
          val alphaMultiplier = if (selectedIndex == null || isSelected) 1f else 0.4f

          if (!hasSecondary) {
            // Barra Simples
            val barWidth = slotWidth * 0.55f
            val barHeight = (point.value / maxValue).coerceIn(0f, 1f) * h
            val left = index * slotWidth + (slotWidth - barWidth) / 2f
            val top = h - barHeight

            drawRoundRect(
              brush = Brush.verticalGradient(
                colors = listOf(
                  primaryColor.copy(alpha = alphaMultiplier),
                  primaryColor.copy(alpha = 0.6f * alphaMultiplier)
                ),
                startY = top,
                endY = h
              ),
              topLeft = Offset(left, top),
              size = Size(barWidth, barHeight),
              cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
            )

            // Destaque de seleção
            if (isSelected) {
              drawRoundRect(
                color = Color.White.copy(alpha = 0.8f),
                topLeft = Offset(left - 1.dp.toPx(), top - 1.dp.toPx()),
                size = Size(barWidth + 2.dp.toPx(), barHeight + 1.dp.toPx()),
                style = Stroke(width = 1.5.dp.toPx()),
                cornerRadius = CornerRadius(7.dp.toPx(), 7.dp.toPx())
              )
            }
          } else {
            // Barras Duplas Agrupadas
            val groupWidth = slotWidth * 0.72f
            val singleBarWidth = groupWidth / 2f - 2.dp.toPx()
            val groupLeft = index * slotWidth + (slotWidth - groupWidth) / 2f

            // Barra 1
            val bar1Height = (point.value / maxValue).coerceIn(0f, 1f) * h
            val bar1Top = h - bar1Height
            drawRoundRect(
              color = primaryColor.copy(alpha = alphaMultiplier),
              topLeft = Offset(groupLeft, bar1Top),
              size = Size(singleBarWidth, bar1Height),
              cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )

            // Barra 2
            val secVal = point.secondaryValue ?: 0f
            val bar2Height = (secVal / maxValue).coerceIn(0f, 1f) * h
            val bar2Top = h - bar2Height
            drawRoundRect(
              color = secondaryColor.copy(alpha = alphaMultiplier),
              topLeft = Offset(groupLeft + singleBarWidth + 4.dp.toPx(), bar2Top),
              size = Size(singleBarWidth, bar2Height),
              cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )
          }
        }
      }
    }

    // Rótulos do Eixo X
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      data.forEachIndexed { index, point ->
        val isSelected = index == selectedIndex
        Text(
          text = point.label,
          fontSize = 9.sp,
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
          color = if (isSelected) primaryColor else GlacierOnSurfaceVariant
        )
      }
    }

    // Legenda Recharts Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(10.dp)
              .clip(RoundedCornerShape(2.dp))
              .background(primaryColor)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = primaryLegend, fontSize = 11.sp, color = GlacierOnSurface)
        }
        if (secondaryLegend != null) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(secondaryColor)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = secondaryLegend, fontSize = 11.sp, color = secondaryColor)
          }
        }
      }

      if (referenceLabel != null) {
        Text(
          text = referenceLabel,
          fontSize = 10.sp,
          color = GlacierTertiary,
          fontWeight = FontWeight.Medium
        )
      }
    }
  }
}

/**
 * 3. D3 DONUT CHART COMPOSABLE
 * Gráfico de Rosca estilo D3.js com segmentos proporcionais,
 * centro oco com métrica chave e legenda descritiva interativa.
 */
@Composable
fun D3DonutChartComposable(
  segments: List<DonutSegment>,
  modifier: Modifier = Modifier,
  centerTitle: String = "Total",
  centerSubtitle: String = "100%",
  donutSize: Dp = 140.dp,
  strokeWidth: Dp = 22.dp,
  testTag: String = "d3_donut_chart"
) {
  val total = remember(segments) { segments.sumOf { it.value.toDouble() }.toFloat().coerceAtLeast(1f) }
  var selectedIndex by remember { mutableStateOf<Int?>(null) }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .testTag(testTag),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Donut Canvas
    Box(
      modifier = Modifier
        .size(donutSize),
      contentAlignment = Alignment.Center
    ) {
      Canvas(modifier = Modifier.fillMaxSize()) {
        val diameter = size.minDimension
        val stroke = strokeWidth.toPx()
        val radius = (diameter - stroke) / 2f
        val center = Offset(size.width / 2f, size.height / 2f)

        var startAngle = -90f
        segments.forEachIndexed { index, segment ->
          val sweepAngle = (segment.value / total) * 360f
          val isSelected = index == selectedIndex
          val currentStroke = if (isSelected) stroke * 1.18f else stroke

          drawArc(
            color = segment.color,
            startAngle = startAngle + 2f, // Pequeno gap estilo D3
            sweepAngle = (sweepAngle - 4f).coerceAtLeast(1f),
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2f, radius * 2f),
            style = Stroke(width = currentStroke, cap = StrokeCap.Round)
          )
          startAngle += sweepAngle
        }
      }

      // Centro do Donut com estatística
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val displayTitle = if (selectedIndex != null && selectedIndex!! in segments.indices) {
          segments[selectedIndex!!].label
        } else {
          centerTitle
        }
        val displaySub = if (selectedIndex != null && selectedIndex!! in segments.indices) {
          val seg = segments[selectedIndex!!]
          "${((seg.value / total) * 100).toInt()}%"
        } else {
          centerSubtitle
        }

        Text(
          text = displayTitle,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierOnSurface,
          maxLines = 1
        )
        Text(
          text = displaySub,
          fontSize = 16.sp,
          fontWeight = FontWeight.ExtraBold,
          color = GlacierPrimary
        )
      }
    }

    // Legenda D3 ao lado
    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      segments.forEachIndexed { index, segment ->
        val pct = ((segment.value / total) * 100).toInt()
        val isSelected = index == selectedIndex
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) GlacierSurfaceContainerHigh else Color.Transparent)
            .clickable { selectedIndex = if (isSelected) null else index }
            .padding(horizontal = 6.dp, vertical = 3.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(segment.color)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = segment.label,
              fontSize = 11.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              color = GlacierOnSurface
            )
          }
          Text(
            text = "$pct%",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = segment.color
          )
        }
      }
    }
  }
}
