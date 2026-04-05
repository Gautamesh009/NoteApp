@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.normalizedAngleCos
import androidx.navigation.NavController
import com.example.noteapp.NoteAppViewModel.NoteViewModel
import com.example.noteapp.R

@Composable
fun NoteElement(viewModel: NoteViewModel, navController: NavController, noteNum: Int) {
    val notes = viewModel.notes.collectAsState()
    val note = notes.value.find { it.noteNum == noteNum }

    if (note == null) {
        Text("Loading note")
    return
}

    note.let { currentValue ->
        val topAppBarColor = remember { mutableStateOf(Color(0xFF022F54)) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        val infiniteTransition = rememberInfiniteTransition()
                        val offset by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1000f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(6000, easing = LinearEasing) // slow shimmer
                            )
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Note",
                                fontSize = 34.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.W200,
                                style = TextStyle(
                                    brush = Brush.linearGradient(
                                        colors = listOf(Color.Cyan, Color.Magenta, Color.Blue),
                                        start = Offset(offset, 0f),
                                        end = Offset(offset + 500f, 0f)
                                    ),
                                    shadow = Shadow(
                                        color = Color.Cyan.copy(alpha = 0.9f),
                                        blurRadius = 18f,
                                        offset = Offset(2f, 2f)
                                    )
                                )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "Detail",
                                fontSize = 34.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.W200,
                                style = TextStyle(
                                    brush = Brush.linearGradient(
                                        colors = listOf(Color.Magenta, Color.Cyan, Color(0xFFDA00FF)),
                                        start = Offset(offset, 0f),
                                        end = Offset(offset + 500f, 0f)
                                    ),
                                    shadow = Shadow(
                                        color = Color.Magenta.copy(alpha = 0.9f),
                                        blurRadius = 20f,
                                        offset = Offset(2f, 2f)
                                    )
                                )
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF021F38) // dark base so neon pops
                    ),

                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigate("mainScreen")
                            }
                        ) {
                            Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                        }
                    },

                    actions = {
                        IconButton({
                            navController.navigate("updateNote/${note.noteNum}")
                        }) {
                            Icon(painter = painterResource(R.drawable.upgrade),
                                null, tint = Color.White)
                        }
                        IconButton({
                            viewModel.delete(currentValue)
                            navController.navigate("mainScreen")
                        }) {
                            Icon(
                                Icons.Filled.Delete,
                                null, tint = Color.White
                            )
                        }
                    },
                )
            },
            content = {
                Box(
                    Modifier.fillMaxSize()
                        .graphicsLayer(alpha = .8f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_1),
                        contentDescription = "Background",
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(note?.title ?: String(), fontSize = 50.sp, fontWeight = FontWeight.W500)

                    Spacer(modifier = Modifier.height(10.dp))

                    note.author?.let { text ->
                        Text("Author Name: ${note.author}",
                            fontSize = 14.sp
                        ) }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(note.notes, fontSize = 24.sp)
                }
            }
        )

    }

}