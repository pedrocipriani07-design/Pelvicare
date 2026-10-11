package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EventRepeat
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SportsVolleyball
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VerticalAlignTop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PelviCareRepository
import com.example.ui.components.GlacierButton
import com.example.ui.components.GlacierCard
import com.example.ui.components.GlacierLinearBar
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
import com.example.ui.theme.GlassSurface

@Composable
fun PrescricaoScreen(
  onStartWorkout: () -> Unit,
  onNavigateToHistory: () -> Unit,
  onNavigateToEditProfile: () -> Unit,
  modifier: Modifier = Modifier
) {
  val profile by PelviCareRepository.patientProfile.collectAsState()
  val scrollState = rememberScrollState()
  var isPlayingAudio by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Patient Header Card
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("prescricao_patient_card"),
      backgroundColor = GlassSurface
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          GlacierPill(
            text = "Em Andamento • Fase 2",
            leadingDot = true,
            containerColor = GlacierPrimary.copy(alpha = 0.15f),
            contentColor = GlacierPrimary
          )

          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(9999.dp))
              .background(GlacierSecondaryContainer.copy(alpha = 0.8f))
              .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.FitnessCenter,
              contentDescription = null,
              tint = GlacierPrimary,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Adesão Alta (85%)",
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              color = GlacierOnSurface
            )
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = profile.name,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Protocolo Fortalecimento e Coordenação Perineal",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant
            )
          }

          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(GlacierSurfaceContainerHigh),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.FitnessCenter,
              contentDescription = null,
              tint = GlacierPrimary,
              modifier = Modifier.size(22.dp)
            )
          }
        }

        // Schedule Grid Pills
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSurfaceContainerLow.copy(alpha = 0.7f))
              .padding(8.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.EventRepeat,
                contentDescription = null,
                tint = GlacierSecondary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Column {
                Text(
                  text = "FREQUÊNCIA",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = GlacierOnSurfaceVariant
                )
                Text(
                  text = "2x ao dia (M/N)",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = GlacierOnSurface
                )
              }
            }
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSurfaceContainerLow.copy(alpha = 0.7f))
              .padding(8.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Timelapse,
                contentDescription = null,
                tint = GlacierTertiary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Column {
                Text(
                  text = "DURAÇÃO TOTAL",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = GlacierOnSurfaceVariant
                )
                Text(
                  text = "~15 minutos",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = GlacierOnSurface
                )
              }
            }
          }
        }

        // Progress Bar
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Conclusão semanal dos ciclos",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant
            )
            Text(
              text = "12 de 14 sessões (85%)",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierPrimary
            )
          }
          GlacierLinearBar(progress = 0.85f)
        }
      }
    }

    // 2. Quick Actions Panel
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .height(42.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(GlacierPrimary)
          .clickable { /* WhatsApp share */ }
          .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Share,
            contentDescription = null,
            tint = GlacierOnPrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Enviar ao WhatsApp",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = GlacierOnPrimary
          )
        }
      }

      Box(
        modifier = Modifier
          .height(42.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(GlacierSurfaceContainerHigh)
          .clickable { onNavigateToEditProfile() }
          .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.EditNote,
            contentDescription = null,
            tint = GlacierPrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Editar",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = GlacierOnSurface
          )
        }
      }

      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(GlacierSurfaceContainerHigh)
          .clickable { onNavigateToHistory() },
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.History,
          contentDescription = "Histórico",
          tint = GlacierOnSurfaceVariant,
          modifier = Modifier.size(18.dp)
        )
      }
    }

    // Start Workout Launcher CTA
    GlacierButton(
      text = "Iniciar Treino Guiado Agora (15 min)",
      onClick = onStartWorkout,
      icon = Icons.Default.PlayArrow,
      modifier = Modifier.fillMaxWidth().testTag("btn_iniciar_treino_topo")
    )

    // 3. Módulos Prescritos (4 Atividades)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "MÓDULOS PRESCRITOS (4 ATIVIDADES)",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierOnSurfaceVariant,
          letterSpacing = 0.8.sp
        )
        Text(
          text = "Guia Visual",
          fontSize = 11.sp,
          color = GlacierTertiary,
          fontWeight = FontWeight.Medium
        )
      }

      // Module 1: Fibras Rápidas
      GlacierCard(
        modifier = Modifier.fillMaxWidth().clickable { onStartWorkout() },
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.6f)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(verticalAlignment = Alignment.Top) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GlacierPrimaryContainer.copy(alpha = 0.6f)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "01",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierPrimary
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "FIBRAS RÁPIDAS",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierPrimary,
                  letterSpacing = 0.5.sp
                )
                Text(
                  text = "8 contrações",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant,
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(GlacierSurfaceContainerHigh)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Ativação Fásica (Coordenação Reflexa)",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierOnSurface
              )
              Text(
                text = "Contrações rápidas submáximas focadas na elevação cranial do esfíncter. Manter sincronismo com relaxamento imediato e total.",
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant,
                lineHeight = 15.sp
              )
            }
          }

          // Cadence row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierSurfaceContainerLow.copy(alpha = 0.7f))
              .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(GlacierPrimary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(text = "DISPARO", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              Text(text = "1 seg contração", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
            }
            Column(modifier = Modifier.weight(1f)) {
              Text(text = "DESCOMPRESSÃO", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              Text(text = "2 seg relaxamento", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierSecondary)
            }
            Column(horizontalAlignment = Alignment.End) {
              Text(text = "Volume", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              Text(text = "1 série única", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            }
          }
        }
      }

      // Module 2: Fibras Lentas
      GlacierCard(
        modifier = Modifier.fillMaxWidth().clickable { onStartWorkout() },
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.6f)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(verticalAlignment = Alignment.Top) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GlacierSecondaryContainer.copy(alpha = 0.6f)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "02",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierSecondary
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "FIBRAS LENTAS",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierSecondary,
                  letterSpacing = 0.5.sp
                )
                Text(
                  text = "3 séries × 4 reps",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant,
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(GlacierSurfaceContainerHigh)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Sustentação Tônica (Resistência Pelvina)",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierOnSurface
              )
              Text(
                text = "Manutenção de tônus isométrico estável. Respiração fluida contínua sem bloqueio glótico e sem Valsalva.",
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant,
                lineHeight = 15.sp
              )
            }
          }

          // Cadence row
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierSurfaceContainerLow.copy(alpha = 0.7f))
              .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(GlacierSecondary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(text = "PLATÔ ISOMÉTRICO", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              Text(text = "5 seg sustentados", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
            }
            Column(modifier = Modifier.weight(1f)) {
              Text(text = "DESCANSO PLENO", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              Text(text = "10 seg consciente", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierSecondary)
            }
            Column(horizontalAlignment = Alignment.End) {
              Text(text = "Intervalo Séries", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              Text(text = "45 seg", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            }
          }
        }
      }

      // Module 3: Coordenação Diafragma-Períneo
      GlacierCard(
        modifier = Modifier.fillMaxWidth().clickable { onStartWorkout() },
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.6f)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(verticalAlignment = Alignment.Top) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GlacierTertiaryContainer.copy(alpha = 0.6f)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "03",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierTertiary
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "PISTÃO TORACO-ABDOMINAL",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierTertiary,
                  letterSpacing = 0.5.sp
                )
                Text(
                  text = "10 ciclos",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant,
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(GlacierSurfaceContainerHigh)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Coordenação Diafragma-Períneo",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierOnSurface
              )
              Text(
                text = "Inspiração nasal ampla com expansão costal baixa tridimensional; expiração oral suave com ativação cefálica simultânea do assoalho.",
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant,
                lineHeight = 15.sp
              )
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierSurfaceContainerLow.copy(alpha = 0.7f))
                .padding(8.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Air, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                  Text(text = "INSPIRAR", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
                  Text(text = "Relaxa e expande", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = GlacierOnSurface)
                }
              }
            }

            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierSurfaceContainerLow.copy(alpha = 0.7f))
                .padding(8.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.VerticalAlignTop, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                  Text(text = "EXPIRAR", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
                  Text(text = "Elevação cranial", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = GlacierOnSurface)
                }
              }
            }
          }
        }
      }

      // Module 4: Mobilidade na Bola Suíça
      GlacierCard(
        modifier = Modifier.fillMaxWidth().clickable { onStartWorkout() },
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.6f)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(verticalAlignment = Alignment.Top) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GlacierSurfaceContainerHighest),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "04",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierOnSurface
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "CINESIOTERAPIA ATIVA",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierSecondary,
                  letterSpacing = 0.5.sp
                )
                Text(
                  text = "5 minutos",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant,
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(GlacierSurfaceContainerHigh)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Mobilidade Pélvica na Bola Suíça",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierOnSurface
              )
              Text(
                text = "Círculos pélvicos nos dois sentidos horários e anti-horários, intercalando com básculas anteroposteriores suaves para ganho proprioceptivo.",
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant,
                lineHeight = 15.sp
              )
            }
          }

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierSurfaceContainerLow.copy(alpha = 0.7f))
              .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.SportsVolleyball, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = "Báscula + Circunvoluções", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = GlacierOnSurface)
            }
            Text(text = "Postura Sentada", fontSize = 11.sp, color = GlacierSecondary)
          }
        }
      }
    }

    // 4. Orientações e Contraindicações Clínicas
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(GlacierErrorContainer.copy(alpha = 0.35f))
        .border(1.dp, GlacierError.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = GlacierError,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "ORIENTAÇÕES E CONTRAINDICAÇÕES CLÍNICAS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierError
          )
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainer.copy(alpha = 0.7f))
            .padding(10.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.Top) {
              Icon(imageVector = Icons.Default.Block, contentDescription = null, tint = GlacierError, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Apneia Zero: Nunca prenda a respiração. A expiração deve acompanhar o esforço muscular.",
                fontSize = 11.sp,
                color = GlacierOnSurface
              )
            }

            Row(verticalAlignment = Alignment.Top) {
              Icon(imageVector = Icons.Default.Cancel, contentDescription = null, tint = GlacierError, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Sem Compensações: Evite a cocontração de glúteos, abdômen reto superior e adutores de coxa durante o TMAP.",
                fontSize = 11.sp,
                color = GlacierOnSurface
              )
            }

            Row(verticalAlignment = Alignment.Top) {
              Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Interrompa em caso de fadiga perineal excessiva, desconforto pélvico agudo ou perda de padrão motor.",
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant
              )
            }
          }
        }
      }
    }

    // 5. Dica do Fisioterapeuta / Áudio
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.6f)
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(GlacierPrimary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.SelfImprovement,
              contentDescription = null,
              tint = GlacierPrimary,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Dica da Fisioterapeuta",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
            Text(
              text = "Realize as séries da manhã em decúbito dorsal e à noite sentada.",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant
            )
          }
        }

        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(if (isPlayingAudio) GlacierPrimary else GlacierPrimary.copy(alpha = 0.2f))
            .clickable { isPlayingAudio = !isPlayingAudio },
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "Áudio",
            tint = if (isPlayingAudio) GlacierOnPrimary else GlacierPrimary,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}
