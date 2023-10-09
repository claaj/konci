package ui.conciliar.tabs.procesar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.kotlinx.dataframe.DataFrame
import java.nio.file.Path
import javax.swing.JFileChooser

@Composable
fun TabProcesar(
    estado: TabProcesarEstado,
    nombreProceso: String,
    nombreImpuesto: String,
    setupExternos: (List<Path>) -> DataFrame<*>,
    setupLocales: (List<Path>) -> DataFrame<*>
) {
    var dialogoAlerta by remember { mutableStateOf(false) }
    var dialogoArchivoNoEncontrado by remember { mutableStateOf(false) }
    var dialogoFormatoInvalido by remember { mutableStateOf(false) }
    var dialogoTodoOk by remember { mutableStateOf(false) }

    val elegirArchivo = JFileChooser().apply {
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        dialogTitle = "Seleccione una carpeta de guardado"
        approveButtonText = "Seleccionar"
        approveButtonToolTipText = "Seleccionar la carpeta actual como la ruta de guardado"
    }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    conciliarActuales(
                        rutaCarpeta = estado.rutaGuardado.value,
                        nombreCarpeta = "$nombreProceso-$nombreImpuesto",
                        nombreArchivo = "procesado",
                        archivosExternos = estado.listaExterno.toList(),
                        archivosLocales = estado.listaLocal.toList(),
                        setupExternos = setupExternos,
                        setupLocales = setupLocales,
                        dialogoFaltanDatos = { dialogoAlerta = true },
                        dialogoArchivoNoEncotrado = { dialogoArchivoNoEncontrado = true },
                        dialogoFormatoInvalido = { dialogoFormatoInvalido = true },
                        dialogoTodoOk = { dialogoTodoOk = true },
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
            Column(modifier = Modifier.padding(10.dp).fillMaxSize()) {
                Text("Carpeta guardado")
                Row(Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                    FilledTonalButton(
                        onClick = {
                            elegirArchivo.showOpenDialog(null)
                            if (elegirArchivo.selectedFile != null) {
                                estado.rutaGuardado.value = elegirArchivo.selectedFile.toString()
                            }
                        }
                    ) {
                        Text("Elegir carpeta")
                    }

                    Surface(
                        modifier = Modifier.fillMaxWidth().padding(top = 5.dp, start = 15.dp)
                            .clip(RoundedCornerShape(25)).height(38.dp),
                    ) {
                        Text(
                            text = estado.rutaGuardado.value,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 10.dp, top = 5.dp)
                        )
                    }
                }
            }
            if (dialogoAlerta) {
                dialogoAviso(
                    cerrarAlerta = { dialogoAlerta = false },
                    icon = Icons.Filled.Warning,
                    titulo = "Proceso no realizado",
                    descripcion = "Esto se debe a la falta de archivos para procesar y/o de una carpeta de guardado.",
                    "Entendido"
                )
            }

            if (dialogoArchivoNoEncontrado) {
                dialogoError(
                    cerrarAlerta = { dialogoArchivoNoEncontrado = false },
                    titulo = "Error al procesar las planillas",
                    descripcion = "Algunas de las planillas ya no se encuentran en la ruta indicada, o la ruta de guardado ya no existe.",
                    textoBoton = "Entendido"
                )
            }

            if (dialogoFormatoInvalido) {
                dialogoError(
                    cerrarAlerta = { dialogoFormatoInvalido = false },
                    titulo = "Error al procesar las planillas",
                    descripcion = "Alguna(s) de la(s) planilla(s) poseen un formato inválido para la operación e impuesto seleccionados.",
                    textoBoton = "Entendido"
                )
            }

            if (dialogoTodoOk) {
                dialogoAviso(
                    cerrarAlerta = { dialogoTodoOk = false },
                    icon = Icons.Filled.Done,
                    titulo = "Proceso realizado con éxito",
                    descripcion = "Todas las planillas fueron procesadas y guardadas correctamente.",
                    textoBoton = "Entendido",
                )
            }
        }
    }
}

