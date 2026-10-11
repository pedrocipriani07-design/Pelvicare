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
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleanHands
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.EventRepeat
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.Shower
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.GlacierButton
import com.example.ui.components.GlacierCard
import com.example.ui.components.GlacierPill
import com.example.ui.theme.GlacierError
import com.example.ui.theme.GlacierErrorContainer
import com.example.ui.theme.GlacierOnPrimary
import com.example.ui.theme.GlacierOnSurface
import com.example.ui.theme.GlacierOnSurfaceVariant
import com.example.ui.theme.GlacierPrimary
import com.example.ui.theme.GlacierPrimaryContainer
import com.example.ui.theme.GlacierOnPrimaryContainer
import com.example.ui.theme.GlacierSecondary
import com.example.ui.theme.GlacierSecondaryContainer
import com.example.ui.theme.GlacierSurfaceContainer
import com.example.ui.theme.GlacierSurfaceContainerHigh
import com.example.ui.theme.GlacierSurfaceContainerHighest
import com.example.ui.theme.GlacierSurfaceContainerLow
import com.example.ui.theme.GlacierSurfaceContainerLowest
import com.example.ui.theme.GlacierTertiary
import com.example.ui.theme.GlacierTertiaryContainer
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassSurface
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MassagemPerinealScreen(
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedPostureIndex by remember { mutableIntStateOf(0) }
  var timerSeconds by remember { mutableIntStateOf(195) } // 03:15
  var isTimerRunning by remember { mutableStateOf(false) }
  var showFeedbackSheet by remember { mutableStateOf(false) }
  var isAudioPlaying by remember { mutableStateOf(false) }

  // Timer loop
  LaunchedEffect(isTimerRunning) {
    while (isTimerRunning && timerSeconds > 0) {
      delay(1000L)
      timerSeconds -= 1
    }
  }

  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Context Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(GlacierSurfaceContainer.copy(alpha = 0.8f))
          .clickable { onBack() }
          .padding(horizontal = 10.dp, vertical = 6.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar", tint = GlacierPrimary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = "Voltar para Treinos", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierPrimary)
        }
      }

      GlacierPill(text = "34ª Semana Gestacional", containerColor = GlacierPrimary.copy(alpha = 0.15f), contentColor = GlacierPrimary)
    }

    // Title & Protocol Info
    Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "Massagem Perineal Orientada",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierOnSurface
        )
        Spacer(modifier = Modifier.width(6.dp))
        Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(20.dp))
      }
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = "Protocolo da 34ª à 40ª Semana • Dra. Luana Silva (Fisioterapeuta Pélvica)",
        fontSize = 11.sp,
        color = GlacierOnSurfaceVariant
      )
    }

    // Frequency & Duration Badges
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(9999.dp))
          .background(GlacierSurfaceContainerHigh)
          .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(imageVector = Icons.Default.EventRepeat, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Frequência: 3 a 4x / semana", fontSize = 11.sp, color = GlacierOnSurface)
      }

      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(9999.dp))
          .background(GlacierSurfaceContainerHigh)
          .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Duração: 5-8 min", fontSize = 11.sp, color = GlacierOnSurface)
      }
    }

    // 2. Audio Player / Quick Tip Card
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainerHigh
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .border(1.5.dp, GlacierPrimary.copy(alpha = 0.4f), CircleShape)
        ) {
          AsyncImage(
            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuA_VWpijlrvD2V9LOfGZ6vjKCrAyTExMQCbu8xDtGqtGXmnJxmYBD8lyfEYuhviBCd-eSk8MKY8VF9M2G3WAolY4NzmbtC1zVekw3jGb_BsmuROF26f8sWMEP-4eeIeuaTrnDitroeX_-RNve-L6YX3V-wHxhw0ovjpHQRyizjljUYkKp3k2UdT18fPlRICEwTBQJ7UlLl0GuvwIFXzN8oe3A9vDAogAmnGjDuQ0_xA_BTvMHskvzg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "ÁUDIO GUIADO • 1:20 MIN", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            Text(text = "Respiração Guiada", fontSize = 9.sp, color = GlacierTertiary)
          }
          Text(text = "Sensibilidade, pressão correta e desmistificação", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
          Text(text = "0:38 / 1:20", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(GlacierPrimary)
            .clickable { isAudioPlaying = !isAudioPlaying },
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = null,
            tint = GlacierOnPrimary,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }

    // 3. Posture Interactive Selector
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "POSTURA CONFORTÁVEL", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
        Text(text = "Escolha o seu apoio ideal", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(GlacierSurfaceContainer)
          .padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        listOf(
          Triple(0, "Semi-sentada", Icons.Default.Weekend),
          Triple(1, "Em pé / Apoio", Icons.Default.AccessibilityNew),
          Triple(2, "Deitada lateral", Icons.Default.Hotel)
        ).forEach { (idx, label, icon) ->
          val isSelected = selectedPostureIndex == idx
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(if (isSelected) GlacierPrimaryContainer else Color.Transparent)
              .clickable { selectedPostureIndex = idx }
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(imageVector = icon, contentDescription = null, tint = if (isSelected) GlacierPrimary else GlacierOnSurfaceVariant, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) GlacierOnPrimaryContainer else GlacierOnSurfaceVariant
              )
            }
          }
        }
      }

      // Selected Posture Description Card
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(GlacierSurfaceContainerLow)
          .padding(12.dp)
      ) {
        Column {
          val postureTitle = when (selectedPostureIndex) {
            0 -> "Apoio Semi-sentado com Almofadas"
            1 -> "Em Pé com Apoio Elevado"
            else -> "Deitada Lateral (Decúbito Lateral)"
          }
          val postureDesc = when (selectedPostureIndex) {
            0 -> "Eleve as costas na cama ou sofá a 45º com travesseiros. Dobre os joelhos e apoie a planta dos pés. Um pequeno espelho de mão entre as pernas auxilia a reconhecer as estruturas anatômicas nas primeiras sessões."
            1 -> "Fique de pé no banheiro ou quarto e apoie uma das pernas na borda da banheira ou em um banquinho firme. Alterne o lado no meio da massagem para relaxamento uniforme."
            else -> "Deite de lado (preferencialmente lado esquerdo) com uma almofada grande entre os joelhos para aliviar a pelve e a veia cava. Excelente para gestantes que sentem falta de ar ao deitar de costas na reta final."
          }
          Text(text = postureTitle, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          Spacer(modifier = Modifier.height(2.dp))
          Text(text = postureDesc, fontSize = 11.sp, color = GlacierOnSurfaceVariant, lineHeight = 15.sp)
        }
      }
    }

    // 4. Preparação & Cuidados Iniciais (4 cards)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "PREPARAÇÃO & CUIDADOS INICIAIS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
        Text(text = "4 CUIDADOS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainer)
            .padding(10.dp)
        ) {
          Column {
            Icon(imageVector = Icons.Default.CleanHands, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Mãos & Unhas", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            Text(text = "Lave bem as mãos; unhas limpas e lixadas.", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
          }
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainer)
            .padding(10.dp)
        ) {
          Column {
            Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Óleo / Lubrificante", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            Text(text = "Óleo de Amêndoas Doces 100% puro ou gel neutro.", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
          }
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainer)
            .padding(10.dp)
        ) {
          Column {
            Icon(imageVector = Icons.Default.Wc, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Esvaziar Bexiga", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            Text(text = "Urine minutos antes para relaxar completamente.", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
          }
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainer)
            .padding(10.dp)
        ) {
          Column {
            Icon(imageVector = Icons.Default.Shower, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Calor Local (Opcional)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            Text(text = "Banho morno para relaxar a fáscia antes.", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
          }
        }
      }
    }

    // 5. Mapa Anatômico do Relógio Perineal (SVG / Canvas)
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("perineal_clock_card"),
      backgroundColor = GlacierSurfaceContainerHigh
    ) {
      Column(
        modifier = Modifier.padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(text = "Mapa Anatômico do Relógio Perineal", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            Text(text = "Visualização do introito posterior e direções de estímulo", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          }
          GlacierPill(text = "Esquema Guiado", containerColor = GlacierPrimary.copy(alpha = 0.15f), contentColor = GlacierPrimary)
        }

        // Custom Perineal Clock Canvas
        Box(
          modifier = Modifier.size(180.dp),
          contentAlignment = Alignment.Center
        ) {
          Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width * 0.42f

            // Outer dashed circle
            drawCircle(
              color = Color(0xFF2A3A48),
              radius = radius,
              center = center,
              style = Stroke(width = 1.5.dp.toPx())
            )

            // Upper red arch (12h - do not massage)
            drawArc(
              color = GlacierError.copy(alpha = 0.6f),
              startAngle = 200f,
              sweepAngle = 140f,
              useCenter = false,
              topLeft = Offset(center.x - radius, center.y - radius),
              size = Size(radius * 2, radius * 2),
              style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
            )

            // Lower cyan arch (3h to 9h - U-shape)
            drawArc(
              color = GlacierPrimary,
              startAngle = 20f,
              sweepAngle = 140f,
              useCenter = false,
              topLeft = Offset(center.x - radius, center.y - radius),
              size = Size(radius * 2, radius * 2),
              style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
            )

            // Center Introitus Oval
            drawOval(
              color = GlacierSurfaceContainer,
              topLeft = Offset(center.x - 30.dp.toPx(), center.y - 40.dp.toPx()),
              size = Size(60.dp.toPx(), 80.dp.toPx())
            )
            drawOval(
              color = Color(0xFF4A6070),
              topLeft = Offset(center.x - 30.dp.toPx(), center.y - 40.dp.toPx()),
              size = Size(60.dp.toPx(), 80.dp.toPx()),
              style = Stroke(width = 1.dp.toPx())
            )

            // 12h dot
            drawCircle(color = GlacierError, radius = 5.dp.toPx(), center = Offset(center.x, center.y - radius))

            // 3h dot
            drawCircle(color = GlacierPrimary, radius = 5.dp.toPx(), center = Offset(center.x + radius, center.y))

            // 9h dot
            drawCircle(color = GlacierPrimary, radius = 5.dp.toPx(), center = Offset(center.x - radius, center.y))

            // 6h center dot (main focus)
            drawCircle(color = GlacierPrimary, radius = 7.dp.toPx(), center = Offset(center.x, center.y + radius))
            drawCircle(color = GlacierOnPrimary, radius = 3.dp.toPx(), center = Offset(center.x, center.y + radius))
          }

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Polegar: 2-3 cm", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierSecondary)
          }
        }

        // Legend
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierSurfaceContainer)
              .padding(8.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(GlacierPrimary))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = "Arco 3h às 9h: Movimento contínuo em U", fontSize = 9.sp, color = GlacierOnSurface)
            }
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierSurfaceContainer)
              .padding(8.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(GlacierError))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = "Arco 12h: Manter livre de pressão", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
            }
          }
        }
      }
    }

    // 6. Passo a Passo em 4 Etapas
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.FormatListNumbered, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "PASSO A PASSO EM 4 ETAPAS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
        }
        Text(text = "Siga com calma", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
      }

      listOf(
        Triple("01", "Lubrificação & Acomodação", "Aplique óleo ou lubrificante nos polegares e na fenda perineal. Insira delicadamente os polegares cerca de 2 a 3 cm no introito vaginal posterior."),
        Triple("02", "Pressão Constante para Baixo (6h)", "Pressione com firmeza suave em direção ao ânus (posição de 6 horas) até notar uma leve sensação de estiramento ou formigamento agradável, sustentando 1 a 2 min."),
        Triple("03", "Movimento em U ou Pêndulo (3h às 9h)", "Mantendo a leve pressão descendente, mova lenta e ritmicamente os polegares de um lado para o outro em formato de 'U'."),
        Triple("04", "Relaxamento Concomitante com Expiração", "Ao sentir a tensão máxima do tecido, solte o ar suavemente entre os lábios ('soprando uma vela'), relaxando ombros e mandíbula.")
      ).forEach { (step, title, desc) ->
        GlacierCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierPrimaryContainer),
              contentAlignment = Alignment.Center
            ) {
              Text(text = step, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Spacer(modifier = Modifier.height(2.dp))
              Text(text = desc, fontSize = 11.sp, color = GlacierOnSurfaceVariant, lineHeight = 15.sp)
            }
          }
        }
      }
    }

    // 7. Interactive Timer & Rhythm Widget
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("massage_timer_card"),
      backgroundColor = GlacierSurfaceContainerHigh
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(GlacierPrimary))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "CRONÔMETRO & RITMO DA SESSÃO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          }
          Text(text = "Meta: 05:00 min", fontSize = 10.sp, color = GlacierPrimary)
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Circular Countdown
          val min = timerSeconds / 60
          val sec = timerSeconds % 60
          val formattedTime = String.format("%02d:%02d", min, sec)

          Box(
            modifier = Modifier
              .size(80.dp)
              .clip(CircleShape)
              .background(GlacierSurfaceContainerLowest)
              .border(3.dp, GlacierPrimary, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = formattedTime, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "restante", fontSize = 8.sp, color = GlacierOnSurfaceVariant)
            }
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(text = "Série 2 de 5 • Movimento U", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            Text(text = "Deslize rítmico das 3h às 9h", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            Text(text = "Mantenha respiração tranquila e constante.", fontSize = 10.sp, color = GlacierOnSurfaceVariant)

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierPrimary)
                  .clickable { isTimerRunning = !isTimerRunning }
                  .padding(horizontal = 12.dp, vertical = 6.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = GlacierOnPrimary,
                    modifier = Modifier.size(14.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = if (isTimerRunning) "Pausar" else "Iniciar",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GlacierOnPrimary
                  )
                }
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierSurfaceContainer)
                  .clickable { timerSeconds = (timerSeconds + 60).coerceAtMost(480) }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(text = "+1 min", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = GlacierOnSurface)
              }
            }
          }
        }
      }
    }

    // 8. Medical Caution Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(GlacierErrorContainer.copy(alpha = 0.4f))
        .border(1.dp, GlacierError.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
        .padding(12.dp)
    ) {
      Row(verticalAlignment = Alignment.Top) {
        Icon(imageVector = Icons.Default.Emergency, contentDescription = null, tint = GlacierError, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Text(text = "Quando suspender a sessão imediatamente:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierError)
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Dor aguda lancinante, presença de sangramento vaginal ativo, suspeita de perda de líquido amniótico ou contrações uterinas rítmicas e dolorosas.",
            fontSize = 10.sp,
            color = GlacierOnSurface,
            lineHeight = 14.sp
          )
        }
      }
    }

    // 9. Finish Button to Open Post-Massage Feedback
    GlacierButton(
      text = "Concluir Massagem & Registrar Feedback",
      onClick = { showFeedbackSheet = true },
      icon = Icons.Default.CheckCircle,
      modifier = Modifier.fillMaxWidth().testTag("btn_concluir_massagem")
    )
  }

  // Post-Massage Feedback Bottom Sheet
  if (showFeedbackSheet) {
    ModalBottomSheet(
      onDismissRequest = { showFeedbackSheet = false },
      containerColor = GlacierSurfaceContainer,
      dragHandle = {
        Box(
          modifier = Modifier
            .padding(vertical = 8.dp)
            .size(width = 40.dp, height = 4.dp)
            .clip(RoundedCornerShape(9999.dp))
            .background(GlacierOnSurfaceVariant.copy(alpha = 0.4f))
        )
      }
    ) {
      var selectedEva by remember { mutableIntStateOf(4) }
      var selectedTissue by remember { mutableStateOf("Tensão moderada / Normal") }
      var selectedZone by remember { mutableStateOf("Corpo Central") }
      var noteFeedback by remember {
        mutableStateOf("Consegui sincronizar bem a respiração expiratória profunda. A ardência aliviou por volta do terceiro minuto.")
      }
      var isFeedbackSaved by remember { mutableStateOf(false) }

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 10.dp)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(text = "Feedback Pós-Massagem", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            Text(text = "05:00 min • 34ª Semana Gestacional", fontSize = 11.sp, color = GlacierOnSurfaceVariant)
          }
          IconButton(onClick = { showFeedbackSheet = false }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = GlacierOnSurfaceVariant)
          }
        }

        // 1. EVA Estiramento
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "1. Percepção de Estiramento & Ardência", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            GlacierPill(text = "EVA: Grau $selectedEva", containerColor = GlacierPrimary.copy(alpha = 0.15f), contentColor = GlacierPrimary)
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
          ) {
            (0..10).forEach { num ->
              val isSel = selectedEva == num
              Box(
                modifier = Modifier
                  .weight(1f)
                  .height(32.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(if (isSel) GlacierPrimary else GlacierSurfaceContainerHigh)
                  .clickable { selectedEva = num },
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = num.toString(),
                  fontSize = 11.sp,
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSel) GlacierOnPrimary else GlacierOnSurfaceVariant
                )
              }
            }
          }

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierPrimary.copy(alpha = 0.12f))
              .padding(8.dp)
          ) {
            Text(
              text = "Nível $selectedEva: Estiramento Tolerável & Calor Suave (Faixa Ideal)",
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = GlacierPrimary
            )
          }
        }

        // 2. Elasticidade
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text(text = "2. Elasticidade dos Tecidos", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          listOf(
            "Mais flexível que o habitual",
            "Tensão moderada / Normal",
            "Muito rígido / Dificuldade de acomodar"
          ).forEach { option ->
            val isSel = selectedTissue == option
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSel) GlacierPrimaryContainer.copy(alpha = 0.5f) else GlacierSurfaceContainerLow)
                .clickable { selectedTissue = option }
                .padding(10.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = option, fontSize = 11.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal, color = GlacierOnSurface)
                if (isSel) {
                  Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
                }
              }
            }
          }
        }

        // 3. Zonas Sensíveis
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text(text = "3. Zonas com Maior Sensibilidade", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("3h Arco Esquerdo", "6h Corpo Central", "9h Arco Direito").forEach { zone ->
              val isSel = selectedZone in zone
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSel) GlacierPrimary.copy(alpha = 0.2f) else GlacierSurfaceContainerHigh)
                  .border(1.dp, if (isSel) GlacierPrimary else GlassBorder, RoundedCornerShape(8.dp))
                  .clickable { selectedZone = zone }
                  .padding(8.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(text = zone, fontSize = 10.sp, textAlign = TextAlign.Center, color = if (isSel) GlacierPrimary else GlacierOnSurface)
              }
            }
          }
        }

        // 4. Triagem de Segurança
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text(text = "4. Triagem de Segurança Imediata", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "Presença de sangramento", fontSize = 11.sp, color = GlacierOnSurfaceVariant)
            Text(text = "Não ✓", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierSecondary)
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "Cólicas ou contrações", fontSize = 11.sp, color = GlacierOnSurfaceVariant)
            Text(text = "Não ✓", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierSecondary)
          }
        }

        // 5. Nota para Dra. Luana
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text(text = "5. Nota para Dra. Luana Silva", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          OutlinedTextField(
            value = noteFeedback,
            onValueChange = { noteFeedback = it },
            modifier = Modifier.fillMaxWidth().height(80.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp, color = GlacierOnSurface),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = GlacierPrimary,
              unfocusedBorderColor = GlassBorder,
              focusedContainerColor = GlacierSurfaceContainerHigh,
              unfocusedContainerColor = GlacierSurfaceContainerHigh
            ),
            shape = RoundedCornerShape(10.dp)
          )
        }

        // Action Button
        GlacierButton(
          text = if (isFeedbackSaved) "Salvo no Prontuário!" else "Salvar & Registrar no Histórico",
          onClick = {
            isFeedbackSaved = true
            showFeedbackSheet = false
            onBack()
          },
          icon = Icons.Default.CheckCircle,
          modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp).testTag("btn_salvar_feedback_massagem")
        )
      }
    }
  }
}
