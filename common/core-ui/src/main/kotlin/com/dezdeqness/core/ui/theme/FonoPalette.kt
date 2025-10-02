package com.dezdeqness.core.ui.theme

import androidx.compose.ui.graphics.Color

object FonoLightColors {
    val Primary = Color(0xFF2563EB) // vivid blue
    val PrimaryVariant = Color(0xFF1E40AF) // deep navy-blue
    val Secondary = Color(0xFF3B82F6) // bright accent blue
    val Background = Color(0xFFE6F0FA) // soft light blue background
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFDCEAF8) // bluish highlight surface
    val Border = Color(0xFFB0C4DE) // steel blue border
    val OnPrimary = Color.White
    val OnSecondary = Color.White
    val OnBackground = Color(0xFF0F172A) // navy text
    val OnSurface = Color(0xFF1E293B)
    val Error = Color(0xFFEF4444) // red
    val Success = Color(0xFF22C55E) // green
    val Warning = Color(0xFFFACC15) // yellow
    val TextPrimary = Color(0xFF0F172A)
    val TextSecondary = Color(0xFF475569) // slate
    val TextDisabled = Color(0xFF94A3B8) // muted blue-gray
    val Ripple = Color(0x1F000000)
    val Accent = Color(0xFF38BDF8) // cyan accent
}

object FonoDarkColors {
    val Primary = Color(0xFF3B82F6) // bright blue
    val PrimaryVariant = Color(0xFF1E3A8A) // navy blue
    val Secondary = Color(0xFF60A5FA) // soft blue accent
    val Background = Color(0xFF0A192F) // deep navy
    val Surface = Color(0xFF112240) // dark blue surface
    val SurfaceVariant = Color(0xFF1E3A5F) // slate navy
    val Border = Color(0xFF334155) // muted navy-gray
    val OnPrimary = Color.White
    val OnSecondary = Color.White
    val OnBackground = Color(0xFFE2E8F0)
    val OnSurface = Color(0xFFDADADA)
    val Error = Color(0xFFF87171)
    val Success = Color(0xFF34D399)
    val Warning = Color(0xFFFBBF24)
    val TextPrimary = Color(0xFFEDEDED)
    val TextSecondary = Color(0xFF94A3B8)
    val TextDisabled = Color(0xFF64748B)
    val Ripple = Color(0x33FFFFFF)
    val Accent = Color(0xFF38BDF8) // cyan highlight
}

fun fonoLightColors(): AppColors = AppColors(
    primaryColor = FonoLightColors.Primary,
    primaryVariantColor = FonoLightColors.PrimaryVariant,
    secondaryColor = FonoLightColors.Secondary,
    backgroundColor = FonoLightColors.Background,
    surfaceColor = FonoLightColors.Surface,
    surfaceVariantColor = FonoLightColors.SurfaceVariant,
    borderColor = FonoLightColors.Border,
    onPrimaryColor = FonoLightColors.OnPrimary,
    onSecondaryColor = FonoLightColors.OnSecondary,
    onBackgroundColor = FonoLightColors.OnBackground,
    onSurfaceColor = FonoLightColors.OnSurface,
    textPrimaryColor = FonoLightColors.TextPrimary,
    textSecondaryColor = FonoLightColors.TextSecondary,
    textDisabledColor = FonoLightColors.TextDisabled,
    rippleColor = FonoLightColors.Ripple,
    errorColor = FonoLightColors.Error,
    successColor = FonoLightColors.Success,
    warningColor = FonoLightColors.Warning,
    accentColor = FonoLightColors.Accent,
)

fun fonoDarkColors(): AppColors = AppColors(
    primaryColor = FonoDarkColors.Primary,
    primaryVariantColor = FonoDarkColors.PrimaryVariant,
    secondaryColor = FonoDarkColors.Secondary,
    backgroundColor = FonoDarkColors.Background,
    surfaceColor = FonoDarkColors.Surface,
    surfaceVariantColor = FonoDarkColors.SurfaceVariant,
    borderColor = FonoDarkColors.Border,
    onPrimaryColor = FonoDarkColors.OnPrimary,
    onSecondaryColor = FonoDarkColors.OnSecondary,
    onBackgroundColor = FonoDarkColors.OnBackground,
    onSurfaceColor = FonoDarkColors.OnSurface,
    textPrimaryColor = FonoDarkColors.TextPrimary,
    textSecondaryColor = FonoDarkColors.TextSecondary,
    textDisabledColor = FonoDarkColors.TextDisabled,
    rippleColor = FonoDarkColors.Ripple,
    errorColor = FonoDarkColors.Error,
    successColor = FonoDarkColors.Success,
    warningColor = FonoDarkColors.Warning,
    accentColor = FonoDarkColors.Accent,
)
