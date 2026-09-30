package com.jebygold.new.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val JebyGoldDarkColorScheme = darkColorScheme(
    primary = Gold,
    onPrimary = Black,
    primaryContainer = BlackSurfaceElevated,
    onPrimaryContainer = GoldLight,
    secondary = Silver,
    onSecondary = Black,
    background = Black,
    onBackground = TextWhite,
    surface = BlackSurface,
    onSurface = TextWhite,
    outline = Gold
)

@Composable
fun JebyGoldTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = JebyGoldDarkColorScheme,
        content = content
    )
}
