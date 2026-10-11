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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SsidChart
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ClinicalFeedback
import com.example.data.PelviCareRepository
import com.example.ui.components.GlacierButton
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
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassSurface

@Composable
fun FeedbackTreinoScreen(
  onSaveAndProceed: () -> Unit,
  onGoHome: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedBorg by remember { mutableIntStateOf(4) }
  var selectedEvaRange by remember { mutableStateOf("0") }
  var apneaChecked by remember { mutableStateOf(true) }
  var gluteosRelaxedChecked by remember { mutableStateOf(true) }
  var zeroEscapesChecked by remember { mutableStateOf(true) }
  var hardToRelaxChecked by remember { mutableStateOf(false) }
  var noteToDoctor by remember {
    mutableStateOf("Senti mais facilidade na respiração do 3º exercício e consegui isolar bem a musculatura!")
  }
  var isSaved by remember { mutableStateOf(false) }

  val scrollState = rememberScrollState()

  val borgDescriptions = mapOf(
    0 to "Nível 0: Nenhum esforço perceptível, contração imperceptível.",
    1 to "Nível 1: Esforço extremamente leve, sustentação quase passiva.",
    2 to "Nível 2: Esforço muito leve, percepção muscular sutil.",
    3 to "Nível 3: Esforço leve, boa coordenação com a respiração.",
    4 to "Nível 4: Esforço moderado, sustentação firme sem fadiga excessiva ou perda de tônus.",
    5 to "Nível 5: Cansaço leve a moderado ao fim das séries.",
    6 to "Nível 6: Esforço ligeiramente pesado, leve perda de sustentação final.",
    7 to "Nível 7: Esforço pesado, exigiu bastante concentração.",
    8 to "Nível 8: Muito pesado, presença de tremores leves perineais.",
    9 to "Nível 9: Quase exaustão muscular local.",
    10 to "Nível 10: Esforço máximo insustentável, fadiga aguda."
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Celebration Header Card
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("celebration_card"),
      backgroundColor = GlassSurface
    ) {
      Column(
        modifier = Modifier.padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Trophy Badge
        Box(
          modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(GlacierPrimary.copy(alpha = 0.15f))
            .border(1.dp, GlacierPrimary.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.WorkspacePremium,
            contentDescription = null,
            tint = GlacierPrimary,
            modifier = Modifier.size(30.dp)
          )
        }

        GlacierPill(
          text = "Sessão 2 de 2 de Hoje • Meta Diária 100%",
          leadingIcon = Icons.Default.Verified,
          containerColor = GlacierPrimary.copy(alpha = 0.12f),
          contentColor = GlacierPrimary
        )

        Text(
          text = "Parabéns, Camila! 🎉",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierOnSurface,
          textAlign = TextAlign.Center
        )

        Text(
          text = "Treino do Assoalho Pélvico + Respiração Diafragmática concluído com sucesso e estabilidade perineal!",
          fontSize = 12.sp,
          color = GlacierOnSurfaceVariant,
          textAlign = TextAlign.Center,
          lineHeight = 16.sp
        )

        // Metrics Grid (2x2)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(GlacierSurfaceContainerHigh.copy(alpha = 0.7f))
              .padding(10.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierPrimary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
              }
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(text = "Tempo Total", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
                Text(text = "15 min", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              }
            }
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(GlacierSurfaceContainerHigh.copy(alpha = 0.7f))
              .padding(10.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierTertiary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(imageVector = Icons.Default.SelfImprovement, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(16.dp))
              }
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(text = "Contrações", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
                Text(text = "16 repetições", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              }
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
              .clip(RoundedCornerShape(12.dp))
              .background(GlacierSurfaceContainerHigh.copy(alpha = 0.7f))
              .padding(10.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierPrimary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(imageVector = Icons.Default.SsidChart, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
              }
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(text = "Adesão Alvo", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
                Text(text = "92% Estável", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
              }
            }
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(GlacierSurfaceContainerHigh.copy(alpha = 0.7f))
              .padding(10.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierSecondary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(imageVector = Icons.Default.LocalFireDepartment, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(16.dp))
              }
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(text = "Sequência", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
                Text(text = "5 dias seguidos", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              }
            }
          }
        }
      }
    }

    // 2. Percepção de Esforço (Borg 0-10)
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("borg_scale_card"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Percepção de Esforço (Borg)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          }
          GlacierPill(text = "Escala 0–10", containerColor = GlacierPrimary.copy(alpha = 0.12f), contentColor = GlacierPrimary)
        }

        Text(text = "Qual foi o nível de cansaço ou esforço do assoalho pélvico?", fontSize = 11.sp, color = GlacierOnSurfaceVariant)

        // 0 to 10 Numbers Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
          (0..10).forEach { num ->
            val isSelected = selectedBorg == num
            Box(
              modifier = Modifier
                .weight(1f)
                .height(34.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isSelected) GlacierPrimary else GlacierSurfaceContainerHigh)
                .clickable { selectedBorg = num },
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = num.toString(),
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) GlacierOnPrimary else GlacierOnSurfaceVariant
              )
            }
          }
        }

        // Active description banner
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerHigh)
            .padding(10.dp)
        ) {
          Row(verticalAlignment = Alignment.Top) {
            Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = borgDescriptions[selectedBorg] ?: "",
              fontSize = 11.sp,
              color = GlacierOnSurface,
              lineHeight = 15.sp
            )
          }
        }
      }
    }

    // 3. Avaliação de Desconforto (EVA)
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("eva_scale_card"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Healing, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Avaliação de Desconforto (EVA)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          }
          GlacierPill(text = "Visual Analógica", containerColor = GlacierSecondaryContainer, contentColor = GlacierSecondary)
        }

        Text(text = "Sentiu dor, ardência ou queimação involuntária durante os exercícios?", fontSize = 11.sp, color = GlacierOnSurfaceVariant)

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // 0: Nenhuma dor
          val sel0 = selectedEvaRange == "0"
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(if (sel0) GlacierPrimary.copy(alpha = 0.2f) else GlacierSurfaceContainerHigh)
              .border(1.dp, if (sel0) GlacierPrimary else GlassBorder, RoundedCornerShape(12.dp))
              .clickable { selectedEvaRange = "0" }
              .padding(8.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(imageVector = Icons.Default.SentimentVerySatisfied, contentDescription = null, tint = if (sel0) GlacierPrimary else GlacierOnSurfaceVariant, modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.height(4.dp))
              Text(text = "0 • Nenhuma dor", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "Apenas fadiga normal", fontSize = 9.sp, color = GlacierOnSurfaceVariant, textAlign = TextAlign.Center)
            }
          }

          // 1-4: Leve
          val sel1 = selectedEvaRange == "1-4"
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(if (sel1) GlacierSecondary.copy(alpha = 0.2f) else GlacierSurfaceContainerHigh)
              .border(1.dp, if (sel1) GlacierSecondary else GlassBorder, RoundedCornerShape(12.dp))
              .clickable { selectedEvaRange = "1-4" }
              .padding(8.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(imageVector = Icons.Default.SentimentNeutral, contentDescription = null, tint = if (sel1) GlacierSecondary else GlacierOnSurfaceVariant, modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.height(4.dp))
              Text(text = "1–4 • Leve", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "Desconforto sutil", fontSize = 9.sp, color = GlacierOnSurfaceVariant, textAlign = TextAlign.Center)
            }
          }

          // 5-10: Incômodo
          val sel2 = selectedEvaRange == "5-10"
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(if (sel2) GlacierError.copy(alpha = 0.2f) else GlacierSurfaceContainerHigh)
              .border(1.dp, if (sel2) GlacierError else GlassBorder, RoundedCornerShape(12.dp))
              .clickable { selectedEvaRange = "5-10" }
              .padding(8.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(imageVector = Icons.Default.SentimentDissatisfied, contentDescription = null, tint = if (sel2) GlacierError else GlacierOnSurfaceVariant, modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.height(4.dp))
              Text(text = "5–10 • Incômodo", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "Avisa fisioterapeuta", fontSize = 9.sp, color = GlacierOnSurfaceVariant, textAlign = TextAlign.Center)
            }
          }
        }
      }
    }

    // 4. Checklist: Qualidade da Contração Perineal
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.TaskAlt, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Qualidade da Contração Perineal", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
        }

        Text(text = "Marque os pontos observados durante a respiração e elevação:", fontSize = 11.sp, color = GlacierOnSurfaceVariant)

        listOf(
          Triple("Sustentei sem prender a respiração (apneia zero)", apneaChecked) { apneaChecked = !apneaChecked },
          Triple("Glúteos e abdômen compensatório relaxados", gluteosRelaxedChecked) { gluteosRelaxedChecked = !gluteosRelaxedChecked },
          Triple("Zero escapes urinários durante o esforço", zeroEscapesChecked) { zeroEscapesChecked = !zeroEscapesChecked },
          Triple("Dificuldade de relaxar na última repetição", hardToRelaxChecked) { hardToRelaxChecked = !hardToRelaxChecked }
        ).forEach { (text, isChecked, onToggle) ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierSurfaceContainerHigh.copy(alpha = 0.6f))
              .clickable { onToggle() }
              .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(if (isChecked) GlacierPrimary else GlacierSurfaceContainerHighest),
              contentAlignment = Alignment.Center
            ) {
              if (isChecked) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = GlacierOnPrimary, modifier = Modifier.size(14.dp))
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, fontSize = 11.sp, color = GlacierOnSurface)
          }
        }
      }
    }

    // 5. Recado para Dra. Luana Silva
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.MedicalServices, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Recado para Dra. Luana Silva", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          }
          Text(text = "CREFITO-3", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
        }

        OutlinedTextField(
          value = noteToDoctor,
          onValueChange = { noteToDoctor = it },
          modifier = Modifier.fillMaxWidth().height(80.dp),
          textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp, color = GlacierOnSurface),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GlacierPrimary,
            unfocusedBorderColor = GlassBorder,
            focusedContainerColor = GlacierSurfaceContainerHigh,
            unfocusedContainerColor = GlacierSurfaceContainerHigh
          ),
          shape = RoundedCornerShape(10.dp),
          placeholder = { Text(text = "Deixe uma observação para sua fisioterapeuta...", fontSize = 11.sp, color = GlacierOnSurfaceVariant) }
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          Text(text = "Sincronização imediata no prontuário", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
        }
      }
    }

    // 6. Action Button
    GlacierButton(
      text = if (isSaved) "Sincronizado com Sucesso!" else "Salvar & Sincronizar com a Fisioterapeuta",
      onClick = {
        isSaved = true
        PelviCareRepository.addFeedback(
          ClinicalFeedback(
            id = System.currentTimeMillis().toString(),
            date = "Hoje, Agora",
            title = "Treino do Assoalho Pélvico + Respiração",
            sessionDuration = "15 min",
            borgScore = selectedBorg,
            evaScore = if (selectedEvaRange == "0") 0 else if (selectedEvaRange == "1-4") 2 else 6,
            patientMessage = "“$noteToDoctor”",
            doctorResponse = "Recebido! Excelente coordenação e adesão.",
            doctorResponseTime = "Agora",
            status = "Sincronizado"
          )
        )
        onSaveAndProceed()
      },
      icon = Icons.Default.CloudDone,
      modifier = Modifier.fillMaxWidth().testTag("btn_salvar_feedback")
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center
    ) {
      Text(
        text = "Voltar ao Início",
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = GlacierOnSurfaceVariant,
        modifier = Modifier.clickable { onGoHome() }.padding(8.dp)
      )
    }
  }
}
