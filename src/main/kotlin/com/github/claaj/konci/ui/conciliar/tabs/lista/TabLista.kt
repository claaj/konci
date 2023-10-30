package com.github.claaj.konci.ui.conciliar.tabs.lista

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.nio.file.Path


@Composable
fun TabLista(estado: TabEstadoLista) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            ZonaArrastrarSoltar { ruta ->
                estado.items.add(ruta)
            }
        }

        items(estado.items.toList()) { ruta ->
            ElementoLista(ruta) {
                if (estado.items.contains(it)) {
                    estado.items.remove(it)
                }

            }
        }
    }
}

@Composable
private fun ElementoLista(ruta: Path, eliminarRuta: (Path) -> Unit) {
    val nombreArchivo = ruta.toFile().name
    Surface(
        modifier = Modifier.padding(8.dp).height(80.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
        ) {
            Text(
                text = nombreArchivo,
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
                    onClick = { eliminarRuta(ruta) },
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
