package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
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
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val viewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {

        composable(
            route = "mainScreen",
        ) {
            MainScreen(viewModel, navController)
        }

        composable(
            route = "noteField",
        ) {
            NoteFieldCom(navController, viewModel)
        }

        composable(
            route = "noteElement/{noteNum}",
            arguments = listOf(navArgument("noteNum") { type = NavType.IntType }), // ✅ FIXED
        ) {
            val noteNum = it.arguments!!.getInt("noteNum")
            NoteElement(viewModel, navController, noteNum)
        }

        composable(
            route = "updateNote/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType }),
        ) {
            val noteId = it.arguments!!.getInt("noteId")
            UpdateScreen(navController, viewModel, noteId)
        }
    }
}