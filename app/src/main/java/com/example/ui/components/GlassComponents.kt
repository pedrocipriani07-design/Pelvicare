package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.GlacierBackground
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
import com.example.ui.theme.GlassCard
import com.example.ui.theme.GlassSurface

@Composable
fun GlacierCard(
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(16.dp),
  backgroundColor: Color = GlassSurface,
  borderColor: Color = GlassBorder,
  borderWidth: Dp = 1.dp,
  content: @Composable () -> Unit
) {
  Box(
    modifier = modifier
      .clip(shape)
      .background(backgroundColor)
      .border(borderWidth, borderColor, shape)
  ) {
    content()
  }
}

@Composable
fun GlacierPill(
  text: String,
  modifier: Modifier = Modifier,
  leadingDot: Boolean = false,
  leadingIcon: ImageVector? = null,
  containerColor: Color = GlacierPrimary.copy(alpha = 0.15f),
  contentColor: Color = GlacierPrimary
) {
  Row(
    modifier = modifier
      .clip(RoundedCornerShape(9999.dp))
      .background(containerColor)
      .padding(horizontal = 10.dp, vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    if (leadingDot) {
      val infiniteTransition = rememberInfiniteTransition(label = "pulse")
      val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
          animation = tween(800, easing = FastOutSlowInEasing),
          repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
      )
      Box(
        modifier = Modifier
          .size(6.dp)
          .clip(CircleShape)
          .background(contentColor.copy(alpha = alpha))
      )
      Spacer(modifier = Modifier.width(6.dp))
    }
    if (leadingIcon != null) {
      Icon(
        imageVector = leadingIcon,
        contentDescription = null,
        tint = contentColor,
        modifier = Modifier.size(13.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
    }
    Text(
      text = text,
      color = contentColor,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      letterSpacing = 0.4.sp
    )
  }
}

@Composable
fun GlacierButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  icon: ImageVector? = null,
  containerColor: Color = GlacierPrimary,
  contentColor: Color = GlacierOnPrimary,
  enabled: Boolean = true
) {
  Row(
    modifier = modifier
      .height(48.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(if (enabled) containerColor else containerColor.copy(alpha = 0.5f))
      .clickable(
        enabled = enabled,
        onClick = onClick
      )
      .padding(horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    if (icon != null) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = contentColor,
        modifier = Modifier.size(18.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
    }
    Text(
      text = text,
      color = contentColor,
      fontSize = 13.sp,
      fontWeight = FontWeight.SemiBold
    )
  }
}

@Composable
fun GlacierOutlineButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  icon: ImageVector? = null,
  containerColor: Color = GlacierSurfaceContainerHigh.copy(alpha = 0.8f),
  contentColor: Color = GlacierOnSurface,
  borderColor: Color = GlassBorder
) {
  Row(
    modifier = modifier
      .height(44.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(containerColor)
      .border(1.dp, borderColor, RoundedCornerShape(12.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 14.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    if (icon != null) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = GlacierPrimary,
        modifier = Modifier.size(18.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
    }
    Text(
      text = text,
      color = contentColor,
      fontSize = 12.sp,
      fontWeight = FontWeight.Medium
    )
  }
}

@Composable
fun GlacierLinearBar(
  progress: Float,
  modifier: Modifier = Modifier,
  trackColor: Color = GlacierSurfaceContainerHighest,
  gradientColors: List<Color> = listOf(GlacierSecondary, GlacierPrimary, GlacierTertiary)
) {
  val clampedProgress = progress.coerceIn(0f, 1f)
  Box(
    modifier = modifier
      .height(8.dp)
      .fillMaxWidth()
      .clip(RoundedCornerShape(9999.dp))
      .background(trackColor)
  ) {
    Box(
      modifier = Modifier
        .height(8.dp)
        .fillMaxWidth(clampedProgress)
        .clip(RoundedCornerShape(9999.dp))
        .background(Brush.horizontalGradient(gradientColors))
    )
  }
}

@Composable
fun PelviCareTopHeader(
  title: String,
  subtitle: String,
  isDoctorMode: Boolean,
  onTogglePerspective: () -> Unit,
  avatarUrl: String,
  onAvatarClick: () -> Unit,
  onNotificationClick: () -> Unit = {}
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(64.dp)
      .background(GlacierBackground.copy(alpha = 0.85f))
      .border(width = 0.5.dp, color = GlassBorder, shape = RoundedCornerShape(0.dp))
      .padding(horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    // Brand Logo & Title
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.weight(1f)
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(GlacierSurfaceContainer)
          .border(1.dp, GlacierPrimary.copy(alpha = 0.25f), RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Spa,
          contentDescription = "PelviCare",
          tint = GlacierPrimary,
          modifier = Modifier.size(20.dp)
        )
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "PELVICARE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GlacierPrimary,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.width(6.dp))
          // Perspective Badge Clickable
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(if (isDoctorMode) GlacierTertiary.copy(alpha = 0.2f) else GlacierPrimary.copy(alpha = 0.15f))
              .clickable { onTogglePerspective() }
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.SwapHoriz,
                contentDescription = "Alternar Visão",
                tint = if (isDoctorMode) GlacierTertiary else GlacierPrimary,
                modifier = Modifier.size(10.dp)
              )
              Spacer(modifier = Modifier.width(2.dp))
              Text(
                text = if (isDoctorMode) "Fisio" else "Paciente",
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isDoctorMode) GlacierTertiary else GlacierPrimary
              )
            }
          }
        }
        Text(
          text = title,
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = GlacierOnSurface,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }
    }

    // Actions: Notifications & Avatar
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      IconButton(
        onClick = onNotificationClick,
        modifier = Modifier.size(40.dp).testTag("header_notifications_button")
      ) {
        Icon(
          imageVector = Icons.Default.Notifications,
          contentDescription = "Notificações",
          tint = GlacierOnSurfaceVariant,
          modifier = Modifier.size(20.dp)
        )
      }

      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .border(1.5.dp, GlacierPrimary.copy(alpha = 0.4f), CircleShape)
          .clickable { onAvatarClick() }
          .testTag("header_profile_avatar")
      ) {
        AsyncImage(
          model = avatarUrl,
          contentDescription = "Perfil",
          contentScale = ContentScale.Crop,
          modifier = Modifier.size(36.dp)
        )
      }
    }
  }
}
