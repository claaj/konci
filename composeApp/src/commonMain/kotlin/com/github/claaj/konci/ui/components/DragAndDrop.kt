package com.github.claaj.konci.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.DragData
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.nio.file.Path
import java.nio.file.LinkOption
import java.net.URI
import kotlin.io.path.exists
import kotlin.io.path.toPath
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.ui.*
import konci.composeapp.generated.resources.Res
import konci.composeapp.generated.resources.drag_drop
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@ExperimentalComposeUiApi
@Composable
fun DragDropComponent(addPath: (Path) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        DragDropZone (
            onDrop = { dragData ->
                if (dragData is DragData.FilesList) {
                    val draggedFiles = dragData.readFiles().mapNotNull { pathString ->
                        URI(pathString).toPath().takeIf { it.exists(LinkOption.NOFOLLOW_LINKS) }
                    }
                    draggedFiles.forEach {
                        addPath(it)
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
@Composable
private fun DragDropZone(onDrop: (DragData) -> Unit) {
    var dragging by remember { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.onExternalDrag(onDragStart = { dragging = true },
            onDragExit = { dragging = false },
            onDrop = {
                dragging = false
                onDrop(it.dragData)
            })
    ) {
        Column(
            modifier = Modifier.height(200.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Arrastra y solta tus archivos aquí",
                fontSize = 14.sp,
            )

            Icon(
                painter = painterResource(Res.drawable.drag_drop),
                modifier = Modifier.size(36.dp).padding(8.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}