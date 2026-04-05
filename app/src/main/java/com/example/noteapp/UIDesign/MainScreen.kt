@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import com.example.noteapp.NoteAppViewModel.NoteViewModel
import com.example.noteapp.R
import com.example.noteapp.UIDesign.Colors.colorForText
import com.example.noteapp.UIDesign.Colors.cyberpunkClassic
import com.example.noteapp.UIDesign.Colors.neoTokyo
import com.example.noteapp.UIDesign.Colors.toxicWaste

@Composable
fun MainScreen(viewModel: NoteViewModel, navController: NavController) {
    val notes by viewModel.notes.collectAsState()
    val topAppBarColor = remember { mutableStateOf(Color(0xFF021F38)) }
    val cardBack = remember { mutableStateOf(Color(0xFF6B00FF)) }

    val infiniteTransition = rememberInfiniteTransition()
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )
    val glowColor by infiniteTransition.animateColor(
        initialValue = Color.Cyan,
        targetValue = Color.Magenta,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

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
                            "My",
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
                            "Note",
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
                )
            )
        },
        content = { paddingValues ->

            // Background
            Box(modifier = Modifier.fillMaxSize()
                .graphicsLayer(alpha = .8f)) {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Foreground content
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {

                // Tags header
                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .background(Color(0xFF0B07C4).copy(alpha = 0.6f)),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Tags:",
                            fontSize = 20.sp,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = cyberpunkClassic,
                                    start = Offset(offset, 0f),
                                    end = Offset(offset + 500f, 1f)
                                )
                            )
                        )
                    }
                }

                // Recent Notes header
                item {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .background(Color(0xFF0B07C4).copy(alpha = 0.4f)),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Recent Notes:",
                            fontSize = 20.sp,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = toxicWaste,
                                    start = Offset(offset, 0f),
                                    end = Offset(offset + 500f, 1f)
                                )
                            )
                        )
                    }
                }

                // Horizontal recent notes row
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(notes) { note ->
                            val textColor = remember(note.noteNum) { colorForText() }
                            val designColor = remember(note.noteNum) { BackgroundColor() }
                            NoteCard(
                                note = note,
                                textColor = textColor,
                                designColor = designColor.copy(alpha = 1f),
                                glowColor = glowColor,
                                cardBack = cardBack.value,
                                offset = offset,
                                onClick = { navController.navigate("noteElement/${note.noteNum}") }
                            )
                        }
                    }
                }

                item { Spacer(Modifier.height(16.dp)) }

                // All Notes header
                item {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .background(Color(0xFF0B07C4).copy(alpha = 0.4f)),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "All Notes:",
                            fontSize = 20.sp,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = neoTokyo,
                                    start = Offset(offset, 0f),
                                    end = Offset(offset + 500f, 1f)
                                )
                            )
                        )
                    }
                }

                item { Spacer(Modifier.height(10.dp)) }

                // 2-column grid using chunked rows
                val chunkedNotes = notes.chunked(2)
                items(chunkedNotes) { rowNotes ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowNotes.forEach { note ->
                            val textColor = remember(note.noteNum) { colorForText() }
                            val designColor = remember(note.noteNum) { BackgroundColor() }
                            Box(modifier = Modifier.weight(1f)) {
                                NoteCard(
                                    note = note,
                                    textColor = textColor,
                                    designColor = designColor,
                                    glowColor = glowColor,
                                    cardBack = cardBack.value,
                                    offset = offset,
                                    onClick = { navController.navigate("noteElement/${note.noteNum}") },
                                    fillWidth = true
                                )
                            }
                        }
                        // If odd number of notes, fill remaining space
                        if (rowNotes.size == 1) {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("noteField") },
                shape = RoundedCornerShape(50), // circular but stylized
                containerColor = Color(0xFF0D324B), // dark base
                contentColor = Color.White,
                modifier = Modifier
                    .border(
                        width = 3.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Cyan, Color.Magenta),
                            start = Offset(0f, 0f),
                            end = Offset(100f, 100f)
                        ),
                        shape = RoundedCornerShape(50)
                    )
                    .shadow(
                        elevation = 12.dp,
                        ambientColor = Color.Magenta,
                        spotColor = Color.Cyan
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Note",
                    tint = Color(0xFFF6075A), // neon pink accent
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center // bottom-center
    )
}

// Extracted reusable NoteCard composable
@Composable
fun NoteCard(
    note: com.example.noteapp.DataBase.NoteEntity,
    textColor: List<Color>,
    designColor: Color,
    glowColor: Color,
    cardBack: Color,
    offset: Float,
    onClick: () -> Unit,
    fillWidth: Boolean = false
) {
    val modifier = if (fillWidth) {
        Modifier
            .width(250.dp)
            .height(200.dp)
            .padding(8.dp)
            .border(width = 3.dp, color = glowColor, shape = RoundedCornerShape(16.dp))
            .background(Color(0x33000000), RoundedCornerShape(16.dp))
    } else {
        Modifier
            .width(195.dp)
            .height(200.dp)
            .padding(8.dp)
            .border(width = 3.dp, color = glowColor, shape = RoundedCornerShape(16.dp))
            .background(Color(0x33000000), RoundedCornerShape(16.dp))
    }

    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = cardBack.copy(alpha = 0.4f)),
        modifier = modifier,
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("~ Tag:", color = designColor, fontSize = 12.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                note.title,
                fontSize = 22.sp,
                color = designColor,
                fontWeight = FontWeight.W300,
                fontFamily = FontFamily.Monospace,
                fontStyle = FontStyle.Normal
            )
            Spacer(Modifier.height(4.dp))
            Text(
                note.author.toString(),
                color = designColor.copy(alpha = 0.7f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W300,
                fontFamily = FontFamily.Monospace
            )
            Spacer(Modifier.height(4.dp))
            Text(
                note.notes,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = textColor,
                        start = Offset(offset, 0f),
                        end = Offset(offset + 500f, 0f)
                    )
                ),
                maxLines = 2,
                fontSize = 14.sp
            )
        }
    }
}

fun BackgroundColor(): Color {
    return listOf(
        Color(0xFFD000FF),
        Color(0xFFEE2F6C),
        Color(0xFF2396CE),
        Color(0xFFE87B73),
        Color(0xFFF3D900),
    ).random()
}