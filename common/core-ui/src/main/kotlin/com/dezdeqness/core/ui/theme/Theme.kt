package com.dezdeqness.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun FonoTheme(
    colors: AppColors = if (isSystemInDarkTheme()) fonoDarkColors() else fonoLightColors(),
    materialDefaultTheme: ColorScheme = if (isSystemInDarkTheme()) toDarkMaterialScheme() else toLightMaterialScheme(),
    typography: AppTypography = AppTypography(),
    shapes: AppShapes = AppShapes(),
    content: @Composable () -> Unit
) {
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }

    MaterialTheme(colorScheme = materialDefaultTheme) {
        AppCustomTheme(
            colors = rememberedColors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

fun toLightMaterialScheme(): ColorScheme = lightColorScheme(
    primary = LightColors.Primary,
    onPrimary = LightColors.OnPrimary,
    secondary = LightColors.Secondary,
    onSecondary = LightColors.OnSecondary,
    background = LightColors.Background,
    onBackground = LightColors.OnBackground,
    surface = LightColors.Surface,
    onSurface = LightColors.OnSurface,
    surfaceVariant = LightColors.SurfaceVariant,
    error = LightColors.Error,
)

fun toDarkMaterialScheme(): ColorScheme = darkColorScheme(
    primary = DarkColors.Primary,
    onPrimary = DarkColors.OnPrimary,
    secondary = DarkColors.Secondary,
    onSecondary = DarkColors.OnSecondary,
    background = DarkColors.Background,
    onBackground = DarkColors.OnBackground,
    surface = DarkColors.Surface,
    onSurface = DarkColors.OnSurface,
    surfaceVariant = DarkColors.SurfaceVariant,
    error = DarkColors.Error,
)
