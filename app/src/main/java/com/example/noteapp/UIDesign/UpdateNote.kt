@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.example.noteapp.DataBase.NoteEntity
import com.example.noteapp.NoteAppViewModel.NoteViewModel
import kotlin.math.round

@Preview
@Composable
fun UpdateNote() {
    val textField = remember { mutableStateOf("") }
    val topAppBarColor = remember { mutableStateOf(Color(0xFF3D91DE)) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text("Update Note",
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.W200,
                            fontStyle = FontStyle.Italic
                        )
                    }
                },
                navigationIcon = {
                    IconButton({

                    }) {
                        Icon(Icons.Filled.ArrowBack,
                            null)
                    }
                },

                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            Icons.Filled.Check,
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
                modifier = Modifier.fillMaxSize()
                    .padding(it)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = textField.value,
                    onValueChange = {
                        textField.value = it
                    },
                    label = {
                        Text("Update")
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = textField.value,
                    onValueChange = {
                        textField.value = it
                    },
                    label = {
                        Text("Update")
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxSize(),
                    value = textField.value,
                    onValueChange = {
                        textField.value = it
                    },
                    label = {
                        Text("Update")
                    }
                )
            }
        },
        bottomBar = {
            BottomAppBar {

            }
        }
    )
}