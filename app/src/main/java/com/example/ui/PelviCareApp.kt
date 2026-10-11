package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PelviCareRepository
import com.example.ui.components.PelviCareTopHeader
import com.example.ui.screens.AgendaScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DiarioMiccionalScreen
import com.example.ui.screens.EvolucaoScreen
import com.example.ui.screens.FeedbackTreinoScreen
import com.example.ui.screens.MassagemPerinealScreen
import com.example.ui.screens.PerfilEditarScreen
import com.example.ui.screens.Preparo34SemanasScreen
import com.example.ui.screens.PrescricaoScreen
import com.example.ui.screens.ProntuarioScreen
import com.example.ui.screens.RelatorioClinicoPdfScreen
import com.example.ui.screens.TreinoGuiadoScreen
import com.example.ui.theme.GlacierBackground
import com.example.ui.theme.GlacierOnSurface
import com.example.ui.theme.GlacierOnSurfaceVariant
import com.example.ui.theme.GlacierPrimary
import com.example.ui.theme.GlacierSurfaceContainerLow
import com.example.ui.theme.GlassBorder

sealed class AppDestination(val route: String) {
  // Main Navigation Tabs
  object Agenda : AppDestination("agenda")
  object Prontuario : AppDestination("prontuario")
  object Prescricao : AppDestination("prescricao")
  object Diario : AppDestination("diario")
  object Evolucao : AppDestination("evolucao")

  // Sub-Flows
  object Dashboard : AppDestination("dashboard")
  object TreinoGuiado : AppDestination("treino_guiado")
  object FeedbackTreino : AppDestination("feedback_treino")
  object Preparo34Semanas : AppDestination("preparo_34semanas")
  object MassagemPerineal : AppDestination("massagem_perineal")
  object RelatorioPdf : AppDestination("relatorio_pdf")
  object PerfilEditar : AppDestination("perfil_editar")
}

@Composable
fun PelviCareApp() {
  val isDoctorMode by PelviCareRepository.isDoctorPerspective.collectAsState()
  val profile by PelviCareRepository.patientProfile.collectAsState()

  var currentDestination by remember { mutableStateOf<AppDestination>(AppDestination.Agenda) }

  // Handle Android hardware/gesture back press for sub-screens
  if (currentDestination !in listOf(
      AppDestination.Agenda,
      AppDestination.Prontuario,
      AppDestination.Prescricao,
      AppDestination.Diario,
      AppDestination.Evolucao
    )
  ) {
    BackHandler {
      currentDestination = if (currentDestination == AppDestination.Dashboard) {
        AppDestination.Evolucao
      } else if (isDoctorMode) {
        AppDestination.Agenda
      } else {
        AppDestination.Prescricao
      }
    }
  }

  Scaffold(
    contentWindowInsets = WindowInsets(0.dp),
    topBar = {
      // Top Bar shown on main screens
      if (currentDestination in listOf(
          AppDestination.Agenda,
          AppDestination.Prontuario,
          AppDestination.Prescricao,
          AppDestination.Diario,
          AppDestination.Evolucao,
          AppDestination.Dashboard
        )
      ) {
        val screenTitle = when (currentDestination) {
          AppDestination.Agenda -> if (isDoctorMode) "Agenda Clínica" else "Início • Bem-vinda"
          AppDestination.Prontuario -> "Prontuário Pélvico"
          AppDestination.Prescricao -> if (isDoctorMode) "Prescrição Clínica" else "Treinos & Protocolos"
          AppDestination.Diario -> "Diário Miccional 72h"
          AppDestination.Evolucao -> "Evolução & Progresso"
          AppDestination.Dashboard -> "Dashboard Analítico"
          else -> "PelviCare"
        }

        Box(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
          PelviCareTopHeader(
            title = screenTitle,
            subtitle = if (isDoctorMode) profile.doctorName else profile.name,
            isDoctorMode = isDoctorMode,
            onTogglePerspective = {
              PelviCareRepository.togglePerspective()
            },
            avatarUrl = if (isDoctorMode) profile.doctorAvatarUrl else profile.avatarUrl,
            onAvatarClick = {
              currentDestination = AppDestination.PerfilEditar
            }
          )
        }
      }
    },
    bottomBar = {
      // Bottom Bar visible on primary tabs
      if (currentDestination in listOf(
          AppDestination.Agenda,
          AppDestination.Prontuario,
          AppDestination.Prescricao,
          AppDestination.Diario,
          AppDestination.Evolucao,
          AppDestination.Dashboard
        )
      ) {
        PelviCareBottomNavBar(
          currentDestination = if (currentDestination == AppDestination.Dashboard) AppDestination.Evolucao else currentDestination,
          isDoctorMode = isDoctorMode,
          onSelect = { currentDestination = it }
        )
      }
    },
    containerColor = GlacierBackground
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      AnimatedContent(
        targetState = currentDestination,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "ScreenTransition"
      ) { destination ->
        when (destination) {
          AppDestination.Agenda -> AgendaScreen(
            onNavigateToProntuario = { currentDestination = AppDestination.Prontuario },
            onNavigateToTreino = { currentDestination = AppDestination.TreinoGuiado },
            onNavigateToEvolucao = { currentDestination = AppDestination.Evolucao },
            onNavigateTo34Weeks = { currentDestination = AppDestination.Preparo34Semanas }
          )

          AppDestination.Prontuario -> ProntuarioScreen(
            onExportPdf = { currentDestination = AppDestination.RelatorioPdf },
            onNavigateTo34Weeks = { currentDestination = AppDestination.Preparo34Semanas }
          )

          AppDestination.Prescricao -> PrescricaoScreen(
            onStartWorkout = { currentDestination = AppDestination.TreinoGuiado },
            onNavigateToHistory = { currentDestination = AppDestination.Evolucao },
            onNavigateToEditProfile = { currentDestination = AppDestination.PerfilEditar }
          )

          AppDestination.Diario -> DiarioMiccionalScreen(
            onExportReport = { currentDestination = AppDestination.RelatorioPdf },
            onOpenDashboard = { currentDestination = AppDestination.Dashboard }
          )

          AppDestination.Evolucao -> EvolucaoScreen(
            onExportPdf = { currentDestination = AppDestination.RelatorioPdf },
            onNavigateTo34Weeks = { currentDestination = AppDestination.Preparo34Semanas },
            onNavigateToDashboard = { currentDestination = AppDestination.Dashboard }
          )

          AppDestination.Dashboard -> DashboardScreen(
            onNavigateToTreino = { currentDestination = AppDestination.TreinoGuiado },
            onNavigateToDiario = { currentDestination = AppDestination.Diario },
            onExportPdf = { currentDestination = AppDestination.RelatorioPdf }
          )

          AppDestination.TreinoGuiado -> TreinoGuiadoScreen(
            onFinishWorkout = { currentDestination = AppDestination.FeedbackTreino },
            onExit = { currentDestination = AppDestination.Prescricao }
          )

          AppDestination.FeedbackTreino -> FeedbackTreinoScreen(
            onSaveAndProceed = { currentDestination = AppDestination.Evolucao },
            onGoHome = { currentDestination = AppDestination.Prescricao }
          )

          AppDestination.Preparo34Semanas -> Preparo34SemanasScreen(
            onStartPerinealMassage = { currentDestination = AppDestination.MassagemPerineal }
          )

          AppDestination.MassagemPerineal -> MassagemPerinealScreen(
            onBack = { currentDestination = AppDestination.Preparo34Semanas }
          )

          AppDestination.RelatorioPdf -> RelatorioClinicoPdfScreen(
            onBack = { currentDestination = AppDestination.Evolucao }
          )

          AppDestination.PerfilEditar -> PerfilEditarScreen(
            onBack = { currentDestination = AppDestination.Prescricao }
          )
        }
      }
    }
  }
}

@Composable
fun PelviCareBottomNavBar(
  currentDestination: AppDestination,
  isDoctorMode: Boolean,
  onSelect: (AppDestination) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(GlacierSurfaceContainerLow.copy(alpha = 0.92f))
      .border(0.5.dp, GlassBorder, RoundedCornerShape(0.dp))
      .windowInsetsPadding(WindowInsets.navigationBars)
      .height(64.dp)
      .testTag("pelvicare_bottom_navigation")
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      if (isDoctorMode) {
        BottomNavItem(
          icon = Icons.Default.CalendarMonth,
          label = "Agenda",
          isSelected = currentDestination == AppDestination.Agenda,
          onClick = { onSelect(AppDestination.Agenda) }
        )
        BottomNavItem(
          icon = Icons.Default.AssignmentInd,
          label = "Prontuário",
          isSelected = currentDestination == AppDestination.Prontuario,
          onClick = { onSelect(AppDestination.Prontuario) }
        )
        BottomNavItem(
          icon = Icons.Default.FitnessCenter,
          label = "Prescrição",
          isSelected = currentDestination == AppDestination.Prescricao,
          onClick = { onSelect(AppDestination.Prescricao) }
        )
        BottomNavItem(
          icon = Icons.Default.ShowChart,
          label = "Evolução",
          isSelected = currentDestination == AppDestination.Evolucao,
          onClick = { onSelect(AppDestination.Evolucao) }
        )
      } else {
        BottomNavItem(
          icon = Icons.Default.CalendarToday,
          label = "Início",
          isSelected = currentDestination == AppDestination.Agenda,
          onClick = { onSelect(AppDestination.Agenda) }
        )
        BottomNavItem(
          icon = Icons.Default.FitnessCenter,
          label = "Treinos",
          isSelected = currentDestination == AppDestination.Prescricao,
          onClick = { onSelect(AppDestination.Prescricao) }
        )
        BottomNavItem(
          icon = Icons.Default.EditNote,
          label = "Diário",
          isSelected = currentDestination == AppDestination.Diario,
          onClick = { onSelect(AppDestination.Diario) }
        )
        BottomNavItem(
          icon = Icons.Default.ShowChart,
          label = "Progresso",
          isSelected = currentDestination == AppDestination.Evolucao,
          onClick = { onSelect(AppDestination.Evolucao) }
        )
      }
    }
  }
}

@Composable
fun BottomNavItem(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Column(
    modifier = Modifier
      .clip(RoundedCornerShape(12.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 14.dp, vertical = 6.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = if (isSelected) GlacierPrimary else GlacierOnSurfaceVariant,
      modifier = Modifier.size(22.dp)
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = label,
      fontSize = 11.sp,
      fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
      color = if (isSelected) GlacierPrimary else GlacierOnSurfaceVariant,
      letterSpacing = (-0.2).sp
    )
  }
}
