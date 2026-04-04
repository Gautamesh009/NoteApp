@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteapp.NoteAppViewModel.NoteViewModel

@Composable
fun UpdateScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    noteId: Int                      // ← received from nav
) {
    // 1. Find the existing note
    val notes by viewModel.notes.collectAsState()
    val note = notes.find { it.noteNum == noteId }

    // 2. If note doesn't exist yet, show a loader
    if (note == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // 3. Pre-fill fields with current note values
    var title  by remember { mutableStateOf(note.title  ?: "") }
    var author by remember { mutableStateOf(note.author ?: "") }
    var body   by remember { mutableStateOf(note.notes) }

    // 4. Validation state
    var titleError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Update Note",
                        fontSize = 28.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.W200,
                        fontStyle = FontStyle.Normal,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    // Save button in the top bar
                    IconButton(onClick = {
                        if (title.isBlank()) {
                            titleError = true
                        } else {
                            // 5. Copy the note with updated fields and save
                            val updatedNote = note.copy(
                                title  = title.trim(),
                                author = author.trim().ifBlank { null },
                                notes  = body.trim()
                            )
                            viewModel.update(updatedNote)       // call your update fun
                            navController.navigate("mainScreen") {
                                popUpTo("mainScreen") { inclusive = true }
                            }
                        }
                    }) {
                        Icon(Icons.Filled.Done, contentDescription = "Save", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF022F54)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Title field
            OutlinedTextField(
                value = title,
                onValueChange = { title = it; titleError = false },
                label = { Text("Title") },
                isError = titleError,
                supportingText = {
                    if (titleError) Text("Title cannot be empty", color = MaterialTheme.colorScheme.error)
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    Color.Black,
                    Color.Black,
                    Color.Black
                )
            )

            // Author field (optional)
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author (optional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    Color.Black,
                    Color.Black,
                    Color.Black
                )
            )

            // Body field
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                label = { Text("Note") },
                modifier = Modifier
                    .fillMaxSize(),
                maxLines = 40,
                colors = TextFieldDefaults.colors(
                    Color.Black,
                    Color.Black,
                    Color.Black
                )
            )
        }
    }
}