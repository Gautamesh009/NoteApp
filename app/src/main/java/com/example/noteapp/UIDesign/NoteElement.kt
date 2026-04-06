@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp.UIDesign

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.noteapp.NoteAppViewModel.NoteViewModel
import com.example.noteapp.R
import com.example.noteapp.UIDesign.Colors.ghostShell
import com.example.noteapp.UIDesign.Colors.neoTokyo

@Composable
fun NoteElement(
    viewModel: NoteViewModel,
    navController: NavController,
    noteNum: Int
) {
    val notes = viewModel.notes.collectAsState()
    val note = notes.value.find { it.noteNum == noteNum }

    if (note == null) {
        Text("Loading note")
        return
    }

    note.let { currentValue ->
        val topAppBarColor = remember { mutableStateOf(Color(0xFF022F54)) }
        var showDeleteDialog by remember { mutableStateOf(false) }

        if (showDeleteDialog) {
            CyberpunkDeleteDialog(
                onConfirm = {
                    showDeleteDialog = false
                    viewModel.delete(currentValue)
                    navController.navigate("mainScreen")
                },
                onDismiss = { showDeleteDialog = false }
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        val infiniteTransition = rememberInfiniteTransition(label = "title")
                        val offset by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1000f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(6000, easing = LinearEasing)
                            ),
                            label = "title_offset"
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
                        containerColor = Color(0xFF021F38)
                    )
                )
            },
            content = {
                val infiniteTransition = rememberInfiniteTransition(label = "content")
                val offset by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1000f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing)
                    ),
                    label = "content_offset"
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0A0A0F))
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
                    Text(
                        text = note.title ?: "",
                        fontSize = 50.sp,
                        fontWeight = FontWeight.W700,
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.Cyan, Color.Magenta, Color.Blue)
                            ),
                            shadow = Shadow(
                                color = Color.Magenta.copy(alpha = 0.8f),
                                offset = Offset(2f, 2f),
                                blurRadius = 12f
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    note.author?.let { author ->
                        val infiniteTransition = rememberInfiniteTransition(label = "author")
                        val offset by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1000f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(2000, easing = LinearEasing)
                            ),
                            label = "author_offset"
                        )
                        Text(
                            text = "Author Name: $author",
                            fontSize = 16.sp,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = neoTokyo,
                                    start = Offset(offset, 0f),
                                    end = Offset(offset + 500f, 0f)
                                ),
                                shadow = Shadow(
                                    color = Color.Cyan.copy(alpha = 0.6f),
                                    offset = Offset(1f, 1f),
                                    blurRadius = 8f
                                )
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn {
                        item {
                            val infiniteTransition = rememberInfiniteTransition(label = "notes")
                            val offset by infiniteTransition.animateFloat(
                                initialValue = 0f,
                                targetValue = 1000f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(2000, easing = LinearEasing)
                                ),
                                label = "notes_offset"
                            )
                            Text(
                                text = note.notes,
                                fontSize = 24.sp,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color(0xFF9C27B0).copy(alpha = 0.7f),
                                        offset = Offset(1f, 1f),
                                        blurRadius = 10f
                                    ),
                                    brush = Brush.sweepGradient(
                                        colors = ghostShell,
                                        center = Offset(offset, 0f)
                                    )
                                )
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                CyberpunkExpandableFab(
                    onDelete = { showDeleteDialog = true },
                    onUpdate = { navController.navigate("updateNote/${note.noteNum}") }
                )
            },
            floatingActionButtonPosition = FabPosition.Center
        )
    }
}


@Composable
fun CyberpunkExpandableFab(
    onDelete: () -> Unit,
    onUpdate: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val transition = updateTransition(targetState = expanded, label = "fab_transition")

    val mainRotation by transition.animateFloat(
        label = "main_rotation",
        transitionSpec = { spring(dampingRatio = 0.6f, stiffness = 300f) }
    ) { if (it) 45f else 0f }

    val childScale by transition.animateFloat(
        label = "child_scale",
        transitionSpec = { spring(dampingRatio = 0.5f, stiffness = 400f) }
    ) { if (it) 1f else 0f }

    val childAlpha by transition.animateFloat(
        label = "child_alpha",
        transitionSpec = { tween(150) }
    ) { if (it) 1f else 0f }

    val deleteOffset by transition.animateDp(
        label = "delete_offset",
        transitionSpec = { spring(dampingRatio = 0.55f, stiffness = 380f) }
    ) { if (it) (-80).dp else 0.dp }

    val updateOffset by transition.animateDp(
        label = "update_offset",
        transitionSpec = { spring(dampingRatio = 0.55f, stiffness = 380f) }
    ) { if (it) 80.dp else 0.dp }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    Box(contentAlignment = Alignment.Center) {

        // DELETE button (left)
        Box(
            modifier = Modifier
                .offset(x = deleteOffset)
                .alpha(childAlpha)
                .scale(scale = childScale),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.Red.copy(alpha = 0.4f), Color.Transparent)
                        ),
                        shape = CircleShape
                    )
            )
            FloatingActionButton(
                onClick = { expanded = false; onDelete() },
                containerColor = Color(0xFF1A0010),
                contentColor = Color.Red,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(listOf(Color.Red, Color(0xFFFF006E))),
                        shape = CircleShape
                    )
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color(0xFFFF1744))
            }
        }

        // UPDATE button (right)
        Box(
            modifier = Modifier
                .offset(x = updateOffset)
                .alpha(childAlpha)
                .scale(childScale),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.Cyan.copy(alpha = 0.3f), Color.Transparent)
                        ),
                        shape = CircleShape
                    )
            )
            FloatingActionButton(
                onClick = { expanded = false; onUpdate() },
                containerColor = Color(0xFF001A1A),
                contentColor = Color.Cyan,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(listOf(Color.Cyan, Color(0xFF00E5FF))),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.upgrade),
                    contentDescription = "Update",
                    tint = Color(0xFF00E5FF)
                )
            }
        }

        // MAIN FAB (center)
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.Magenta.copy(alpha = glowAlpha * 0.35f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
            FloatingActionButton(
                onClick = { expanded = !expanded },
                containerColor = Color(0xFF0D0020),
                contentColor = Color.Magenta,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                modifier = Modifier
                    .size(58.dp)
                    .rotate(mainRotation)
                    .border(
                        width = 1.5.dp,
                        brush = Brush.sweepGradient(
                            listOf(Color.Cyan, Color.Magenta, Color(0xFFDA00FF), Color.Cyan)
                        ),
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = if (expanded) "✕" else "⚙",
                    fontSize = 22.sp,
                    color = if (expanded) Color.White else Color.Magenta,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun CyberpunkDeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "dialog_pulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "border_glow"
    )
    val scanlineOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing)
        ),
        label = "scanline"
    )

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color(0xFF040010), shape = RoundedCornerShape(4.dp))
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Red.copy(alpha = glowAlpha),
                            Color.Magenta.copy(alpha = glowAlpha),
                            Color.Red.copy(alpha = glowAlpha)
                        )
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.03f),
                                Color.Transparent
                            ),
                            startY = scanlineOffset,
                            endY = scanlineOffset + 80f
                        )
                    )
            )

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⚠",
                    fontSize = 36.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Red.copy(alpha = 0.9f),
                            blurRadius = 24f,
                            offset = Offset(0f, 0f)
                        )
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "DELETE NOTE",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Red, Color(0xFFFF006E), Color.Red)
                        ),
                        shadow = Shadow(
                            color = Color.Red.copy(alpha = 0.7f),
                            blurRadius = 16f,
                            offset = Offset(0f, 0f)
                        )
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Red.copy(alpha = 0.6f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "This action is irreversible.\nNote will be permanently wiped.",
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFFAAAAAA),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .background(Color(0xFF0A0A1A), shape = RoundedCornerShape(2.dp))
                            .border(
                                width = 1.dp,
                                brush = Brush.linearGradient(
                                    listOf(
                                        Color.Cyan.copy(alpha = 0.5f),
                                        Color(0xFF00E5FF).copy(alpha = 0.5f)
                                    )
                                ),
                                shape = RoundedCornerShape(2.dp)
                            )
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ABORT",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 3.sp,
                            color = Color(0xFF00E5FF)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF3D0000), Color(0xFF1A0010))
                                ),
                                shape = RoundedCornerShape(2.dp)
                            )
                            .border(
                                width = 1.dp,
                                brush = Brush.linearGradient(
                                    listOf(
                                        Color.Red.copy(alpha = glowAlpha),
                                        Color(0xFFFF006E).copy(alpha = glowAlpha)
                                    )
                                ),
                                shape = RoundedCornerShape(2.dp)
                            )
                            .clickable { onConfirm() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "WIPE",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 3.sp,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color.Red, Color(0xFFFF006E))
                                ),
                                shadow = Shadow(
                                    color = Color.Red.copy(alpha = 0.8f),
                                    blurRadius = 12f,
                                    offset = Offset(0f, 0f)
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}