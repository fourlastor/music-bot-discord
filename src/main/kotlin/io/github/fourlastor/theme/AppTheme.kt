package io.github.fourlastor.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object AppTheme {
    val colors: Colors = Colors()

    class Colors(
        val material: androidx.compose.material.Colors = lightColors(
            primary = 0xffc5cae9.color(),
            primaryVariant = 0xff9499b7.color(),
            secondary = 0xff3949ab.color(),
            secondaryVariant = 0xff6f74dd.color(),
            onPrimary = Color.Black,
            onSecondary = Color.White,
        ),
    )
}

private fun Long.color() = Color(this)
