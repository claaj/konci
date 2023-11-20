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
import com.github.claaj.konci.ui.conciliar.tabs.lista.TabLista
import com.github.claaj.konci.ui.conciliar.tabs.procesar.conciliarActuales
import javax.swing.JFileChooser

@Composable
fun TabsConciliar(
    estado: TabsConciliarEstado,
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var descripcionDialogo by remember { mutableStateOf("") }
    var tipoDialogo by remember { mutableStateOf(Dialogo.Aviso) }
    var dialogoProcesando by remember { mutableStateOf(false) }

    val elegirCarpeta = JFileChooser().apply {
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        dialogTitle = "Seleccione una carpeta de guardado"
        approveButtonText = "Seleccionar"
        approveButtonToolTipText = "Seleccionar la carpeta actual como la ruta de guardado"
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    var rutaGuardado: String? = null
                    elegirCarpeta.selectedFile = null
                    elegirCarpeta.showOpenDialog(null)
                    if (elegirCarpeta.selectedFile != null) {
                        rutaGuardado = elegirCarpeta.selectedFile.toPath().toString()
                    }

                    conciliarActuales(
                        rutaCarpeta = rutaGuardado,
                        nombreCarpeta = "${estado.regimen.name}-${estado.impuesto.nombre}",
                        nombreArchivo = "procesado",
                        regimen = estado.regimen,
                        impuesto = estado.impuesto,
                        archivosExternos = estado.listaExterna.toList(),
                        archivosLocales = estado.listaLocal.toList(),
                        origenExterno = estado.origenExterno,
                        origenLocal = estado.origenLocal,
                        dialogo = { descripcion, tipo ->
                            descripcionDialogo = descripcion
                            tipoDialogo = tipo
                            mostrarDialogo = true
                        },
                        abrirDialogoProcesando = { dialogoProcesando = true },
                        cerrarDialogoProcesando = { dialogoProcesando = false },
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("Procesar", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().padding(end = 16.dp, top = 8.dp, start = 16.dp),
        ) {
            Text(
                text = "${estado.regimen.name} - ${estado.impuesto.nombre}",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                fontWeight = FontWeight.Bold
            )

            TabRow(
                selectedTabIndex = estado.indexActual,
                modifier = Modifier.clip(RoundedCornerShape(50)),
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                indicator = { Box {} },
                divider = { Box {} }
            ) {
                tabDecorada(
                    onClick = { estado.indexActual = 0 },
                    seleccionado = estado.indexActual == 0,
                    estado.origenExterno
                )
                tabDecorada(
                    onClick = { estado.indexActual = 1 },
                    seleccionado = estado.indexActual == 1,
                    estado.origenLocal
                )
            }

            when (estado.indexActual) {
                0 -> TabLista(estado.listaExterna, estado.extensionesExternos)
                1 -> TabLista(estado.listaLocal, estado.extensionesLocales)
                else -> Text("Error de index.")
            }

            if (mostrarDialogo) {
                dialogo(
                    tipo = tipoDialogo,
                    cerrarAlerta = { mostrarDialogo = false },
                    descripcion = descripcionDialogo
                )

                estado.impuesto
            }

            if (dialogoProcesando) {
                dialogoProcesando { dialogoProcesando = false }
            }
        }
    }
}

@Composable
internal fun tabDecorada(onClick: () -> Unit, seleccionado: Boolean, titulo: String) {
    Tab(
        selected = seleccionado,
        onClick = onClick,
        modifier = if (seleccionado) Modifier.clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.primaryContainer)
        else Modifier.clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
        text = {
            if (seleccionado) Text(
                text = titulo,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            else Text(text = titulo, color = MaterialTheme.colorScheme.onSurface)
        },
    )
}
