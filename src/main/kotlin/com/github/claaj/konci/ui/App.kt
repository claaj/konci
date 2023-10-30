package com.github.claaj.konci.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TableView
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.claaj.konci.ui.acercade.DialogoAcercaDe
import com.github.claaj.konci.ui.conciliar.PaginaConciliar
import com.github.claaj.konci.ui.conciliar.PaginaConciliarEstado


@Composable
@Preview
fun app() {
    val opcionesMenu = OpcionesMenuPpal.entries
    var itemSeleccionado by remember { mutableStateOf(0) }
    var itemAux by remember { mutableStateOf(0) }
    var dialogoAcercaDe by remember { mutableStateOf(false) }
    val estadoPercepciones by remember { mutableStateOf(PaginaConciliarEstado("Percepciones")) }
    val estadoRetenciones by remember { mutableStateOf(PaginaConciliarEstado("Retenciones")) }


    Row {
        NavigationRail(
            modifier = Modifier.width(110.dp).fillMaxHeight().verticalScroll(rememberScrollState()),
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            header = {
                LogoApp(40.dp)
            }
        ) {
            opcionesMenu.forEachIndexed { index, opcion ->
                if (opcion == OpcionesMenuPpal.ACERCADE) Spacer(Modifier.weight(1f))
                OpcionMenu(opcion, index == itemSeleccionado) {
                    itemSeleccionado = index
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            color = MaterialTheme.colorScheme.background,
        ) {
            when (opcionesMenu[itemSeleccionado]) {
                OpcionesMenuPpal.PERCEPCIONES -> {
                    itemAux = itemSeleccionado
                    PaginaConciliar(estadoPercepciones)
                }

                OpcionesMenuPpal.RETENCIONES -> {
                    itemAux = itemSeleccionado
                    PaginaConciliar(estadoRetenciones)
                }

                OpcionesMenuPpal.ACERCADE -> {
                    itemSeleccionado = itemAux
                    dialogoAcercaDe = true
                }
            }

            if (dialogoAcercaDe) {
                DialogoAcercaDe(
                    cerrarDialogo = {
                        dialogoAcercaDe = false
                    },
                    logo = { LogoApp(40.dp) },
                    version = "0.0.1-BETA"
                )
            }
        }
    }
}

@Composable
internal fun OpcionMenu(opcion: OpcionesMenuPpal, seleccionado: Boolean, onClick: () -> Unit) {
    NavigationRailItem(
        icon = { Icon(opcion.icono, contentDescription = null) },
        label = { Text(opcion.titulo) },
        selected = seleccionado,
        onClick = onClick,
        modifier = Modifier.padding(12.dp),
        colors = NavigationRailItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
internal fun LogoApp(tamanio: Dp) {
    Box(
        modifier = Modifier.padding(top = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Filled.TableView,
            modifier = Modifier.size(tamanio),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
