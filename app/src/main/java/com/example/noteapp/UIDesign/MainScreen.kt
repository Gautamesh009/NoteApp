@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteapp.DataBase.NoteEntity
import com.example.noteapp.DataBase.NoteRepo
import com.example.noteapp.NoteAppViewModel.NoteViewModel

@Composable
fun MainScreen(viewModel : NoteViewModel, navController: NavController) {

       val notes by viewModel.notes.collectAsState()

        val topAppBarColor = remember { mutableStateOf(Color(0xFF3D91DE)) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Text("Note App",
                                fontSize = 28.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.W200,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    },

                    actions = {
                        IconButton({
                            navController.navigate("noteField")
                        }) {
                            Icon(Icons.Filled.Add,
                                null)
                        }
                        IconButton({}) {
                            Icon(Icons.Filled.Delete,
                                null)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor =topAppBarColor.value
                    )
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .padding(it)
                ) {
                    LazyColumn() {
                        items(notes){
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Card(
                                    onClick = {
                                        navController.navigate("noteElement/${it.noteNum}")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(16.dp)
                                        .height(70.dp),
                                    border = BorderStroke(4.dp, Color.Black)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(16.dp)
                                            .height(50.dp),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            it.title,
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            },
            bottomBar = {
                BottomAppBar {

                }
            }
        )
    }