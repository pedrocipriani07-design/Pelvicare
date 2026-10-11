package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val GlacierColorScheme = darkColorScheme(
  primary = GlacierPrimary,
  onPrimary = GlacierOnPrimary,
  primaryContainer = GlacierPrimaryContainer,
  onPrimaryContainer = GlacierOnPrimaryContainer,
  secondary = GlacierSecondary,
  onSecondary = GlacierOnSecondary,
  secondaryContainer = GlacierSecondaryContainer,
  onSecondaryContainer = GlacierOnSecondaryContainer,
  tertiary = GlacierTertiary,
  onTertiary = GlacierOnTertiary,
  tertiaryContainer = GlacierTertiaryContainer,
  onTertiaryContainer = GlacierOnTertiaryContainer,
  background = GlacierBackground,
  onBackground = GlacierOnBackground,
  surface = GlacierSurface,
  onSurface = GlacierOnSurface,
  surfaceVariant = GlacierSurfaceVariant,
  onSurfaceVariant = GlacierOnSurfaceVariant,
  surfaceContainer = GlacierSurfaceContainer,
  surfaceContainerLow = GlacierSurfaceContainerLow,
  surfaceContainerHigh = GlacierSurfaceContainerHigh,
  surfaceContainerHighest = GlacierSurfaceContainerHighest,
  surfaceContainerLowest = GlacierSurfaceContainerLowest,
  outline = GlacierOutline,
  outlineVariant = GlacierOutlineVariant,
  error = GlacierError,
  onError = GlacierOnError,
  errorContainer = GlacierErrorContainer,
  onErrorContainer = GlacierOnErrorContainer,
)

@Composable
fun PelviCareTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = GlacierColorScheme,
    typography = Typography,
    content = content
  )
}
