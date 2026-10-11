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
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun Preparo34SemanasScreen(
  onStartPerinealMassage: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isTransitioned by PelviCareRepository.is34WeeksTransitioned.collectAsState()
  var showTransitionModal by remember { mutableStateOf(!isTransitioned) }
  var isAudioPlaying by remember { mutableStateOf(false) }

  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Celebratory Milestone Card
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("milestone_34weeks_card"),
      backgroundColor = GlassSurface
    ) {
      Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        GlacierPill(
          text = "MARCO CLÍNICO • 34ª SEMANA",
          leadingDot = true,
          containerColor = GlacierPrimary.copy(alpha = 0.15f),
          contentColor = GlacierPrimary
        )

        Text(
          text = "Bem-vinda à Fase de Preparo para o Parto!",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierOnSurface,
          lineHeight = 24.sp
        )

        Text(
          text = "Camila, seu corpo ingressa hoje na janela áurea. Nosso foco fisioterapêutico expande-se para relaxamento consciente, elasticidade tecidual e mobilidade dos estreitos da pelve.",
          fontSize = 12.sp,
          color = GlacierOnSurfaceVariant,
          lineHeight = 17.sp
        )

        // Timeline Stages
        Column(modifier = Modifier.padding(top = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "1º Trimestre", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
            Text(text = "Fortalecimento", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
            Text(text = "34ª Sem (Parto)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            Text(text = "Puerpério", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
          }

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(9999.dp))
              .background(GlacierSurfaceContainerHighest),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
          ) {
            Box(modifier = Modifier.weight(1f).fillMaxSize().background(GlacierSecondary.copy(alpha = 0.4f)))
            Box(modifier = Modifier.weight(1f).fillMaxSize().background(GlacierSecondary.copy(alpha = 0.6f)))
            Box(modifier = Modifier.weight(1f).fillMaxSize().background(GlacierPrimary))
            Box(modifier = Modifier.weight(1f).fillMaxSize().background(GlacierSurfaceContainerHigh))
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Fase 3 de 4 • Protocolo validado com Dra. Luana Silva", fontSize = 10.sp, color = GlacierPrimary)
          }
        }
      }
    }

    // 2. Audio Message from Physiotherapist
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
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
            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBDff9esxpTuD1PBIem8OKwC-eS8Bk995KSxzvE360AmsxcjlwteKqI3Wifz_jntnNIRHwvUUa9iuSbb2I1a8IuLURMExdejxA-BsMvpBqU-AaTxig0tLdJ3Qxcww-bFMSWqCFZzw4QzqUdkS-pQ1MQ-CQ3rmriI8IbD8-p8JLdJ98rICBXu0E1AFJuf29xhLpATxboWtqeTLmNXVlZR3PK8TR5ow58iAA-S2t27xXQokQG-S9hWJc",
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
            Column {
              Text(text = "Dra. Luana Silva", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "Fisioterapeuta Pélvica • Crefito-3", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
            Text(text = "1:45", fontSize = 10.sp, color = GlacierPrimary)
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(text = "“Como funcionará sua rotina com massagem e balonete...”", fontSize = 11.sp, color = GlacierOnSurfaceVariant)

          Row(
            modifier = Modifier.padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(GlacierPrimary)
                .clickable { isAudioPlaying = !isAudioPlaying },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = if (isAudioPlaying) Icons.Default.Check else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = GlacierOnPrimary,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Mock sound bars
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
              listOf(8, 14, 18, 12, 6, 16, 20, 10, 14, 8).forEach { barH ->
                Box(
                  modifier = Modifier
                    .width(3.dp)
                    .height(barH.dp)
                    .clip(RoundedCornerShape(9999.dp))
                    .background(GlacierPrimary.copy(alpha = if (isAudioPlaying) 0.9f else 0.4f))
                )
              }
            }
          }
        }
      }
    }

    // 3. Os 4 Pilares do Novo Protocolo
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "OS 4 PILARES DO NOVO PROTOCOLO", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
        Text(text = "34ª até 40ª semana", fontSize = 10.sp, color = GlacierSecondary)
      }

      // Pillar 1: Massagem Perineal (Clickable to start!)
      GlacierCard(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onStartPerinealMassage() }
          .testTag("pillar_massagem_perineal"),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.8f),
        borderColor = GlacierPrimary.copy(alpha = 0.4f)
      ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierPrimary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(20.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column(modifier = Modifier.weight(1f)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Massagem Perineal Orientada", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(
                text = "Diário • 5-10 min",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierPrimary,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(GlacierPrimary.copy(alpha = 0.15f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = "Alongamento elástico do introito vaginal com movimentos em 'U' e pressão constante, reduzindo a incidência de dor expulsiva e lacerações severas.",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant,
              lineHeight = 15.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = "Abrir Passo a Passo Guiado", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierPrimary)
              Spacer(modifier = Modifier.width(4.dp))
              Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(14.dp))
            }
          }
        }
      }

      // Pillar 2: Treino com Balonete (Epi-No)
      GlacierCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.6f)
      ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierTertiary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.Air, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(20.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column(modifier = Modifier.weight(1f)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Treino com Balonete (Epi-No)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(
                text = "3x por semana",
                fontSize = 9.sp,
                color = GlacierTertiary,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(GlacierTertiaryContainer.copy(alpha = 0.5f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = "Insuflação milimétrica progressiva com biofeedback visual. Foco na expiração sem bloqueio glótico e na descida controlada sem expulsão forçada.",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant,
              lineHeight = 15.sp
            )
          }
        }
      }

      // Pillar 3: Respiração & Descida Fetal
      GlacierCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.6f)
      ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierSecondary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.SelfImprovement, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(20.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column(modifier = Modifier.weight(1f)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Respiração & Descida Fetal", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(
                text = "Coordenação",
                fontSize = 9.sp,
                color = GlacierSecondary,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(GlacierSecondaryContainer.copy(alpha = 0.5f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = "Sinergia entre diafragma e relaxamento do assoalho pélvico em posições verticais (quatro apoios, agachamento assistido e bola de parto).",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant,
              lineHeight = 15.sp
            )
          }
        }
      }

      // Pillar 4: Mobilidade da Bacia
      GlacierCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.6f)
      ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(GlacierPrimary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.AccessibilityNew, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(20.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column(modifier = Modifier.weight(1f)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Mobilidade & Liberação da Bacia", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(
                text = "Soltura Lombo-Sacra",
                fontSize = 9.sp,
                color = GlacierPrimary,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(GlacierPrimary.copy(alpha = 0.15f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = "Movimentos de báscula pélvica, rotações assimétricas e abertura de diâmetros ósseos para favorecer a rotação interna da cabeça fetal.",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant,
              lineHeight = 15.sp
            )
          }
        }
      }
    }

    // 4. Kit de Preparação Perineal Checklist
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.Checklist, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "KIT DE PREPARAÇÃO PERINEAL", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
        }
        Text(text = "3 de 4 itens prontos", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
      }

      GlacierCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          listOf(
            Triple("Óleo vegetal 100% puro", "Amêndoas doces / Coco extravirgem", "Pronto"),
            Triple("Balonete Epi-No / Biofeedback", "Calibrado pela Dra. Luana", "Liberado"),
            Triple("Bola Suíça (65cm)", "Calibrada na altura do quadril", "Em casa"),
            Triple("Espelho com suporte de apoio", "Facilita visualização inicial", "Opcional")
          ).forEach { (item, desc, status) ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(if (status != "Opcional") GlacierPrimary.copy(alpha = 0.2f) else GlacierSurfaceContainerHighest),
                  contentAlignment = Alignment.Center
                ) {
                  if (status != "Opcional") {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(14.dp))
                  }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(text = item, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
                  Text(text = desc, fontSize = 10.sp, color = GlacierOnSurfaceVariant)
                }
              }

              Text(
                text = status,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (status != "Opcional") GlacierPrimary else GlacierOnSurfaceVariant,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(if (status != "Opcional") GlacierPrimary.copy(alpha = 0.12f) else GlacierSurfaceContainerHighest)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
        }
      }
    }

    // 5. Critérios de Pausa (Atenção Médica)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(GlacierErrorContainer.copy(alpha = 0.35f))
        .border(1.dp, GlacierError.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.HealthAndSafety, contentDescription = null, tint = GlacierError, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "ATENÇÃO MÉDICA & CRITÉRIOS DE PAUSA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierError)
        }
        Text(
          text = "Suspenda imediatamente a massagem e o uso do balonete e entre em contato com sua obstetra ou com a Dra. Luana em caso de: perda de líquido amniótico, sangramento vaginal ativo ou contrações uterinas dolorosas e rítmicas.",
          fontSize = 11.sp,
          color = GlacierOnSurface,
          lineHeight = 15.sp
        )
      }
    }

    // 6. Action CTAs
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      GlacierButton(
        text = "Iniciar Meu Primeiro Treino da 34ª Semana",
        onClick = onStartPerinealMassage,
        icon = Icons.Default.PlayCircle,
        modifier = Modifier.fillMaxWidth().testTag("btn_iniciar_massagem_34w")
      )

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(GlacierSurfaceContainerHigh)
          .clickable { /* Agendar */ }
          .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Agendar Alinhamento Presencial com Dra. Luana", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = GlacierSecondary)
        }
      }
    }
  }

  // Dialog: Confirmar Nova Idade Gestacional (Transition Modal)
  if (showTransitionModal) {
    AlertDialog(
      onDismissRequest = { showTransitionModal = false },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.Update, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(text = "TRANSIÇÃO DE PROTOCOLO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            Text(text = "Confirmar Nova Idade Gestacional?", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          }
        }
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "Essa modificação recalcula marcos da pelve e altera suas prescrições ativas para o preparo de parto.",
            fontSize = 11.sp,
            color = GlacierOnSurfaceVariant
          )

          // Comparativo Antes vs Depois
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(GlacierSurfaceContainerLow)
                .padding(8.dp)
            ) {
              Column {
                Text(text = "ANTERIOR", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
                Text(text = "32 Sem. e 4 Dias", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
                Text(text = "3º Trimestre Regular", fontSize = 9.sp, color = GlacierSecondary)
              }
            }

            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(GlacierPrimaryContainer.copy(alpha = 0.5f))
                .padding(8.dp)
            ) {
              Column {
                Text(text = "NOVA IDADE", fontSize = 9.sp, color = GlacierPrimary)
                Text(text = "34 Sem. e 0 Dias", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
                Text(text = "Início Fase Parto", fontSize = 9.sp, color = GlacierPrimary)
              }
            }
          }

          Text(
            text = "• Preparo Perineal & Massagem: Desbloqueio do treino com Epi-No e liberação miofascial.\n• Recálculo de Carga: Foco em elasticidade.\n• Sincronização: Notificação enviada à Dra. Luana Silva.",
            fontSize = 10.sp,
            color = GlacierOnSurface,
            lineHeight = 14.sp
          )
        }
      },
      confirmButton = {
        TextButton(
          onClick = {
            PelviCareRepository.confirm34WeeksTransition()
            showTransitionModal = false
          }
        ) {
          Text(text = "Confirmar & Recalcular Protocolos", color = GlacierPrimary, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showTransitionModal = false }) {
          Text(text = "Cancelar", color = GlacierOnSurfaceVariant)
        }
      },
      containerColor = GlacierSurfaceContainer
    )
  }
}
