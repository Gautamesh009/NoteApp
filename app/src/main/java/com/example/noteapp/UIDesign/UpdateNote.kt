@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.example.noteapp.UIDesign.Colors.cyberpunkClassic
import com.example.noteapp.UIDesign.Colors.ghostShell
import com.example.noteapp.UIDesign.Colors.neoTokyo
import com.example.noteapp.UIDesign.Colors.toxicWaste

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

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Unknown") }
    val tagOptions = listOf("Important", "Code", "Completed", "Check List")

    val transition = rememberInfiniteTransition()

    val offsetX by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val infiniteTransition = rememberInfiniteTransition()

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )

    val animatedBrush = Brush.linearGradient(
        colors = ghostShell,
        start = Offset(offsetX, 0f),
        end = Offset(offsetX + 500f, 500f)
    )

    // 4. Validation state
    var titleError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Update",
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.W200,
                            fontStyle = FontStyle.Normal,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color.Magenta, Color.Cyan, Color(0xFFDA00FF)),
                                    start = Offset(offset, 0f),
                                    end = Offset(offset + 500f, 0f)
                                )
                            )
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(
                            "Note",
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.W200,
                            fontStyle = FontStyle.Normal,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color.Cyan, Color.Magenta, Color.Blue),
                                    start = Offset(offset, 0f),
                                    end = Offset(offset + 500f, 0f)
                                )
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF021F38)
                )
            )
        }
    ) { padding ->
        Box(
            Modifier.fillMaxSize()
                .background(Color(0xFF0A0A0F)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.img_3
                ),
                "Background",
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title",
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = toxicWaste,
                                start = Offset(0f, 0f),
                                end = Offset(500f, 0f)
                            )
                        )
                    ) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color.Cyan,
                        focusedLabelColor = Color.Cyan,
                        unfocusedLabelColor = Color.Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth(.7f)
                        .alpha(0.8f)
                        .drawBehind {
                            // Draw custom animated border
                            drawRoundRect(
                                brush = animatedBrush,
                                style = Stroke(width = 4.dp.toPx()),
                                cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                            )
                        }
                )

                Spacer(Modifier.width(5.dp))

                Box(
                    Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth(.4f)
                        .border(
                            brush = Brush.linearGradient(
                                colors = ghostShell,
                                start = Offset(0f, 0f),
                                end = Offset(500f, 0f)
                            ),
                            width = 2.dp,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { expanded = true }) {
                        if (expanded != true) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Tags",
                                tint = Color.Cyan
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "Tags",
                                tint = Color.Cyan
                            )
                        }
                    }

                    // Cyberpunk DropMenu
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color.Black,
                                        Color(0xFF00E5FF),
                                        Color(0xFFFF4081)
                                    )
                                )
                            )
                            .border(
                                width = 2.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(Color.Cyan, Color.Magenta, Color.Blue)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        tagOptions.forEach { tag ->
                            DropdownMenuItem(
                                text = { Text(tag, color = Color.White) },
                                onClick = {
                                    selectedOption = tag
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(5.dp))
            OutlinedTextField(value = author, onValueChange = {
                author = it
            },
                label = {
                    Text("Author (Optional)",
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = cyberpunkClassic,
                                start = Offset(0f, 0f),
                                end = Offset(500f, 0f)
                            )
                        )
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.Cyan,
                    focusedLabelColor = Color.Cyan,
                    unfocusedLabelColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.8f)
                    .drawBehind {
                        // Draw custom animated border
                        drawRoundRect(
                            brush = animatedBrush,
                            style = Stroke(width = 4.dp.toPx()),
                            cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                        )
                    }
            )
            Spacer(Modifier.height(5.dp))
            OutlinedTextField(value = body, onValueChange = {
                body = it
            },
                label = {
                    Text("Note",
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = neoTokyo,
                                start = Offset(0f, 0f),
                                end = Offset(500f, 0f)
                            )
                        )
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.Cyan,
                    focusedLabelColor = Color.Cyan,
                    unfocusedLabelColor = Color.Gray
                ),
                maxLines = 40,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .shadow(
                        elevation = 2.dp,
                        ambientColor = Color.Cyan,
                        spotColor = Color.Magenta
                    )
                    .alpha(0.8f)
                    .drawBehind {
                        // Draw custom animated border
                        drawRoundRect(
                            brush = animatedBrush,
                            style = Stroke(width = 4.dp.toPx()),
                            cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                        )
                    }
            )
        }
    }
}