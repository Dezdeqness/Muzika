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
    primary = FonoLightColors.Primary,
    onPrimary = FonoLightColors.OnPrimary,
    secondary = FonoLightColors.Secondary,
    onSecondary = FonoLightColors.OnSecondary,
    background = FonoLightColors.Background,
    onBackground = FonoLightColors.OnBackground,
    surface = FonoLightColors.Surface,
    onSurface = FonoLightColors.OnSurface,
    surfaceVariant = FonoLightColors.SurfaceVariant,
    error = FonoLightColors.Error,
)

fun toDarkMaterialScheme(): ColorScheme = darkColorScheme(
    primary = FonoDarkColors.Primary,
    onPrimary = FonoDarkColors.OnPrimary,
    secondary = FonoDarkColors.Secondary,
    onSecondary = FonoDarkColors.OnSecondary,
    background = FonoDarkColors.Background,
    onBackground = FonoDarkColors.OnBackground,
    surface = FonoDarkColors.Surface,
    onSurface = FonoDarkColors.OnSurface,
    surfaceVariant = FonoDarkColors.SurfaceVariant,
    error = FonoDarkColors.Error,
)
