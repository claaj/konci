package com.github.claaj.konci.ui.conciliar.tabs.lista

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.net.URI
import java.nio.file.LinkOption
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.toPath


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ZonaArrastrarSoltar(agregarRuta: (Path) -> Unit) {
    val extensionesPermitidas = listOf("xls", "xlsx")
    Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        arrastarSoltar(
            onDrop = { dragData ->
                if (dragData is DragData.FilesList) {
                    val archivosArrastrados = dragData.readFiles().mapNotNull { rutaString ->
                        URI(rutaString).toPath().takeIf { it.exists(LinkOption.NOFOLLOW_LINKS) }
                    }

                    archivosArrastrados.forEach { ruta ->
                        if (checkExtensionArchivo(ruta, extensionesPermitidas)) {
                            agregarRuta(ruta)
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun arrastarSoltar(
    onDrop: (DragData) -> Unit,
) {
    var arrastrando by remember { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.onExternalDrag(onDragStart = { arrastrando = true },
            onDragExit = { arrastrando = false },
            onDrop = { valor ->
                arrastrando = false
                onDrop(valor.dragData)
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
                painter = painterResource("drag-drop-fill.svg"),
                modifier = Modifier.size(50.dp).padding(8.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

internal fun checkExtensionArchivo(archivo: Path, extensionesPermitidas: List<String>): Boolean {
    return extensionesPermitidas.contains(archivo.toFile().extension)
}
