@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package ui.conciliar.tabs.procesar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun dialogoAviso(
    cerrarAlerta: () -> Unit,
    icon: ImageVector,
    titulo: String,
    descripcion: String,
    textoBoton: String,
) {
    AlertDialog(
        onDismissRequest = { cerrarAlerta() },
    ) {
        Surface(
            modifier = Modifier.wrapContentHeight().wrapContentWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(Modifier.padding(15.dp)) {
                Icon(
                    icon,
                    contentDescription = null,
                    Modifier.align(Alignment.CenterHorizontally).size(40.dp)
                )

                Text(
                    titulo,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    descripcion,
                    fontSize = 14.sp
                )

                TextButton(
                    onClick = { cerrarAlerta() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(textoBoton)
                }
            }
        }
    }
}

@Composable
internal fun dialogoError(
    cerrarAlerta: () -> Unit,
    titulo: String,
    descripcion: String,
    textoBoton: String,

    ) {
    AlertDialog(
        onDismissRequest = { cerrarAlerta() },
    ) {
        Surface(
            modifier = Modifier.wrapContentHeight().wrapContentWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = MaterialTheme.colorScheme.errorContainer
        ) {
            Column(Modifier.padding(15.dp)) {
                Icon(
                    Icons.Filled.Error,
                    contentDescription = null,
                    Modifier.align(Alignment.CenterHorizontally).size(40.dp)
                )

                Text(
                    titulo,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    descripcion,
                    fontSize = 14.sp
                )

                TextButton(
                    onClick = { cerrarAlerta() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(textoBoton, color = MaterialTheme.colorScheme.onErrorContainer)
                }
            }
        }
    }
}