package com.github.claaj.konci.ui.conciliar.tabs.procesar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import org.jetbrains.kotlinx.dataframe.DataFrame
import java.nio.file.Path

@Composable
fun TabProcesar(
    estado: TabProcesarEstado,
    nombreProceso: String,
    nombreImpuesto: String,
    setupExternos: (List<Path>) -> DataFrame<*>,
    setupLocales: (List<Path>) -> DataFrame<*>
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var descripcionDialogo by remember { mutableStateOf("") }
    var tipoDialogo by remember { mutableStateOf(Dialogo.Aviso) }
    var dialogoProcesando by remember { mutableStateOf(false) }
    var mostrarExplorador by remember { mutableStateOf(false) }

    DirectoryPicker(mostrarExplorador) { ruta ->
        estado.rutaGuardado = ruta
        mostrarExplorador = false
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    conciliarActuales(
                        rutaCarpeta = estado.rutaGuardado,
                        nombreCarpeta = "$nombreProceso-$nombreImpuesto",
                        nombreArchivo = "procesado",
                        archivosExternos = estado.listaExterno.toList(),
                        archivosLocales = estado.listaLocal.toList(),
                        formatearExternos = setupExternos,
                        formatearLocales = setupLocales,
                        origenExterno = estado.origenExterno,
                        origenLocal = estado.origenLocal,
                        dialogo = { descripcion, tipo ->
                            descripcionDialogo = descripcion
                            tipoDialogo = tipo
                            mostrarDialogo = true
                        },
                        abrirDialogoProcesando = { dialogoProcesando = true },
                        cerrarDialogoProcesando = { dialogoProcesando = false }
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("Procesar", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Surface {
            Column(modifier = Modifier.padding(8.dp).fillMaxSize()) {
                Text("Carpeta guardado")
                Row(Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                    FilledTonalButton(
                        onClick = { mostrarExplorador = true }
                    ) {
                        Text("Elegir carpeta")
                    }

                    Surface(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp)
                            .clip(RoundedCornerShape(25)).height(38.dp),
                    ) {
                        Text(
                            text = estado.rutaGuardado.orEmpty(),
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 8.dp, top = 4.dp)
                        )
                    }
                }
            }
            if (mostrarDialogo) {
                dialogo(
                    tipo = tipoDialogo,
                    cerrarAlerta = { mostrarDialogo = false },
                    descripcion = descripcionDialogo
                )
            }

            if (dialogoProcesando) {
                dialogoProcesando { dialogoProcesando = false }
            }
        }
    }
}

