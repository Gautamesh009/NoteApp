package com.example.noteapp.UIDesign.Colors

import androidx.compose.ui.graphics.Color

// 🔵 Neon Blue & Pink (Classic Cyberpunk)
val cyberpunkClassic = listOf(
    Color(0xFF00F0FF), // Neon Cyan
    Color(0xFF7B2FFF), // Electric Violet
    Color(0xFFFF2079), // Hot Pink
    Color(0xFF00F0FF), // Neon Cyan again (smooth loop)
)

// 🟣 Purple Haze
val purpleHaze = listOf(
    Color(0xFFBF00FF), // Electric Purple
    Color(0xFF6A0FD1), // Deep Violet
    Color(0xFFFF00AA), // Magenta
    Color(0xFF00FFEA), // Teal Glow
)

// 🟢 Matrix Green
val matrixGreen = listOf(
    Color(0xFF00FF41), // Matrix Green
    Color(0xFF00BFFF), // Deep Sky Blue
    Color(0xFF00FF41), // Matrix Green
)

// 🔴 Blade Runner Sunset
val bladeRunner = listOf(
    Color(0xFFFF6B00), // Toxic Orange
    Color(0xFFFF003C), // Neon Red
    Color(0xFFBF00FF), // Electric Purple
    Color(0xFF00F0FF), // Cyan Glow
)

// 🌆 Neo Tokyo
val neoTokyo = listOf(
    Color(0xFFFF00AA), // Hot Pink
    Color(0xFFFFD700), // Gold
    Color(0xFFFF4500), // Red Orange
    Color(0xFFFF00AA), // Hot Pink loop
)

// 🤖 Ghost in the Shell
val ghostShell = listOf(
    Color(0xFF00FFEA), // Teal
    Color(0xFF0080FF), // Electric Blue
    Color(0xFF00FFEA), // Teal
    Color(0xFF7B2FFF), // Violet
)

// ☢️ Toxic Wasteland
val toxicWaste = listOf(
    Color(0xFF39FF14), // Neon Green
    Color(0xFFCCFF00), // Acid Yellow
    Color(0xFF00FF99), // Mint Neon
)

// 🩸 Synthwave
val synthwave = listOf(
    Color(0xFFFF2079), // Hot Pink
    Color(0xFF7B2FFF), // Purple
    Color(0xFF001AFF), // Deep Blue
    Color(0xFFFF2079), // Hot Pink loop
)

fun colorForText() : List<Color> {
    val cPColor : List<List<Color>> = listOf(
        synthwave,
        neoTokyo,
        bladeRunner,
        matrixGreen,
        purpleHaze,
        cyberpunkClassic,
        )

    return cPColor.random()
}