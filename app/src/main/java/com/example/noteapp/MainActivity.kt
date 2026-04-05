package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.NoteAppViewModel.NoteViewModel
import com.example.noteapp.UIDesign.MainScreen
import com.example.noteapp.UIDesign.NoteElement
import com.example.noteapp.UIDesign.NoteFieldCom
import com.example.noteapp.UIDesign.UpdateScreen
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
    val navController : NavHostController = rememberNavController()
    val viewModel : NoteViewModel = viewModel(factory = NoteViewModel.Factory)

    NavHost(navController, startDestination = "mainScreen") {
        composable("mainScreen",
        ) {
            MainScreen(viewModel, navController)
        }
        composable("noteField") {
            NoteFieldCom(navController, viewModel)
        }
        composable("noteElement/{noteNum}",
            arguments = listOf(
                navArgument("noteNum") {
                    type = NavType.IntType
                }
            )
        ) {
            val noteNum = it.arguments!!.getInt("noteNum")
            NoteElement(viewModel, navController, noteNum)
        }
        // ✅ After — extract noteId and pass it down
        composable(
            "updateNote/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) {
            val noteId = it.arguments!!.getInt("noteId")
            UpdateScreen(navController, viewModel, noteId)
        }
    }
}