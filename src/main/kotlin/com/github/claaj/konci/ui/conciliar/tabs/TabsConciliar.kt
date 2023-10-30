package com.github.claaj.konci.ui.conciliar.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.claaj.konci.ui.conciliar.tabs.lista.TabEstadoLista
import com.github.claaj.konci.ui.conciliar.tabs.lista.TabLista
import com.github.claaj.konci.ui.conciliar.tabs.procesar.TabProcesar
import com.github.claaj.konci.ui.conciliar.tabs.procesar.TabProcesarEstado

@Composable
fun TabsConciliar(
    estado: TabsConciliarEstado,
    nombreProceso: String,
) {
    var indexActual by remember { mutableStateOf(estado.indexAcutal.value) }

    Column(
        modifier = Modifier.fillMaxHeight().padding(end = 16.dp, top = 8.dp, start = 16.dp),
    ) {
        Text(
            text = "$nombreProceso - ${estado.tituloImpuesto}",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )

        TabRow(
            selectedTabIndex = indexActual,
            modifier = Modifier.clip(RoundedCornerShape(50)),
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            indicator = { _ ->
                Box {}
            },
            divider = { Box {} }
        ) {
            estado.items.forEachIndexed { index, pagina ->
                val seleccionado = indexActual == index
                Tab(
                    selected = indexActual == index,
                    onClick = { indexActual = index },
                    modifier = if (seleccionado) Modifier.clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                    else Modifier.clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
                    text = {
                        if (seleccionado) Text(
                            text = pagina.nombre,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        else Text(text = pagina.nombre, color = MaterialTheme.colorScheme.onSurface)
                    },
                )
            }
        }
        when (estado.items[indexActual].tipo) {
            TabTipo.PLANILLA -> TabLista(estado.items[indexActual] as TabEstadoLista)
            TabTipo.PROCESAR -> {
                TabProcesar(
                    estado.items[indexActual] as TabProcesarEstado,
                    nombreProceso,
                    estado.impuesto.titulo,
                    estado.impuesto.setupExternos,
                    estado.impuesto.setupLocales,
                )
            }
        }
    }
}
