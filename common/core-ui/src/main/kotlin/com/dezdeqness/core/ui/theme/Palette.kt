package com.dezdeqness.core.ui.theme

import androidx.compose.ui.graphics.Color

object LightColors {
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

object DarkColors {
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
    primaryColor = LightColors.Primary,
    primaryVariantColor = LightColors.PrimaryVariant,
    secondaryColor = LightColors.Secondary,
    backgroundColor = LightColors.Background,
    surfaceColor = LightColors.Surface,
    surfaceVariantColor = LightColors.SurfaceVariant,
    borderColor = LightColors.Border,
    onPrimaryColor = LightColors.OnPrimary,
    onSecondaryColor = LightColors.OnSecondary,
    onBackgroundColor = LightColors.OnBackground,
    onSurfaceColor = LightColors.OnSurface,
    textPrimaryColor = LightColors.TextPrimary,
    textSecondaryColor = LightColors.TextSecondary,
    textDisabledColor = LightColors.TextDisabled,
    rippleColor = LightColors.Ripple,
    errorColor = LightColors.Error,
    successColor = LightColors.Success,
    warningColor = LightColors.Warning,
    accentColor = LightColors.Accent,
)

fun fonoDarkColors(): AppColors = AppColors(
    primaryColor = DarkColors.Primary,
    primaryVariantColor = DarkColors.PrimaryVariant,
    secondaryColor = DarkColors.Secondary,
    backgroundColor = DarkColors.Background,
    surfaceColor = DarkColors.Surface,
    surfaceVariantColor = DarkColors.SurfaceVariant,
    borderColor = DarkColors.Border,
    onPrimaryColor = DarkColors.OnPrimary,
    onSecondaryColor = DarkColors.OnSecondary,
    onBackgroundColor = DarkColors.OnBackground,
    onSurfaceColor = DarkColors.OnSurface,
    textPrimaryColor = DarkColors.TextPrimary,
    textSecondaryColor = DarkColors.TextSecondary,
    textDisabledColor = DarkColors.TextDisabled,
    rippleColor = DarkColors.Ripple,
    errorColor = DarkColors.Error,
    successColor = DarkColors.Success,
    warningColor = DarkColors.Warning,
    accentColor = DarkColors.Accent,
)
