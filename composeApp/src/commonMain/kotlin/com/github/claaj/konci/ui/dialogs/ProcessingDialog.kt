package com.github.claaj.konci.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcessingDialog(visible: () -> Unit) {
    BasicAlertDialog(
        onDismissRequest = visible,
    ) {
        Surface(
            modifier = Modifier.wrapContentHeight().wrapContentWidth().width(256.dp).height(256.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(Modifier.height(16.dp))

                Text("Procesando...")
            }
        }
    }
}
