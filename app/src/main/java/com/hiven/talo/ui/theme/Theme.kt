package com.hiven.talo.ui.theme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = WhiteBackground,

    secondary = GreenLight,
    onSecondary = WhiteBackground,

    background = WhiteBackground,
    onBackground = TextPrimary,

    surface = WhiteBackground,
    onSurface = TextPrimary
)

@Composable
fun TaloTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography(),
        content = content
    )
}