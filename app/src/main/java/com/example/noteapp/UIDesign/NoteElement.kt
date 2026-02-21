@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.normalizedAngleCos
import androidx.navigation.NavController
import com.example.noteapp.NoteAppViewModel.NoteViewModel

@Composable
fun NoteElement(viewModel: NoteViewModel, navController: NavController, noteNum: Int) {
    val notes = viewModel.notes.collectAsState()
    val note = notes.value.find { it.noteNum == noteNum }

    if (note == null) {
        Text("Loading note")
    return
}

    note?.let { currentValue ->
        val topAppBarColor = remember { mutableStateOf(Color(0xFF3D91DE)) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Text(
                                "Note Details",
                                fontSize = 28.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.W200,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    },

                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigate("mainScreen")
                            }
                        ) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    },

                    actions = {
                        IconButton({
                            viewModel.delete(currentValue)
                            navController.navigate("mainScreen")
                        }) {
                            Icon(
                                Icons.Filled.Delete,
                                null
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = topAppBarColor.value
                    )
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(note?.title ?: String(), fontSize = 50.sp, fontWeight = FontWeight.SemiBold)

                    Spacer(modifier = Modifier.height(10.dp))

                    note.author?.let { text -> Text("Author Name: ${note.author}",
                            fontSize = 10.sp
                        ) }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(note.notes, fontSize = 24.sp)
                }
            }
        )

    }

}