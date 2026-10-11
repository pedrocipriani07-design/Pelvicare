package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PregnantWoman
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
fun PerfilEditarScreen(
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val profile by PelviCareRepository.patientProfile.collectAsState()
  val context = LocalContext.current

  var nameInput by remember { mutableStateOf(profile.name) }
  var phoneInput by remember { mutableStateOf(profile.phone) }
  var emailInput by remember { mutableStateOf(profile.email) }
  var addressInput by remember { mutableStateOf(profile.address) }

  // Switches
  var morningReminder by remember { mutableStateOf(true) }
  var afternoonReminder by remember { mutableStateOf(true) }
  var hapticFeedback by remember { mutableStateOf(true) }
  var voiceGuidance by remember { mutableStateOf(true) }
  var bladderReminder by remember { mutableStateOf(true) }
  var shareWithObstetrician by remember { mutableStateOf(true) }
  var biometricLock by remember { mutableStateOf(true) }

  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Top Bar
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
          Text(text = "Voltar", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierPrimary)
        }
      }

      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Editar Dados & Perfil", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
        Text(text = "Ficha Cadastral PelviCare", fontSize = 10.sp, color = GlacierPrimary)
      }

      Text(
        text = "Salvar",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = GlacierPrimary,
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(GlacierPrimary.copy(alpha = 0.15f))
          .clickable {
            PelviCareRepository.updateProfile(
              profile.copy(
                name = nameInput,
                phone = phoneInput,
                email = emailInput,
                address = addressInput
              )
            )
            Toast.makeText(context, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show()
            onBack()
          }
          .padding(horizontal = 12.dp, vertical = 6.dp)
          .testTag("btn_salvar_topo_perfil")
      )
    }

    // 1. Avatar Card
    GlacierCard(
      modifier = Modifier.fillMaxWidth().testTag("profile_avatar_card"),
      backgroundColor = GlassSurface
    ) {
      Column(
        modifier = Modifier.padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Box(
          modifier = Modifier
            .size(86.dp)
            .clip(CircleShape)
            .border(2.dp, GlacierPrimary, CircleShape)
        ) {
          AsyncImage(
            model = profile.avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
          Box(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .size(26.dp)
              .clip(CircleShape)
              .background(GlacierPrimary),
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = null, tint = GlacierOnPrimary, modifier = Modifier.size(14.dp))
          }
        }

        GlacierPill(
          text = "Paciente Ativa • ${profile.gestationalWeeks}ª Sem. Gestacional",
          leadingDot = true,
          containerColor = GlacierPrimary.copy(alpha = 0.15f),
          contentColor = GlacierPrimary
        )

        Text(text = profile.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
        Text(text = "${profile.age} anos • Prontuário ${profile.id}", fontSize = 11.sp, color = GlacierOnSurfaceVariant)

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerLow)
            .padding(10.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GlacierTertiaryContainer.copy(alpha = 0.6f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.MedicalServices, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(text = "ACOMPANHAMENTO ESPECIALIZADO", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = GlacierTertiary)
              Text(text = profile.doctorName, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = profile.doctorTitle, fontSize = 9.sp, color = GlacierOnSurfaceVariant)
            }
          }
        }
      }
    }

    // 2. Dados Pessoais & Contato
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Dados Pessoais e de Contato", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
        }

        // Nome
        Column {
          Text(text = "Nome Completo *", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = GlacierOnSurface),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = GlacierPrimary,
              unfocusedBorderColor = GlassBorder,
              focusedContainerColor = GlacierSurfaceContainerHigh,
              unfocusedContainerColor = GlacierSurfaceContainerHigh
            ),
            shape = RoundedCornerShape(8.dp)
          )
        }

        // CPF (Protegido) & Nascimento
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierSurfaceContainerHigh)
              .padding(8.dp)
          ) {
            Column {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(text = "CPF Registrado", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
                Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(11.dp))
              }
              Text(text = profile.cpf, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            }
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(GlacierSurfaceContainerHigh)
              .padding(8.dp)
          ) {
            Column {
              Text(text = "Nascimento", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
              Text(text = profile.birthDate, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
            }
          }
        }

        // Email & Phone
        Column {
          Text(text = "E-mail Principal", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = GlacierOnSurface),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = GlacierPrimary,
              unfocusedBorderColor = GlassBorder,
              focusedContainerColor = GlacierSurfaceContainerHigh,
              unfocusedContainerColor = GlacierSurfaceContainerHigh
            ),
            shape = RoundedCornerShape(8.dp)
          )
        }

        Column {
          Text(text = "WhatsApp / Emergência", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          OutlinedTextField(
            value = phoneInput,
            onValueChange = { phoneInput = it },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = GlacierOnSurface),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = GlacierPrimary,
              unfocusedBorderColor = GlassBorder,
              focusedContainerColor = GlacierSurfaceContainerHigh,
              unfocusedContainerColor = GlacierSurfaceContainerHigh
            ),
            shape = RoundedCornerShape(8.dp)
          )
        }

        Column {
          Text(text = "Endereço Residencial", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
          OutlinedTextField(
            value = addressInput,
            onValueChange = { addressInput = it },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = GlacierOnSurface),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = GlacierPrimary,
              unfocusedBorderColor = GlassBorder,
              focusedContainerColor = GlacierSurfaceContainerHigh,
              unfocusedContainerColor = GlacierSurfaceContainerHigh
            ),
            shape = RoundedCornerShape(8.dp)
          )
        }
      }
    }

    // 3. Perfil Obstétrico & Fisioterapia Pélvica
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.PregnantWoman, contentDescription = null, tint = GlacierTertiary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Perfil Obstétrico & Fisioterapia Pélvica", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(GlacierSurfaceContainerHigh)
            .padding(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = "IDADE GESTACIONAL ATUAL", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
              Text(text = "${profile.gestationalWeeks}ª Semana (${profile.gestationalWeeks}s ${profile.gestationalDays}d)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
              Text(text = "Histórico: G2P1 • 1 Cesárea prévia", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            }
            GlacierPill(text = "DPP: ${profile.dpp}", containerColor = GlacierPrimary.copy(alpha = 0.15f), contentColor = GlacierPrimary)
          }
        }

        // Obstetrician & Goal
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(text = "Obstetra Assistente", fontSize = 10.sp, color = GlacierOnSurfaceVariant)
            Text(text = profile.obstetrician, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
          }
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(9999.dp))
              .background(GlacierSecondaryContainer.copy(alpha = 0.6f))
              .padding(horizontal = 8.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(11.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Vinculado", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = GlacierSecondary)
          }
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerLow)
            .padding(8.dp)
        ) {
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "Objetivo de Parto:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Parto Normal Humanizado (Preparo de Períneo & Massagem Perineal ativa)", fontSize = 11.sp, color = GlacierOnSurface)
          }
        }
      }
    }

    // 4. Preferências de Treino & Biofeedback (Switches)
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Preferências de Treino & Biofeedback", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
        }

        PreferenceSwitchRow(
          icon = Icons.Default.WbSunny,
          title = "Treino Matinal",
          subtitle = "Lembrete às 09:00 (Fibras Lentas / Kegel)",
          checked = morningReminder,
          onCheckedChange = { morningReminder = it }
        )

        PreferenceSwitchRow(
          icon = Icons.Default.Schedule,
          title = "Treino Vespertino",
          subtitle = "Lembrete às 16:00 (Fibras Rápidas / Agilidade)",
          checked = afternoonReminder,
          onCheckedChange = { afternoonReminder = it }
        )

        PreferenceSwitchRow(
          icon = Icons.Default.Vibration,
          title = "Feedback Háptico (Vibração)",
          subtitle = "Guiar contração e sustentação do assoalho",
          checked = hapticFeedback,
          onCheckedChange = { hapticFeedback = it }
        )

        PreferenceSwitchRow(
          icon = Icons.Default.RecordVoiceOver,
          title = "Voz Guiada dos Exercícios",
          subtitle = "Comandos de áudio com a Dra. Luana",
          checked = voiceGuidance,
          onCheckedChange = { voiceGuidance = it }
        )

        PreferenceSwitchRow(
          icon = Icons.Default.WaterDrop,
          title = "Diário Miccional & Hidratação",
          subtitle = "Lembretes regulares a cada 3 horas",
          checked = bladderReminder,
          onCheckedChange = { bladderReminder = it }
        )
      }
    }

    // 5. Segurança & Privacidade LGPD
    GlacierCard(
      modifier = Modifier.fillMaxWidth(),
      backgroundColor = GlacierSurfaceContainer.copy(alpha = 0.7f)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Segurança & Privacidade LGPD", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GlacierOnSurface)
        }

        PreferenceSwitchRow(
          icon = Icons.Default.Share,
          title = "Compartilhar com Obstetra",
          subtitle = "Permitir Dr. Roberto acessar relatórios em PDF",
          checked = shareWithObstetrician,
          onCheckedChange = { shareWithObstetrician = it }
        )

        PreferenceSwitchRow(
          icon = Icons.Default.Fingerprint,
          title = "Bloqueio Biométrico / Face ID",
          subtitle = "Exigir autenticação ao abrir o PelviCare",
          checked = biometricLock,
          onCheckedChange = { biometricLock = it }
        )

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GlacierSurfaceContainerHigh)
            .padding(10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.AssignmentTurnedIn, contentDescription = null, tint = GlacierSecondary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(text = "TCLE de Fisioterapia Pélvica", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
              Text(text = "Assinado digitalmente em 10/02/2025", fontSize = 9.sp, color = GlacierOnSurfaceVariant)
            }
          }
          Text(text = "Ver PDF", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GlacierPrimary)
        }
      }
    }

    // Save Button
    GlacierButton(
      text = "Salvar Alterações",
      onClick = {
        PelviCareRepository.updateProfile(
          profile.copy(
            name = nameInput,
            phone = phoneInput,
            email = emailInput,
            address = addressInput
          )
        )
        Toast.makeText(context, "Dados cadastrais salvos!", Toast.LENGTH_SHORT).show()
        onBack()
      },
      icon = Icons.Default.CheckCircle,
      modifier = Modifier.fillMaxWidth().testTag("btn_salvar_perfil_rodape")
    )
  }
}

@Composable
private fun PreferenceSwitchRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  subtitle: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      modifier = Modifier.weight(1f),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(GlacierSurfaceContainerHigh),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = GlacierPrimary, modifier = Modifier.size(16.dp))
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = GlacierOnSurface)
        Text(text = subtitle, fontSize = 10.sp, color = GlacierOnSurfaceVariant)
      }
    }

    Switch(
      checked = checked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = GlacierOnPrimary,
        checkedTrackColor = GlacierPrimary,
        uncheckedThumbColor = GlacierOnSurfaceVariant,
        uncheckedTrackColor = GlacierSurfaceContainerHigh
      )
    )
  }
}
