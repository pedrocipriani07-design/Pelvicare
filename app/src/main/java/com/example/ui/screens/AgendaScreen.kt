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
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PregnantWoman
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
fun AgendaScreen(
  onNavigateToProntuario: () -> Unit,
  onNavigateToTreino: () -> Unit,
  onNavigateToEvolucao: () -> Unit,
  onNavigateTo34Weeks: () -> Unit,
  modifier: Modifier = Modifier
) {
  val profile by PelviCareRepository.patientProfile.collectAsState()
  val scrollState = rememberScrollState()
  var selectedTabDay by remember { mutableStateOf("Hoje") }
  var emgModalOpen by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Welcome & Identity Glass Card
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("agenda_doctor_card"),
      backgroundColor = GlassSurface
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top
        ) {
          Column(modifier = Modifier.weight(1f)) {
            GlacierPill(
              text = "CLÍNICA ATIVA • SALA 01",
              leadingDot = true,
              containerColor = GlacierPrimary.copy(alpha = 0.12f),
              contentColor = GlacierPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Olá, Dra. Luana Silva",
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Especialista em Fisioterapia Pélvica & Saúde da Mulher • CREFITO-3/284910-F",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant
            )
          }

          Box(
            modifier = Modifier
              .size(56.dp)
              .clip(RoundedCornerShape(16.dp))
              .border(1.5.dp, GlacierPrimary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
          ) {
            AsyncImage(
              model = profile.doctorAvatarUrl,
              contentDescription = "Dra. Luana Silva",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
            Box(
              modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(16.dp)
                .clip(CircleShape)
                .background(GlacierSurfaceContainerHighest),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = null,
                tint = GlacierPrimary,
                modifier = Modifier.size(12.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(GlassBorder)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Spa,
              contentDescription = null,
              tint = GlacierSecondary,
              modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "6 atendimentos agendados • 1 em andamento",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant
            )
          }
          Text(
            text = "Terça, 18 Mar",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = GlacierPrimary
          )
        }
      }
    }

    // 2. Visão Clínica Geral (Glacier Bento Grid)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "VISÃO CLÍNICA GERAL",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = GlacierOnSurfaceVariant,
          letterSpacing = 0.8.sp
        )
        Text(
          text = "Tempo Real",
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
          color = GlacierPrimary
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Card 1: Pacientes Ativas
        GlacierCard(
          modifier = Modifier.weight(1f).height(115.dp),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
        ) {
          Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierPrimary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.PregnantWoman,
                  contentDescription = null,
                  tint = GlacierPrimary,
                  modifier = Modifier.size(16.dp)
                )
              }
              Text(
                text = "+4 este mês",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierPrimary,
                modifier = Modifier
                  .clip(RoundedCornerShape(9999.dp))
                  .background(GlacierPrimary.copy(alpha = 0.12f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
            Column {
              Text(
                text = "38",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierOnSurface
              )
              Text(
                text = "Pacientes Ativas",
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant
              )
            }
          }
        }

        // Card 2: Atendimentos Hoje
        GlacierCard(
          modifier = Modifier.weight(1f).height(115.dp),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
        ) {
          Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierSecondary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.CalendarMonth,
                  contentDescription = null,
                  tint = GlacierSecondary,
                  modifier = Modifier.size(16.dp)
                )
              }
              Text(
                text = "Dia Cheio",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierSecondary,
                modifier = Modifier
                  .clip(RoundedCornerShape(9999.dp))
                  .background(GlacierSecondary.copy(alpha = 0.12f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
            Column {
              Row(verticalAlignment = Alignment.Bottom) {
                Text(
                  text = "6",
                  fontSize = 24.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnSurface
                )
                Text(
                  text = " / 8 slots",
                  fontSize = 12.sp,
                  color = GlacierOnSurfaceVariant
                )
              }
              Text(
                text = "Atendimentos Hoje",
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant
              )
            }
          }
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Card 3: Alertas Clínicos
        GlacierCard(
          modifier = Modifier.weight(1f).height(115.dp),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
        ) {
          Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierError.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Warning,
                  contentDescription = null,
                  tint = GlacierError,
                  modifier = Modifier.size(16.dp)
                )
              }
              Text(
                text = "Urgente",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierError,
                modifier = Modifier
                  .clip(RoundedCornerShape(9999.dp))
                  .background(GlacierError.copy(alpha = 0.12f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
            Column {
              Text(
                text = "02",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierError
              )
              Text(
                text = "Alertas Clínicos",
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant
              )
            }
          }
        }

        // Card 4: Adesão Domiciliar
        GlacierCard(
          modifier = Modifier.weight(1f).height(115.dp),
          backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
        ) {
          Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierTertiary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = GlacierTertiary,
                  modifier = Modifier.size(16.dp)
                )
              }
              Text(
                text = "+6% sem.",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = GlacierTertiary,
                modifier = Modifier
                  .clip(RoundedCornerShape(9999.dp))
                  .background(GlacierTertiary.copy(alpha = 0.12f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
            Column {
              Text(
                text = "84%",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = GlacierOnSurface
              )
              Text(
                text = "Adesão Domiciliar",
                fontSize = 11.sp,
                color = GlacierOnSurfaceVariant
              )
            }
          }
        }
      }
    }

    // 3. Monitoramento Pós-Parto (Priority Clinical Alert Banner)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = GlacierError,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "MONITORAMENTO PÓS-PARTO",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierOnSurfaceVariant,
            letterSpacing = 0.8.sp
          )
        }
        Text(
          text = "Ver todos (2)",
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
          color = GlacierError
        )
      }

      GlacierCard(
        modifier = Modifier.fillMaxWidth().testTag("priority_alert_card"),
        backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.8f)
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
                  .size(42.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .border(1.dp, GlacierError.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
              ) {
                AsyncImage(
                  model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCr8ZfnWfr8GBMuAyArj7V5lnZzD-yaizIpJknmEAAxg637um57gBo5jvTKCqwxBJ1Atc8l6LzumnUihn8Pq1rVajhdH5eiYmE0cbdC_XAWvg0heAbsT6LyJ6zIFAB9R3IQ9Rz_b0nO0chFM0GPBkNX6_D8vwtaJEGj1l7XRCEgbHRgm2WdIgxAeYAwbraf4OVinxLQWNfzKxBQAPqnkckr12-jN-hgO50_4rAR4RLHhY04aPg1MJg",
                  contentDescription = "Mariana Costa",
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.fillMaxSize()
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "Mariana Costa",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = GlacierOnSurface
                )
                Text(
                  text = "Puerpério D12 • Parto Normal",
                  fontSize = 11.sp,
                  color = GlacierOnSurfaceVariant
                )
              }
            }

            Text(
              text = "EVA 6 • Dor Perineal",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierError,
              modifier = Modifier
                .clip(RoundedCornerShape(9999.dp))
                .background(GlacierError.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

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
                Text(
                  text = "Último Relato no App Paciente:",
                  fontSize = 11.sp,
                  color = GlacierSecondary,
                  fontWeight = FontWeight.Medium
                )
                Text(
                  text = "Há 22 min",
                  fontSize = 10.sp,
                  color = GlacierOnSurfaceVariant
                )
              }
              Text(
                text = "“Desconforto ao sentar durante amamentação. Episiorrafia cicatrizada e íntegra, sem sinais flogísticos visíveis. Relatou receio ao urinar.”",
                fontSize = 11.sp,
                color = GlacierOnSurface,
                lineHeight = 16.sp
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
                .height(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GlacierSurfaceContainerHigh)
                .clickable { onNavigateToProntuario() }
                .padding(horizontal = 8.dp),
              contentAlignment = Alignment.Center
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Description,
                  contentDescription = null,
                  tint = GlacierSecondary,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Ver Prontuário",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  color = GlacierOnSurface
                )
              }
            }

            Box(
              modifier = Modifier
                .weight(1f)
                .height(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GlacierPrimary)
                .clickable { /* Direct Chat */ }
                .padding(horizontal = 8.dp),
              contentAlignment = Alignment.Center
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Chat,
                  contentDescription = null,
                  tint = GlacierOnPrimary,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Mensagem Direta",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = GlacierOnPrimary
                )
              }
            }
          }
        }
      }
    }

    // 4. Interactive Daily Timeline Schedule
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Schedule,
            contentDescription = null,
            tint = GlacierPrimary,
            modifier = Modifier.size(15.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "AGENDA DO DIA",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierOnSurfaceVariant,
            letterSpacing = 0.8.sp
          )
        }

        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerLow)
            .padding(2.dp)
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(if (selectedTabDay == "Hoje") GlacierPrimary.copy(alpha = 0.2f) else Color.Transparent)
              .clickable { selectedTabDay = "Hoje" }
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Text(
              text = "Hoje",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (selectedTabDay == "Hoje") GlacierPrimary else GlacierOnSurfaceVariant
            )
          }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(if (selectedTabDay == "Amanhã") GlacierPrimary.copy(alpha = 0.2f) else Color.Transparent)
              .clickable { selectedTabDay = "Amanhã" }
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Text(
              text = "Amanhã",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (selectedTabDay == "Amanhã") GlacierPrimary else GlacierOnSurfaceVariant
            )
          }
        }
      }

      // Schedule Items
      PelviCareRepository.scheduleToday.forEach { item ->
        val isLive = item.status == "Ao Vivo"
        GlacierCard(
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              if (item.patientName.contains("Camila")) {
                onNavigateToProntuario()
              }
            },
          backgroundColor = if (isLive) GlacierPrimaryContainer.copy(alpha = 0.35f) else GlacierSurfaceContainer.copy(alpha = 0.6f),
          borderColor = if (isLive) GlacierPrimary.copy(alpha = 0.45f) else GlassBorder
        ) {
          Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Row(verticalAlignment = Alignment.Top) {
                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  modifier = Modifier.width(44.dp)
                ) {
                  Text(
                    text = item.time,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isLive) GlacierPrimary else GlacierOnSurface
                  )
                  Text(
                    text = "${item.durationMin}m",
                    fontSize = 10.sp,
                    color = GlacierOnSurfaceVariant
                  )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = item.patientName,
                      fontSize = 13.sp,
                      fontWeight = FontWeight.Bold,
                      color = GlacierOnSurface
                    )
                    if (isLive) {
                      Spacer(modifier = Modifier.width(6.dp))
                      GlacierPill(
                        text = "Ao Vivo",
                        containerColor = GlacierPrimary,
                        contentColor = GlacierOnPrimary
                      )
                    }
                  }
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(
                    text = item.treatment,
                    fontSize = 11.sp,
                    color = GlacierOnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }
              }

              Text(
                text = item.room,
                fontSize = 10.sp,
                color = GlacierOnSurfaceVariant,
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(GlacierSurfaceContainerHigh)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }

            if (isLive) {
              // Live sEMG Mini Bar
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(8.dp))
                  .background(GlacierSurfaceContainer.copy(alpha = 0.8f))
                  .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Sensors,
                    contentDescription = null,
                    tint = GlacierPrimary,
                    modifier = Modifier.size(15.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Pico EMG: ",
                    fontSize = 11.sp,
                    color = GlacierOnSurface
                  )
                  Text(
                    text = item.emgPeak ?: "28.4 µV",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GlacierPrimary
                  )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "Fadiga: Baixa",
                    fontSize = 10.sp,
                    color = GlacierOnSurfaceVariant
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Box(
                    modifier = Modifier
                      .size(6.dp)
                      .clip(CircleShape)
                      .background(GlacierPrimary)
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
                    .height(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GlacierPrimary)
                    .clickable { onNavigateToTreino() },
                  contentAlignment = Alignment.Center
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.Sensors,
                      contentDescription = null,
                      tint = GlacierOnPrimary,
                      modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "Abrir Eletromiógrafo",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.SemiBold,
                      color = GlacierOnPrimary
                    )
                  }
                }

                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GlacierSurfaceContainerHigh)
                    .clickable { onNavigateToProntuario() },
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "Prontuário",
                    tint = GlacierPrimary,
                    modifier = Modifier.size(18.dp)
                  )
                }
              }
            } else {
              Text(
                text = item.note,
                fontSize = 10.sp,
                color = GlacierSecondary
              )
            }
          }
        }
      }
    }

    // 5. Atalhos Clínicos Rápidos
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Text(
        text = "ATALHOS CLÍNICOS RÁPIDOS",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = GlacierOnSurfaceVariant,
        letterSpacing = 0.8.sp,
        modifier = Modifier.padding(horizontal = 4.dp)
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        ClinicalShortcut(
          icon = Icons.Default.PersonAdd,
          label = "Nova Avaliação",
          tint = GlacierPrimary,
          onClick = onNavigateToProntuario,
          modifier = Modifier.weight(1f)
        )
        ClinicalShortcut(
          icon = Icons.Default.FitnessCenter,
          label = "Prescrever Treino",
          tint = GlacierTertiary,
          onClick = onNavigateToTreino,
          modifier = Modifier.weight(1f)
        )
        ClinicalShortcut(
          icon = Icons.Default.Sensors,
          label = "Biofeedback",
          tint = GlacierSecondary,
          onClick = onNavigateToTreino,
          modifier = Modifier.weight(1f)
        )
        ClinicalShortcut(
          icon = Icons.Default.Description,
          label = "Emitir Laudo",
          tint = GlacierPrimary,
          onClick = onNavigateToEvolucao,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 6. Visual Atlas 3D Capsule
    GlacierCard(
      modifier = Modifier
        .fillMaxWidth()
        .clickable { onNavigateTo34Weeks() }
        .testTag("atlas_3d_capsule"),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.8f)
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(GlacierPrimary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Spa,
              contentDescription = null,
              tint = GlacierPrimary,
              modifier = Modifier.size(24.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Atlas 3D de Assoalho Pélvico",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = GlacierOnSurface
            )
            Text(
              text = "Pronto para demonstração à paciente em consulta",
              fontSize = 11.sp,
              color = GlacierOnSurfaceVariant
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerHighest)
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(
            text = "Abrir 3D",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierPrimary
          )
        }
      }
    }
  }
}

@Composable
private fun ClinicalShortcut(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  tint: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(14.dp))
      .background(GlacierSurfaceContainer.copy(alpha = 0.6f))
      .border(1.dp, GlassBorder, RoundedCornerShape(14.dp))
      .clickable(onClick = onClick)
      .padding(vertical = 12.dp, horizontal = 4.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(tint.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = tint,
          modifier = Modifier.size(18.dp)
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = label,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = GlacierOnSurface,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        lineHeight = 13.sp,
        maxLines = 2
      )
    }
  }
}
