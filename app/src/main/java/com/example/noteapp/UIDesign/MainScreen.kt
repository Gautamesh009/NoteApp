@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
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
import com.example.noteapp.UIDesign.Colors.colorForText
@Composable
fun MainScreen(viewModel : NoteViewModel, navController: NavController) {
    val notes by viewModel.notes.collectAsState()
    val topAppBarColor = remember { mutableStateOf(Color(0xFF021F38)) }
    val cardBack = remember { mutableStateOf(Color(0xFF0D324B)) }
    val infiniteTransition = rememberInfiniteTransition()
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text("My",
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.W200,
                            fontStyle = FontStyle.Normal,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Note",
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.W200,
                            fontStyle = FontStyle.Normal,
                            color = Color.Cyan,
                            modifier = Modifier
                                .shadow(
                                    elevation = 4.dp,
                                    ambientColor = Color.White
                                )
                        )
                    }
                        },
                actions = {
                    IconButton({
                        navController.navigate("noteField")
                    }) {
                        Icon(Icons.Outlined.Add,
                            null,
                            tint = Color.White
                        )
                    }
                    IconButton({}) {
                        Icon(Icons.Filled.Delete,
                            null,
                            tint = Color.White
                        )
                    } },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor =topAppBarColor.value
                    )
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Tags:")
                    }
                    LazyColumn() {
                        items(notes){
                            val textColor  = remember(it.noteNum) { colorForText() }
                            val designColor: Color = remember(it.noteNum) { BackgroundColor() }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Card(
                                    onClick = {
                                        navController.navigate("noteElement/${it.noteNum}")
                                    },
                                    colors = CardDefaults.cardColors(
                                        containerColor = cardBack.value
                                    ),
                                    modifier = Modifier
                                        .width(200.dp)
                                        .padding(8.dp)
                                        .height(200.dp),
                                    border = BorderStroke(1.dp, Color.Black)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize()
                                            .padding(16.dp)
                                            .height(50.dp),
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            "~ Tag:",
                                            color = designColor
                                        )
                                        Spacer(Modifier.height(5.dp))
                                        Text(
                                            it.title,
                                            fontSize = 30.sp,
                                            color = designColor,
                                            fontWeight = FontWeight.W300,
                                            fontFamily = FontFamily.Monospace,
                                            fontStyle = FontStyle.Normal
                                        )
                                        Spacer(Modifier.height(5.dp))
                                        Text(
                                            it.author.toString(),
                                            color = designColor.copy(alpha = 0.7f),
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.W300,
                                            fontFamily = FontFamily.Monospace,
                                            fontStyle = FontStyle.Normal
                                        )
                                        Spacer(Modifier.height(5.dp))
                                        Text(
                                            it.notes,
                                            style = TextStyle(
                                               brush = Brush.linearGradient(
                                                    colors = textColor,
                                                   start = Offset(offset, 0f),
                                                   end = Offset(offset + 500f, 0f)
                                                )
                                            ),
                                            maxLines = 2
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }


fun BackgroundColor() : Color {
    val bgColor : List<Color> = listOf(
        Color(0xFFD000FF),
        Color(0xFFEE2F6C),
        Color(0xFF2396CE),
        Color(0xFFE87B73),
        Color(0xFFF3D900),
    )
    return bgColor.random()
}