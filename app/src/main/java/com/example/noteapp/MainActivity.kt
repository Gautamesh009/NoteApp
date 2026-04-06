package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.noteapp.NoteAppViewModel.NoteViewModel
import com.example.noteapp.UIDesign.*
import com.example.noteapp.ui.theme.NoteAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NoteAppTheme {
                Box(
                    Modifier.fillMaxSize()
                            .background(
                            Brush.linearGradient(
                                colors = listOf(Color.Black, Color(0xFF0D47A1), Color(0xFF880E4F)),
                                start = Offset.Zero,
                                end = Offset.Infinite
                            )
                            ),
                    contentAlignment = Alignment.Center
                ) {
                    AppNavHost()
                }
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val viewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory)

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    // 🎨 Night city vibe gradient backgrounds per route
    val bgBrush = when (currentRoute) {
        "mainScreen" -> Brush.linearGradient(
            listOf(Color(0xFF0F0F0F), Color(0xFF1E88E5)) // dark → neon blue
        )
        "noteField" -> Brush.radialGradient(
            listOf(Color(0xFF1E1E1E), Color(0xFFFF4081)), // smoky → neon pink
            center = Offset(500f, 500f),
            radius = 1200f
        )
        "noteElement/{noteNum}" -> Brush.linearGradient(
            listOf(Color(0xFF121212), Color(0xFF00E5FF)) // deep black → cyan glow
        )
        "updateNote/{noteId}" -> Brush.linearGradient(
            listOf(Color(0xFF0D0D0D), Color(0xFFFFD600)) // night → neon yellow
        )
        else -> Brush.linearGradient(listOf(Color.Black, Color.DarkGray))
    }

    // 🌌 Crossfade background layer (always visible, no white flash)
    Crossfade(
        targetState = bgBrush,
        animationSpec = tween(3000, easing = LinearOutSlowInEasing)
    ) { brush ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        ) {
            // ✅ NavHost only swaps content, background never disappears
            NavHost(
                navController = navController,
                startDestination = "mainScreen"
            ) {
                composable("mainScreen") {
                    MainScreen(viewModel, navController)
                }
                composable("noteField") {
                    NoteFieldCom(navController, viewModel)
                }
                composable(
                    "noteElement/{noteNum}",
                    arguments = listOf(navArgument("noteNum") { type = NavType.IntType })
                ) {
                    val noteNum = it.arguments!!.getInt("noteNum")
                    NoteElement(viewModel, navController, noteNum)
                }
                composable(
                    "updateNote/{noteId}",
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) {
                    val noteId = it.arguments!!.getInt("noteId")
                    UpdateScreen(navController, viewModel, noteId)
                }
            }
        }
    }
}

