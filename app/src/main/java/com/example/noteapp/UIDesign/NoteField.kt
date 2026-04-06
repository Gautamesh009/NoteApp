@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import android.R
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteapp.DataBase.NoteEntity
import com.example.noteapp.NoteAppViewModel.NoteViewModel
import com.example.noteapp.UIDesign.Colors.cyberpunkClassic
import com.example.noteapp.UIDesign.Colors.ghostShell
import com.example.noteapp.UIDesign.Colors.neoTokyo
import com.example.noteapp.UIDesign.Colors.toxicWaste

//@Preview
@Composable
fun NoteFieldCom(navController: NavController, viewModel : NoteViewModel) {
    val transition = rememberInfiniteTransition()
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Unknown") }
    val tagOptions = listOf("Important", "Code", "Completed", "Check List")


    // Animate a float that shifts the gradient
    val offsetX by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Create a moving gradient brush
    val animatedBrush = Brush.linearGradient(
        colors = ghostShell,
        start = Offset(offsetX, 0f),
        end = Offset(offsetX + 500f, 500f)
    )


    val titleTextField = remember { mutableStateOf("") }
    val authorTextField = remember { mutableStateOf("") }
    val noteTextField = remember { mutableStateOf("") }

    val topAppBarColor = remember { mutableStateOf(Color(0xFF022F54)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text("Create note",
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.W200,
                            fontStyle = FontStyle.Normal,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton({
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            null
                            , tint = Color.White)
                    }
                },

                actions = {
                    IconButton(onClick = {
                        val save = NoteEntity(
                            title = titleTextField.value,
                            author = authorTextField.value,
                            notes = noteTextField.value,
                            tag =selectedOption
                        )
                        val insertNote = viewModel.insert(save)
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu_save),
                            null, tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor =topAppBarColor.value
                )
            )
        },
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A0F))
                .graphicsLayer(alpha = .8f)) {
                Image(
                    painter = painterResource(id = com.example.noteapp.R.drawable.img_2),
                    contentDescription = "Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = titleTextField.value,
                            onValueChange = { titleTextField.value = it },
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
                    OutlinedTextField(value = authorTextField.value, onValueChange = {
                        authorTextField.value = it
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
                    OutlinedTextField(value = noteTextField.value, onValueChange = {
                        noteTextField.value = it
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
    )
}