package com.github.claaj.konci.ui.acercade

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun contenido(version: String, logo: @Composable () -> Unit, cerrarDialogo: () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        tonalElevation = AlertDialogDefaults.TonalElevation,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                logo()
            }

            Text(
                "Konci",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(version)

            Text("Licencia: MIT")

            Text("Copyright © 2023 Matías Cajal")

            TextButton(
                onClick = { cerrarDialogo() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Cerrar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoAcercaDe(cerrarDialogo: () -> Unit, logo: @Composable () -> Unit, version: String) {
    AlertDialog(
        onDismissRequest = { cerrarDialogo() },
        modifier = Modifier.wrapContentHeight().wrapContentWidth(),
    ) {
        contenido(version = version, logo = { logo() }, cerrarDialogo = { cerrarDialogo() })
    }
}