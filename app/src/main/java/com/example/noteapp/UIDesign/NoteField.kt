@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteapp.DataBase.NoteEntity
import com.example.noteapp.NoteAppViewModel.NoteViewModel

//@Preview
@Composable
fun noteField(navController: NavController, viewModel : NoteViewModel) {


    val titleTextField = remember { mutableStateOf("") }
    val authorTextField = remember { mutableStateOf("") }
    val noteTextField = remember { mutableStateOf("") }

    val topAppBarColor = remember { mutableStateOf(Color(0xFF3D91DE)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text("Create note",
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.W200,
                            fontStyle = FontStyle.Italic
                        )
                    }
                },
                navigationIcon = {
                    IconButton({
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack,
                            null)
                    }
                },

                actions = {
                    IconButton(onClick = {
                        val save = NoteEntity(
                            title = titleTextField.value,
                            author = authorTextField.value,
                            notes = titleTextField.value
                        )
                        val insertNote = viewModel.insert(save)
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu_save),
                            null
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor =topAppBarColor.value
                )
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(it)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(value = titleTextField.value, onValueChange = {
                        titleTextField.value = it
                    },
                        label = {
                            Text("Title")
                        },
                        colors = TextFieldDefaults.colors(
                            Color.Black,
                            Color.Black,
                            Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()

                    )
                    Spacer(Modifier.height(10.dp))
                    TextField(value = authorTextField.value, onValueChange = {
                        authorTextField.value = it
                    },
                        label = {
                            Text("Author (Optional)")
                        },
                        colors = TextFieldDefaults.colors(
                            Color.Black,
                            Color.Black,
                            Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(10.dp))
                    TextField(value = noteTextField.value, onValueChange = {
                        noteTextField.value = it
                    },
                        label = {
                            Text("Note")
                        },
                        colors = TextFieldDefaults.colors(
                            Color.Black,
                            Color.Black ,
                            Color.Black
                        ),
                        maxLines = 40,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    )
                }
            }
        },
        bottomBar = {
            BottomAppBar {

            }
        }
    )
}