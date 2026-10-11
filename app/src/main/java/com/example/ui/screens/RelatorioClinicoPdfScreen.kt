package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FitScreen
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SsidChart
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PelviCareRepository
import com.example.ui.components.GlacierButton
import com.example.ui.components.GlacierCard
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
import com.example.ui.theme.GlacierSurfaceContainerLowest
import com.example.ui.theme.GlacierTertiary
import com.example.ui.theme.GlacierTertiaryContainer
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassSurface

@Composable
fun RelatorioClinicoPdfScreen(
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val profile by PelviCareRepository.patientProfile.collectAsState()
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  var pageNumber by remember { mutableIntStateOf(1) }
  var zoomPercent by remember { mutableIntStateOf(100) }

  fun sharePdfIntent(dest: String) {
    try {
      val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Relatório Clínico de Fisioterapia Pélvica - Camila Mendonça (32ª-34ª Sem). Assinado digitalmente pela Dra. Luana Silva (CREFITO-3).")
        type = "text/plain"
      }
      context.startActivity(Intent.createChooser(sendIntent, "Compartilhar para $dest"))
    } catch (_: Exception) {
      Toast.makeText(context, "Compartilhado com sucesso!", Toast.LENGTH_SHORT).show()
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 14.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Top Navigation & Action Bar
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
          Text(text = "Voltar", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = GlacierOnSurface)
        }
      }

      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Relatório Clínico de Evolução", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
        GlacierPill(
          text = "ICP-Brasil • Assinado",
          leadingIcon = Icons.Default.Verified,
          containerColor = GlacierPrimary.copy(alpha = 0.15f),
          contentColor = GlacierPrimary
        )
      }

      Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerHigh)
            .clickable { sharePdfIntent("Todos") },
          contentAlignment = Alignment.Center
        ) {
          Icon(imageVector = Icons.Default.Share, contentDescription = "Compartilhar", tint = GlacierPrimary, modifier = Modifier.size(16.dp))
        }
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerHigh)
            .clickable {
              Toast.makeText(context, "Download do PDF iniciado (1.4 MB)", Toast.LENGTH_SHORT).show()
            },
          contentAlignment = Alignment.Center
        ) {
          Icon(imageVector = Icons.Default.Download, contentDescription = "Download", tint = GlacierPrimary, modifier = Modifier.size(16.dp))
        }
      }
    }

    // 2. PDF Viewer Toolbar (Pagination & Zoom)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(GlacierSurfaceContainer.copy(alpha = 0.8f))
        .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(26.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(GlacierSurfaceContainerHigh)
              .clickable { if (pageNumber > 1) pageNumber -= 1 },
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null, tint = GlacierOnSurfaceVariant, modifier = Modifier.size(16.dp))
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Pág $pageNumber de 3", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
          Spacer(modifier = Modifier.width(6.dp))
          Box(
            modifier = Modifier
              .size(26.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(GlacierSurfaceContainerHigh)
              .clickable { if (pageNumber < 3) pageNumber += 1 },
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = GlacierOnSurfaceVariant, modifier = Modifier.size(16.dp))
          }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(26.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(GlacierSurfaceContainerHigh)
              .clickable { zoomPercent = (zoomPercent - 10).coerceAtLeast(70) },
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = null, tint = GlacierOnSurfaceVariant, modifier = Modifier.size(16.dp))
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "$zoomPercent%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
          Spacer(modifier = Modifier.width(6.dp))
          Box(
            modifier = Modifier
              .size(26.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(GlacierSurfaceContainerHigh)
              .clickable { zoomPercent = (zoomPercent + 10).coerceAtMost(140) },
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = GlacierOnSurfaceVariant, modifier = Modifier.size(16.dp))
          }
        }
      }
    }

    // 3. Clinical Report Sheet Document
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("pdf_sheet_card"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.95f),
      borderColor = GlacierPrimary.copy(alpha = 0.3f)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Document Header
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerLow)
            .padding(10.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GlacierPrimary.copy(alpha = 0.15f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(imageVector = Icons.Default.MedicalServices, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(text = "PELVICARE HEALTH CLINIC", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
                  Text(text = "Fisioterapia Pélvica & Uroginecológica", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
                }
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(text = "AUTH: 7DF2-91CA", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
                Text(text = "18/03/2025", fontSize = 9.sp, color = GlacierPrimary)
              }
            }

            Text(
              text = "Dra. Luana Silva • CREFITO-3/284910-F (RQE 1204)",
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = GlacierOnSurface
            )
          }
        }

        // Identificação Clínica
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerHigh)
            .padding(10.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "IDENTIFICAÇÃO CLÍNICA", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
              Text(
                text = "Gestação de Baixo Risco",
                fontSize = 9.sp,
                color = GlacierTertiary,
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(GlacierTertiaryContainer.copy(alpha = 0.5f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text(text = "PACIENTE", fontSize = 8.sp, color = GlacierOnSurfaceVariant)
                Text(text = "${profile.name}, ${profile.age} anos", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
                Text(text = "Obstetrícia: G2P1 (1 Cesárea prévia)", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              }
              Column(horizontalAlignment = Alignment.End) {
                Text(text = "IDADE GESTACIONAL", fontSize = 8.sp, color = GlacierOnSurfaceVariant)
                Text(text = "${profile.gestationalWeeks}ª Semana (${profile.gestationalWeeks}s ${profile.gestationalDays}d)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
                Text(text = "Encam: Dr. Roberto Guimarães", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              }
            }

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(GlacierSurfaceContainerLowest)
                .padding(6.dp)
            ) {
              Text(
                text = "Diagnóstico Fisioterapêutico Inicial: Incontinência Urinária Mista (CID-10 R32) com predomínio de esforço e disfunção de tônus no MAP.",
                fontSize = 9.sp,
                color = GlacierOnSurfaceVariant,
                lineHeight = 13.sp
              )
            }
          }
        }

        // Evolução Funcional Muscular (P.E.R.F.E.C.T.)
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "Evolução Funcional Muscular (P.E.R.F.E.C.T.)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            Text(text = "Semana 1 → Semana 6", fontSize = 10.sp, color = GlacierPrimary)
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierSurfaceContainerLow)
                .padding(8.dp)
            ) {
              Column {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(text = "Escala de Oxford", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
                  Text(text = "+2 Níveis", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Grau 3+", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
                Text(text = "Contração perceptível contra resistência moderada e elevação visível.", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              }
            }

            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierSurfaceContainerLow)
                .padding(8.dp)
            ) {
              Column {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(text = "Sustentação (Endurance)", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
                  Text(text = "Meta: 6s", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = GlacierTertiary)
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "5.0s", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
                Text(text = "Sustentação tônica sem recrutamento acessório de glúteos ou adutores.", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              }
            }
          }
        }

        // Biofeedback EMG Chart in Document
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerLow)
            .padding(10.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Biofeedback Eletromiográfico (sEMG)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "Repouso: 1.8 µV (Normotonia)", fontSize = 8.sp, color = GlacierOnSurfaceVariant)
            }

            // Curve graphic
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
            ) {
              Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                val path = Path().apply {
                  moveTo(w * 0.05f, h * 0.85f)
                  cubicTo(w * 0.25f, h * 0.75f, w * 0.5f, h * 0.45f, w * 0.75f, h * 0.28f)
                  cubicTo(w * 0.85f, h * 0.2f, w * 0.92f, h * 0.15f, w * 0.95f, h * 0.12f)
                }

                drawPath(
                  path = path,
                  brush = Brush.horizontalGradient(listOf(GlacierSecondary, GlacierTertiary, GlacierPrimary)),
                  style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )

                drawCircle(color = GlacierPrimary, radius = 4.dp.toPx(), center = Offset(w * 0.95f, h * 0.12f))
              }
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Semana 1 (4.2 µV)", fontSize = 8.sp, color = GlacierOnSurfaceVariant)
              Text(text = "Semana 3 (8.1 µV)", fontSize = 8.sp, color = GlacierOnSurfaceVariant)
              Text(text = "Semana 5 (14.5 µV)", fontSize = 8.sp, color = GlacierOnSurfaceVariant)
              Text(text = "Semana 6 (21.4 µV pico)", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            }
          }
        }

        // Diário Miccional Padronizado (72h ICS)
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerHigh)
            .padding(10.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Diário Miccional Padronizado (72h ICS)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              }
              Text(text = "-88% de Perdas", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text(text = "Freq. Diurna: 5-6/dia", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
                Text(text = "Noctúria: 1 ep./noite", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
              }
              Column(horizontalAlignment = Alignment.End) {
                Text(text = "Perdas: 0 a 1 semanal", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
                Text(text = "Ingestão: 2.100 ml/dia", fontSize = 10.sp, color = GlacierSecondary)
              }
            }
          }
        }

        // Parecer & Assinatura ICP-Brasil
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerLow)
            .padding(10.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = "“Paciente apresenta excelente resposta proprioceptiva e coordenação diafragma-períneo. Houve remissão quase total dos episódios de perda por esforço. Liberada para progressão de exercícios funcionais em ortostatismo e preparação perineal com massagem e balonete a partir da 34ª semana.”",
            fontSize = 9.sp,
            color = GlacierOnSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 13.sp
          )

          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "Dra. Luana Silva", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
          Text(text = "CREFITO-3/284910-F • Especialista em Fisioterapia da Mulher", fontSize = 8.sp, color = GlacierOnSurfaceVariant)

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(9999.dp))
              .background(GlacierSurfaceContainerHighest)
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(GlacierPrimary))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "Assinatura Digital ICP-Brasil (SHA-256: 4b9e8...71f2a)", fontSize = 8.sp, color = GlacierOnSurfaceVariant)
            }
          }
        }
      }
    }

    // 4. Destinatários & Compartilhamento (1 Toque)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "DESTINATÁRIOS & COMPARTILHAMENTO", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurfaceVariant)
        Text(text = "1 Toque", fontSize = 10.sp, color = GlacierPrimary)
      }

      // Obstetra
      GlacierCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(GlacierPrimary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.MedicalServices, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(text = "Dr. Roberto Guimarães", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "Obstetra / Ginecologista Assistente", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
          }

          Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierSurfaceContainerHighest)
                .clickable { sharePdfIntent("WhatsApp do Dr. Roberto") }
                .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Chat, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "WhatsApp", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
              }
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierSurfaceContainerHighest)
                .clickable { sharePdfIntent("E-mail do Dr. Roberto") }
                .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Mail, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "E-mail", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
              }
            }
          }
        }
      }

      // Paciente
      GlacierCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(GlacierTertiaryContainer.copy(alpha = 0.5f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(text = "${profile.name} (Paciente)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "Disponibilizado na central do app", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierSurfaceContainerHighest)
              .clickable {
                Toast.makeText(context, "Cópia enviada para o aplicativo da paciente!", Toast.LENGTH_SHORT).show()
              }
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Send, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(12.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "Enviar Cópia", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierTertiary)
            }
          }
        }
      }
    }

    // 5. Master Action Buttons
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      GlacierButton(
        text = "Compartilhar Relatório em PDF",
        onClick = { sharePdfIntent("Todos os Canais") },
        icon = Icons.Default.IosShare,
        modifier = Modifier.fillMaxWidth().testTag("btn_compartilhar_master_pdf")
      )

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(GlacierSurfaceContainerHigh)
          .clickable {
            Toast.makeText(context, "Download do arquivo completo (3 páginas • 1.4 MB) concluído!", Toast.LENGTH_SHORT).show()
          }
          .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.FileDownload, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Baixar Arquivo Completo (3 Páginas • 1.4 MB)", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = GlacierOnSurface)
        }
      }
    }
  }
}
