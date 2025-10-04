package com.dezdeqness.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

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
    primaryContainer = FonoLightColors.PrimaryVariant,
    onPrimaryContainer = FonoLightColors.OnPrimary,

    secondary = FonoLightColors.Secondary,
    onSecondary = FonoLightColors.OnSecondary,
    secondaryContainer = FonoLightColors.SurfaceVariant,
    onSecondaryContainer = FonoLightColors.OnSecondary,

    tertiary = FonoLightColors.Accent,
    onTertiary = FonoLightColors.OnSecondary,
    tertiaryContainer = FonoLightColors.SurfaceVariant,
    onTertiaryContainer = FonoLightColors.OnSurface,

    background = FonoLightColors.Background,
    onBackground = FonoLightColors.OnBackground,

    surface = FonoLightColors.Surface,
    onSurface = FonoLightColors.OnSurface,
    surfaceVariant = FonoLightColors.SurfaceVariant,
    onSurfaceVariant = FonoLightColors.OnSurface,

    error = FonoLightColors.Error,
    onError = Color.White,
    errorContainer = FonoLightColors.Error.copy(alpha = 0.1f),
    onErrorContainer = FonoLightColors.Error,

    outline = FonoLightColors.Border,
    inverseOnSurface = FonoLightColors.Surface,
    inverseSurface = FonoLightColors.OnBackground,
    inversePrimary = FonoLightColors.PrimaryVariant,

    scrim = Color.Black
)

fun toDarkMaterialScheme(): ColorScheme = darkColorScheme(
    primary = FonoDarkColors.Primary,
    onPrimary = FonoDarkColors.OnPrimary,
    primaryContainer = FonoDarkColors.PrimaryVariant,
    onPrimaryContainer = FonoDarkColors.OnPrimary,

    secondary = FonoDarkColors.Secondary,
    onSecondary = FonoDarkColors.OnSecondary,
    secondaryContainer = FonoDarkColors.SurfaceVariant,
    onSecondaryContainer = FonoDarkColors.OnSecondary,

    tertiary = FonoDarkColors.Accent,
    onTertiary = FonoDarkColors.OnSecondary,
    tertiaryContainer = FonoDarkColors.SurfaceVariant,
    onTertiaryContainer = FonoDarkColors.OnSurface,

    background = FonoDarkColors.Background,
    onBackground = FonoDarkColors.OnBackground,

    surface = FonoDarkColors.Surface,
    onSurface = FonoDarkColors.OnSurface,
    surfaceVariant = FonoDarkColors.SurfaceVariant,
    onSurfaceVariant = FonoDarkColors.OnSurface,

    error = FonoDarkColors.Error,
    onError = Color.White,
    errorContainer = FonoDarkColors.Error.copy(alpha = 0.2f),
    onErrorContainer = FonoDarkColors.Error,

    outline = FonoDarkColors.Border,
    inverseOnSurface = FonoDarkColors.Surface,
    inverseSurface = FonoDarkColors.OnBackground,
    inversePrimary = FonoDarkColors.PrimaryVariant,

    scrim = Color.Black
)