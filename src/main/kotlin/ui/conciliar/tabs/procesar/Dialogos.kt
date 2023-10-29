package ui.conciliar.tabs.procesar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Dialogo {
    Aviso,
    Error,
    Exito,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun dialogo(
    tipo: Dialogo,
    cerrarAlerta: () -> Unit,
    descripcion: String,
) {

    val colorSurface: Color
    val colorLetra: Color
    val icono: ImageVector
    val titulo: String

    when (tipo) {
        Dialogo.Aviso -> {
            colorSurface = MaterialTheme.colorScheme.primaryContainer
            colorLetra = MaterialTheme.colorScheme.onPrimaryContainer
            icono = Icons.Filled.Warning
            titulo = "Proceso no realizado"
        }

        Dialogo.Error -> {
            colorSurface = MaterialTheme.colorScheme.errorContainer
            colorLetra = MaterialTheme.colorScheme.onErrorContainer
            icono = Icons.Filled.Error
            titulo = "Fallo en el procesamiento"
        }

        Dialogo.Exito -> {
            colorSurface = MaterialTheme.colorScheme.primaryContainer
            colorLetra = MaterialTheme.colorScheme.onPrimaryContainer
            icono = Icons.Filled.Done
            titulo = "Proceso ejecutado con éxito"
        }
    }

    AlertDialog(
        onDismissRequest = { cerrarAlerta() },
    ) {
        Surface(
            modifier = Modifier.wrapContentHeight().wrapContentWidth().width(512.dp).height(256.dp),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = colorSurface,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Column(verticalArrangement = Arrangement.Center) {
                    Icon(
                        icono,
                        contentDescription = null,
                        Modifier.align(Alignment.CenterHorizontally).size(48.dp)
                    )

                    Spacer(Modifier.fillMaxWidth().height(8.dp))

                    Text(
                        titulo,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.fillMaxWidth().height(4.dp))

                    Text(
                        descripcion,
                        fontSize = 14.sp
                    )
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    TextButton(
                        onClick = { cerrarAlerta() },
                    ) {
                        Text("Entendido", color = colorLetra)
                    }
                }
            }
        }
    }
}
