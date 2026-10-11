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
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PregnantWoman
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.PelviCareRepository
import com.example.ui.components.GlacierCard
import com.example.ui.components.GlacierPill
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
import com.example.ui.theme.GlacierTertiaryContainer
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassSurface

@Composable
fun ProntuarioScreen(
  onExportPdf: () -> Unit,
  onNavigateTo34Weeks: () -> Unit,
  modifier: Modifier = Modifier
) {
  val profile by PelviCareRepository.patientProfile.collectAsState()
  val scrollState = rememberScrollState()
  var selectedTab by remember { mutableStateOf("AFA") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header Card: Dados da Paciente & Resumo Obstétrico
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("prontuario_header_card"),
      backgroundColor = GlassSurface
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .border(1.5.dp, GlacierPrimary.copy(alpha = 0.4f), CircleShape)
            ) {
              AsyncImage(
                model = profile.avatarUrl,
                contentDescription = profile.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
              Box(
                modifier = Modifier
                  .align(Alignment.BottomEnd)
                  .clip(RoundedCornerShape(9999.dp))
                  .background(GlacierPrimary)
                  .padding(horizontal = 4.dp, vertical = 1.dp)
              ) {
                Text(
                  text = "G${profile.gestationalWeeks}w",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnPrimary
                )
              }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = profile.name,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnSurface
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "${profile.age} anos",
                  fontSize = 11.sp,
                  color = GlacierOnSurfaceVariant,
                  modifier = Modifier
                    .clip(RoundedCornerShape(9999.dp))
                    .background(GlacierSurfaceContainerHigh)
                    .padding(horizontal = 6.dp, vertical = 1.dp)
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.PregnantWoman,
                  contentDescription = null,
                  tint = GlacierPrimary,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "G2P1 (1 Cesárea anterior) • ${profile.gestationalWeeks}ª sem.",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  color = GlacierPrimary
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "ID: ${profile.id} • Convênio: ${profile.healthPlan}",
                fontSize = 10.sp,
                color = GlacierOnSurfaceVariant
              )
            }
          }

          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSurfaceContainerHigh)
              .clickable { /* Edit Notes */ },
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.EditNote,
              contentDescription = "Editar",
              tint = GlacierPrimary,
              modifier = Modifier.size(20.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Queixa Principal
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerLow.copy(alpha = 0.8f))
            .padding(12.dp)
        ) {
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.MedicalServices,
                contentDescription = null,
                tint = GlacierTertiary,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Queixa Principal & Encaminhamento",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierTertiary
              )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = profile.primaryComplaint,
              fontSize = 11.sp,
              color = GlacierOnSurface,
              lineHeight = 16.sp
            )
          }
        }
      }
    }

    // 2. Abas de Navegação Clínicas
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      listOf(
        "AFA" to "Avaliação Funcional (AFA)",
        "HISTORICO" to "Histórico Gineco-Obstétrico",
        "SESSOES" to "Sessões Clínicas"
      ).forEach { (key, label) ->
        val isSelected = selectedTab == key
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) GlacierPrimary.copy(alpha = 0.2f) else GlacierSurfaceContainer.copy(alpha = 0.6f))
            .border(
              width = 1.dp,
              color = if (isSelected) GlacierPrimary.copy(alpha = 0.4f) else GlassBorder,
              shape = RoundedCornerShape(10.dp)
            )
            .clickable { selectedTab = key }
            .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
          Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) GlacierPrimary else GlacierOnSurfaceVariant
          )
        }
      }
    }

    // 3. Oxford Scale Card
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("oxford_scale_card"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
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
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierPrimary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Speed,
                contentDescription = null,
                tint = GlacierPrimary,
                modifier = Modifier.size(16.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Escala de Oxford Modificada",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
          }

          GlacierPill(
            text = "Grau 3 / 5",
            containerColor = GlacierPrimary.copy(alpha = 0.2f),
            contentColor = GlacierPrimary
          )
        }

        // 5-level segmented bar
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "0 - Nula", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            Text(text = "3 - Moderada", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            Text(text = "5 - Forte", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          }

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(9999.dp))
              .background(GlacierSurfaceContainerHigh),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
          ) {
            Box(modifier = Modifier.weight(1f).fillMaxSize().background(GlacierPrimary.copy(alpha = 0.6f)))
            Box(modifier = Modifier.weight(1f).fillMaxSize().background(GlacierPrimary.copy(alpha = 0.8f)))
            Box(modifier = Modifier.weight(1f).fillMaxSize().background(GlacierPrimary))
            Box(modifier = Modifier.weight(1f).fillMaxSize().background(GlacierSurfaceContainerHighest))
            Box(modifier = Modifier.weight(1f).fillMaxSize().background(GlacierSurfaceContainerHighest))
          }
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerLow)
            .padding(10.dp)
        ) {
          Row(verticalAlignment = Alignment.Top) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = GlacierPrimary,
              modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Contração moderada com elevação cranial visível da parede posterior vaginal, sem sustentação contra forte resistência digital manual.",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant,
              lineHeight = 15.sp
            )
          }
        }
      }
    }

    // 4. Protocolo P.E.R.F.E.C.T. Detalhado
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("perfect_protocol_card"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
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
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierTertiary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = null,
                tint = GlacierTertiary,
                modifier = Modifier.size(16.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Protocolo P.E.R.F.E.C.T.",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
          }

          Text(
            text = "Laycock (2001)",
            fontSize = 10.sp,
            color = GlacierTertiary,
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(GlacierTertiaryContainer.copy(alpha = 0.5f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }

        // PERFECT 4-box Grid
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // P
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSurfaceContainerHigh)
              .padding(10.dp)
          ) {
            Column {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = "P • Power", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurfaceVariant)
                Text(text = "Força", fontSize = 10.sp, color = GlacierPrimary)
              }
              Spacer(modifier = Modifier.height(4.dp))
              Row(verticalAlignment = Alignment.Bottom) {
                Text(text = "3", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Oxford (0-5)", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
              }
            }
          }

          // E
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSurfaceContainerHigh)
              .padding(10.dp)
          ) {
            Column {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = "E • Endurance", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurfaceVariant)
                Text(text = "Sustentação", fontSize = 10.sp, color = GlacierTertiary)
              }
              Spacer(modifier = Modifier.height(4.dp))
              Row(verticalAlignment = Alignment.Bottom) {
                Text(text = "4s", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GlacierTertiary)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Meta: 10s", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
              }
            }
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // R
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSurfaceContainerHigh)
              .padding(10.dp)
          ) {
            Column {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = "R • Repetitions", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurfaceVariant)
                Text(text = "Repetições", fontSize = 10.sp, color = GlacierSecondary)
              }
              Spacer(modifier = Modifier.height(4.dp))
              Row(verticalAlignment = Alignment.Bottom) {
                Text(text = "4", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GlacierSecondary)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "com platô 4s", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
              }
            }
          }

          // F
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSurfaceContainerHigh)
              .padding(10.dp)
          ) {
            Column {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = "F • Fast", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurfaceVariant)
                Text(text = "Fibras II", fontSize = 10.sp, color = GlacierPrimary)
              }
              Spacer(modifier = Modifier.height(4.dp))
              Row(verticalAlignment = Alignment.Bottom) {
                Text(text = "6", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "rápidas (10s)", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
              }
            }
          }
        }

        // ECT Row
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerLow)
            .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = null,
                tint = GlacierSecondary,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "ECT (Every Contraction Timed)",
                fontSize = 11.sp,
                color = GlacierOnSurface
              )
            }
            Text(
              text = "Registrado • 4 seg",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = GlacierSecondary
            )
          }
        }
      }
    }

    // 5. Tônus Muscular & Eletromiografia
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierPrimary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.TouchApp,
              contentDescription = null,
              tint = GlacierPrimary,
              modifier = Modifier.size(16.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Tônus Muscular & Eletromiografia",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierOnSurface
          )
        }

        // Palpation 1: Elevador do Ânus Direito
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerHigh.copy(alpha = 0.6f))
            .padding(10.dp)
        ) {
          Row(verticalAlignment = Alignment.Top) {
            Icon(
              imageVector = Icons.Default.CheckCircleOutline,
              contentDescription = null,
              tint = GlacierPrimary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "M. Elevador do Ânus (Direito)",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierOnSurface
              )
              Text(
                text = "Normotonia conservada, sem nódulos palpáveis ou desconforto reflexo.",
                fontSize = 10.sp,
                color = GlacierOnSurfaceVariant
              )
            }
          }
        }

        // Palpation 2: Obturador Interno Esquerdo
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerHigh.copy(alpha = 0.6f))
            .padding(10.dp)
        ) {
          Row(verticalAlignment = Alignment.Top) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = null,
              tint = GlacierError,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "M. Obturador Interno (Esquerdo)",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierError
              )
              Text(
                text = "Ponto-gatilho miofascial doloroso ativo (EVA 6/10), irradiando para ísquio e períneo posterior.",
                fontSize = 10.sp,
                color = GlacierOnSurfaceVariant
              )
            }
          }
        }

        // EMG Repouso
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerLow)
            .padding(10.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Favorite,
                  contentDescription = null,
                  tint = GlacierSecondary,
                  modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "EMG de Superfície (Repouso)",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = GlacierOnSurface
                )
              }
              Text(
                text = "2.1 µV",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierSecondary
              )
            }

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(9999.dp))
                .background(GlacierSurfaceContainerHighest)
            ) {
              Box(
                modifier = Modifier
                  .fillMaxWidth(0.7f)
                  .height(6.dp)
                  .clip(RoundedCornerShape(9999.dp))
                  .background(GlacierSecondary)
              )
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Alvo Clínico: < 2.0 µV", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
              Text(text = "Hiperatividade basal leve", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
          }
        }
      }
    }

    // 6. Linha do Tempo de Conduta & Intervenções
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
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
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierTertiary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Assignment,
                contentDescription = null,
                tint = GlacierTertiary,
                modifier = Modifier.size(16.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Plano de Conduta Atual",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
          }

          Text(
            text = "4 prescrições ativas",
            fontSize = 11.sp,
            color = GlacierOnSurfaceVariant
          )
        }

        listOf(
          Triple("1", "Terapia Comportamental Miccional", "Diário miccional orientado, técnica de esvaziamento duplo e modulação hídrica após às 19h."),
          Triple("2", "Ativação do Transverso Abdominal & MAP", "Sinergia proprioceptiva com bola suíça em decúbito lateral e posicionamento adaptado para 32ª semana."),
          Triple("3", "Liberação Miofascial Intravaginal", "Desativação de ponto-gatilho em obturador interno E com técnica de pressão isquêmica suave (Thiele adaptado)."),
          Triple("4", "Treino Respiratório Diafragmático", "Coordenação do padrão expiratório prévio ao aumento de pressão intra-abdominal (The Knack).")
        ).forEach { (num, title, desc) ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
          ) {
            Box(
              modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(GlacierPrimary.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = num,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierPrimary
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierOnSurface
              )
              Text(
                text = desc,
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant,
                lineHeight = 15.sp
              )
            }
          }
        }
      }
    }

    // 7. Ações Clínicas Rápidas
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .height(44.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(GlacierSurfaceContainerHigh)
          .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
          .clickable { onExportPdf() }
          .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.PictureAsPdf,
            contentDescription = null,
            tint = GlacierSecondary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Exportar PDF",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = GlacierOnSurface
          )
        }
      }

      Box(
        modifier = Modifier
          .weight(1f)
          .height(44.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(GlacierPrimary)
          .clickable { onNavigateTo34Weeks() }
          .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.AddTask,
            contentDescription = null,
            tint = GlacierOnPrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Iniciar Reavaliação",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = GlacierOnPrimary
          )
        }
      }
    }
  }
}
