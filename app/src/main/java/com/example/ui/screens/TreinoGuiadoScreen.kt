package com.example.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.GlacierButton
import com.example.ui.components.GlacierCard
import com.example.ui.components.GlacierPill
import com.example.ui.theme.GlacierBackground
import com.example.ui.theme.GlacierError
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

enum class ExercisePhase(val label: String, val seconds: Int, val color: Color, val cue: String) {
  CONTRAIR(
    "FASE ATUAL: CONTRAIR (3s)",
    3,
    GlacierTertiary,
    "Comece a elevar lentamente a musculatura interna enquanto expira."
  ),
  SUSTENTAR(
    "FASE ATUAL: SUSTENTAR (5s)",
    5,
    GlacierPrimary,
    "Suba o assoalho pélvico como se fechasse um zíper interno e segure sem prender a respiração."
  ),
  RELAXAR(
    "FASE ATUAL: RELAXAR (10s)",
    10,
    GlacierSecondary,
    "Solte o assoalho pélvico completamente. Respire fundo e sinta a musculatura expandir sem esforço."
  )
}

@Composable
fun TreinoGuiadoScreen(
  onFinishWorkout: () -> Unit,
  onExit: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var isPlaying by remember { mutableStateOf(true) }
  var isMuted by remember { mutableStateOf(false) }
  var currentPhase by remember { mutableStateOf(ExercisePhase.SUSTENTAR) }
  var secondsLeft by remember { mutableIntStateOf(currentPhase.seconds) }
  var repetition by remember { mutableIntStateOf(3) }
  var series by remember { mutableIntStateOf(2) }
  var showPosturalVideoModal by remember { mutableStateOf(false) }
  var showExitConfirmDialog by remember { mutableStateOf(false) }

  // Haptic feedback trigger
  fun triggerVibration() {
    try {
      val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
      if (vibrator != null && vibrator.hasVibrator()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          vibrator.vibrate(VibrationEffect.createOneShot(120, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
          @Suppress("DEPRECATION")
          vibrator.vibrate(120)
        }
      }
    } catch (_: Exception) {}
  }

  // Timer loop
  LaunchedEffect(isPlaying, currentPhase, secondsLeft) {
    if (isPlaying) {
      delay(1000L)
      if (secondsLeft > 1) {
        secondsLeft -= 1
      } else {
        // Switch phase
        triggerVibration()
        when (currentPhase) {
          ExercisePhase.CONTRAIR -> {
            currentPhase = ExercisePhase.SUSTENTAR
            secondsLeft = ExercisePhase.SUSTENTAR.seconds
          }
          ExercisePhase.SUSTENTAR -> {
            currentPhase = ExercisePhase.RELAXAR
            secondsLeft = ExercisePhase.RELAXAR.seconds
          }
          ExercisePhase.RELAXAR -> {
            if (repetition < 4) {
              repetition += 1
            } else {
              repetition = 1
              if (series < 3) series += 1
            }
            currentPhase = ExercisePhase.CONTRAIR
            secondsLeft = ExercisePhase.CONTRAIR.seconds
          }
        }
      }
    }
  }

  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Top Session Navigation Bar
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("treino_top_nav"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.8f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          GlacierPill(
            text = "Sessão Guiada Ativa",
            leadingDot = true,
            containerColor = GlacierPrimary.copy(alpha = 0.15f),
            contentColor = GlacierPrimary
          )
          Text(
            text = "45% Concluído",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierOnSurface
          )
        }

        // Global Progress Bar
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(9999.dp))
            .background(GlacierSurfaceContainerHighest)
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth(0.45f)
              .height(6.dp)
              .clip(RoundedCornerShape(9999.dp))
              .background(Brush.horizontalGradient(listOf(GlacierSecondary, GlacierPrimary)))
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "EXERCÍCIO 2 DE 4",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierOnPrimaryContainer,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(GlacierPrimaryContainer)
                  .padding(horizontal = 5.dp, vertical = 2.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Sustentação Firme (Fibras Lentas)",
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant
              )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Treino do Assoalho Pélvico + Respiração",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
          }

          Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(GlacierSurfaceContainerHigh)
                .clickable { isMuted = !isMuted },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                contentDescription = "Áudio",
                tint = if (isMuted) GlacierError else GlacierPrimary,
                modifier = Modifier.size(18.dp)
              )
            }
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(GlacierSurfaceContainerHigh)
                .clickable { showExitConfirmDialog = true },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Sair",
                tint = GlacierOnSurfaceVariant,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    }

    // 2. Central Visual Biofeedback Orbital & Timer Engine
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("orbital_biofeedback_card"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.65f)
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Phase Pill
        GlacierPill(
          text = currentPhase.label,
          leadingDot = true,
          containerColor = currentPhase.color.copy(alpha = 0.2f),
          contentColor = currentPhase.color
        )

        // Orbital Gauge & Countdown
        Box(
          modifier = Modifier.size(200.dp),
          contentAlignment = Alignment.Center
        ) {
          val progressRatio = secondsLeft.toFloat() / currentPhase.seconds.toFloat()
          val strokeColor = currentPhase.color

          Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 14.dp.toPx()
            val diameter = size.minDimension - strokeWidth
            val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)

            // Background Track
            drawArc(
              color = GlacierSurfaceContainerHighest.copy(alpha = 0.5f),
              startAngle = -90f,
              sweepAngle = 360f,
              useCenter = false,
              topLeft = topLeft,
              size = androidx.compose.ui.geometry.Size(diameter, diameter),
              style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Dynamic Progress
            drawArc(
              color = strokeColor,
              startAngle = -90f,
              sweepAngle = 360f * progressRatio,
              useCenter = false,
              topLeft = topLeft,
              size = androidx.compose.ui.geometry.Size(diameter, diameter),
              style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
          }

          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Text(
              text = "TEMPO",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp,
              color = GlacierOnSurfaceVariant
            )
            Text(
              text = "00:${if (secondsLeft < 10) "0$secondsLeft" else "$secondsLeft"}",
              fontSize = 38.sp,
              fontWeight = FontWeight.ExtraBold,
              color = GlacierOnSurface
            )
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Default.Vibration,
                contentDescription = null,
                tint = currentPhase.color,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = when (currentPhase) {
                  ExercisePhase.SUSTENTAR -> "Sustentando"
                  ExercisePhase.CONTRAIR -> "Subindo"
                  ExercisePhase.RELAXAR -> "Descansando"
                },
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = currentPhase.color
              )
            }
          }
        }

        // Tactile / Proprioceptive Cue
        Text(
          text = currentPhase.cue,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium,
          color = GlacierOnSurface,
          textAlign = TextAlign.Center,
          lineHeight = 18.sp,
          modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Waveform / sEMG Canvas Simulation
        Column(
          modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Sensors,
                contentDescription = null,
                tint = GlacierPrimary,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Biofeedback Muscular",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = GlacierOnSurfaceVariant
              )
            }
            Text(
              text = "Tensão Alvo: 80%",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierPrimary
            )
          }

          // Dynamic wave drawing
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierSurfaceContainerLowest)
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val w = size.width
              val h = size.height

              val wavePath = Path().apply {
                moveTo(0f, h * 0.75f)
                cubicTo(w * 0.2f, h * 0.7f, w * 0.3f, h * 0.25f, w * 0.5f, h * 0.2f)
                cubicTo(w * 0.7f, h * 0.2f, w * 0.8f, h * 0.7f, w, h * 0.75f)
              }

              // Fill below
              val fillPath = Path().apply {
                addPath(wavePath)
                lineTo(w, h)
                lineTo(0f, h)
                close()
              }

              drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                  listOf(GlacierPrimary.copy(alpha = 0.45f), GlacierPrimary.copy(alpha = 0.05f))
                )
              )

              drawPath(
                path = wavePath,
                color = GlacierPrimary,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
              )
            }

            Row(
              modifier = Modifier.align(Alignment.TopEnd).padding(6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(GlacierSecondary)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "ESTÁVEL",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierSecondary,
                letterSpacing = 0.5.sp
              )
            }
          }
        }
      }
    }

    // 3. Realtime Session Metric Bento
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(12.dp))
          .background(GlacierSurfaceContainer.copy(alpha = 0.8f))
          .padding(10.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(text = "REPETIÇÃO", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
          Text(text = "$repetition / 4", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          Text(text = "+1 para pausa", fontSize = 9.sp, color = GlacierPrimary)
        }
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(12.dp))
          .background(GlacierSurfaceContainer.copy(alpha = 0.8f))
          .padding(10.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(text = "SÉRIE", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
          Text(text = "$series / 3", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          Text(text = "Fibras tipo I", fontSize = 9.sp, color = GlacierTertiary)
        }
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(12.dp))
          .background(GlacierSurfaceContainer.copy(alpha = 0.8f))
          .padding(10.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(text = "TEMPO TOTAL", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
          Text(text = "06:42", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          Text(text = "de 15:00 min", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
        }
      }
    }

    // 4. Clinical Voice Guidance (Dra. Luana)
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
            .size(38.dp)
            .clip(CircleShape)
            .border(1.dp, GlacierPrimary.copy(alpha = 0.4f), CircleShape)
        ) {
          AsyncImage(
            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCICHbSNx0-FD3vt9ZS9XuonDYJOrSBHv75pLHuGOTZ4-J1L52krGlHRf2lpKoqgYDFDrWD8LYkfwEDKK5WoUa124KVvqJQHSV2ERONHCUqlkRBxK7zJP51iedhPyxAjDtqYLTQbjqVqA6KP_2S2HpzZ_3uujhk-ZBpJCRYySFB3MlzAsPeOi74f1GrnM9mgRwVQPB1jRzthNZsVQflCP6pXvrrUAMa7XAKLXBclAWO490QMZN8lIc",
            contentDescription = "Dra. Luana Silva",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Dra. Luana Silva",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
            Text(
              text = "Fisioterapeuta Pélvica",
              fontSize = 10.sp,
              color = GlacierOnSurfaceVariant
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "🌬️ Solte o ar suavemente pela boca enquanto sustenta. Mantenha os glúteos e a parte interna das coxas totalmente relaxados!",
            fontSize = 11.sp,
            color = GlacierOnSurface,
            lineHeight = 15.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.TouchApp, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Feedback tátil programado via vibração",
              fontSize = 10.sp,
              color = GlacierOnSurfaceVariant
            )
          }
        }
      }
    }

    // 5. Player Controls Deck
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("player_controls_deck"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.85f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // -10s
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
              secondsLeft = (secondsLeft - 10).coerceAtLeast(1)
            }
          ) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(GlacierSurfaceContainerHigh),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Replay10, contentDescription = "-10s", tint = GlacierOnSurfaceVariant)
            }
            Text(text = "-10s", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          }

          // Center Play/Pause
          Box(
            modifier = Modifier
              .size(62.dp)
              .clip(CircleShape)
              .background(GlacierPrimary)
              .clickable { isPlaying = !isPlaying }
              .testTag("btn_play_pause"),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
              contentDescription = if (isPlaying) "Pausar" else "Continuar",
              tint = GlacierOnPrimary,
              modifier = Modifier.size(32.dp)
            )
          }

          // +10s
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {
              secondsLeft += 10
            }
          ) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(GlacierSurfaceContainerHigh),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Forward10, contentDescription = "+10s", tint = GlacierOnSurfaceVariant)
            }
            Text(text = "+10s", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          }
        }

        // Extra buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .height(38.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSurfaceContainerHigh)
              .clickable { showPosturalVideoModal = true },
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.SmartDisplay, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = "Ver Vídeo de Postura", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = GlacierOnSurface)
            }
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .height(38.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierPrimaryContainer)
              .clickable { onFinishWorkout() }
              .testTag("btn_concluir_treino"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = "Concluir Sessão", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierPrimary)
            }
          }
        }
      }
    }
  }

  // Modal: Vídeo de Postura
  if (showPosturalVideoModal) {
    AlertDialog(
      onDismissRequest = { showPosturalVideoModal = false },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "Guia de Execução Pélvica", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
        }
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(160.dp)
              .clip(RoundedCornerShape(12.dp))
          ) {
            AsyncImage(
              model = "https://lh3.googleusercontent.com/aida-public/AB6AXuDXqB4nmE0HNwX-85dH7VY-RmyRU5PpcddKpkapIreWsPlcGWSc9QcqQfmpOJUaLir3EReP5LOeX2kbWhBSrWgd7f7c9vvypWZ5hvBgy6YcyCJ7eJhgnmHMpWegv3nBrcXtC1mNieyh6V3wNvmITGt9yd1hftBwyEAJ4ZZ2i2zNwgpSUVcYwluFiqS3C6R4A-EXY-op1SNuAtiDwJBa_dTzgB2iJh65mkc7dlnF4MhOzRg-Zo2MPKg",
              contentDescription = "Postura",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
          }
          Text(
            text = "Lembre-se: não prenda a respiração. A contração do assoalho pélvico é sutil, ascendente e não deve envolver contração de glúteos ou adutores.",
            fontSize = 12.sp,
            color = GlacierOnSurfaceVariant,
            lineHeight = 16.sp
          )
        }
      },
      confirmButton = {
        TextButton(onClick = { showPosturalVideoModal = false }) {
          Text(text = "Entendi, Continuar Treino", color = GlacierPrimary, fontWeight = FontWeight.Bold)
        }
      },
      containerColor = GlacierSurfaceContainer
    )
  }

  // Confirmation dialog on exit
  if (showExitConfirmDialog) {
    AlertDialog(
      onDismissRequest = { showExitConfirmDialog = false },
      title = { Text(text = "Pausar Treino?", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface) },
      text = { Text(text = "Deseja salvar o progresso atual ou descartar a sessão?", fontSize = 12.sp, color = GlacierOnSurfaceVariant) },
      confirmButton = {
        TextButton(onClick = {
          showExitConfirmDialog = false
          onFinishWorkout()
        }) {
          Text(text = "Salvar e Concluir", color = GlacierPrimary, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = {
          showExitConfirmDialog = false
          onExit()
        }) {
          Text(text = "Sair", color = GlacierError)
        }
      },
      containerColor = GlacierSurfaceContainer
    )
  }
}
