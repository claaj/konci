package com.github.claaj.konci.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.nio.file.Path


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FilePathList(filePaths: SnapshotStateList<Path>, addPath: (Path) -> Unit, removePath: (Path) -> Unit) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            DragDropComponent(
                addPath = { addPath(it) },
            )
        }

        items(filePaths) { path ->
            ListElement(path) {
                removePath(it)
            }
        }
    }
}

@Composable
fun ListElement(filePath: Path, removePath: (Path) -> Unit) {
    val fileName = filePath.toFile().name
    Surface(
        modifier = Modifier.padding(8.dp).height(80.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
        ) {
            Text(
                text = fileName,
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f).align(alignment = Alignment.CenterVertically).padding(8.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Box(
                modifier = Modifier.padding(8.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                IconButton(
                    onClick = { removePath(filePath) },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Eliminar planilla"
                    )
                }
            }
        }
    }
}
