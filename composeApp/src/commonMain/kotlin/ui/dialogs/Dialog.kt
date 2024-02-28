package ui.dialogs

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
import enums.DialogType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialog(type: DialogType, description: String, closeDialog: () -> Unit) {
    val surfaceColor: Color
    val fontColor: Color
    val icon: ImageVector
    val title: String

    when(type) {
        DialogType.Warning -> {
            surfaceColor = MaterialTheme.colorScheme.primaryContainer
            fontColor = MaterialTheme.colorScheme.onPrimaryContainer
            icon = Icons.Filled.Warning
            title = "Proceso no realizado"
        }

        DialogType.Failure -> {
            surfaceColor = MaterialTheme.colorScheme.errorContainer
            fontColor = MaterialTheme.colorScheme.onErrorContainer
            icon = Icons.Filled.Error
            title = "Fallo en el procesamiento"
        }

        DialogType.Success -> {
            surfaceColor = MaterialTheme.colorScheme.primaryContainer
            fontColor = MaterialTheme.colorScheme.onPrimaryContainer
            icon = Icons.Filled.Done
            title = "Proceso realizado con éxito"
        }
    }

    AlertDialog(onDismissRequest = closeDialog) {
        Surface(
            modifier = Modifier.wrapContentHeight().wrapContentWidth().width(512.dp).height(256.dp),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = surfaceColor,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Column(verticalArrangement = Arrangement.Center) {
                    Icon(
                        icon,
                        contentDescription = null,
                        Modifier.align(Alignment.CenterHorizontally).size(48.dp)
                    )

                    Spacer(Modifier.fillMaxWidth().height(8.dp))

                    Text(
                        title,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.fillMaxWidth().height(4.dp))

                    Text(description, fontSize = 14.sp)
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    TextButton(
                        onClick = { closeDialog() },
                    ) {
                        Text("Entendido", color = fontColor)
                    }
                }
            }
        }
    }
}
