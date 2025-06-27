package org.babe.sqldelight.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = AppColors.Primary600,
    onPrimary = AppColors.Neutral50,
    primaryContainer = AppColors.Primary100,
    onPrimaryContainer = AppColors.Primary900,
    
    secondary = AppColors.Secondary600,
    onSecondary = AppColors.Neutral50,
    secondaryContainer = AppColors.Secondary100,
    onSecondaryContainer = AppColors.Secondary900,
    
    tertiary = AppColors.Success600,
    onTertiary = AppColors.Neutral50,
    tertiaryContainer = AppColors.Success100,
    onTertiaryContainer = AppColors.Success900,
    
    error = AppColors.Error600,
    onError = AppColors.Neutral50,
    errorContainer = AppColors.Error100,
    onErrorContainer = AppColors.Error900,
    
    background = AppColors.Background,
    onBackground = AppColors.OnBackground,
    
    surface = AppColors.Surface,
    onSurface = AppColors.OnSurface,
    surfaceVariant = AppColors.SurfaceVariant,
    onSurfaceVariant = AppColors.Neutral600,
    
    outline = AppColors.Neutral300,
    outlineVariant = AppColors.Neutral200,
    
    scrim = AppColors.Neutral900.copy(alpha = 0.32f),
    
    inverseSurface = AppColors.Neutral800,
    inverseOnSurface = AppColors.Neutral100,
    inversePrimary = AppColors.Primary300,
    
    surfaceTint = AppColors.Primary600
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        content = content
    )
}