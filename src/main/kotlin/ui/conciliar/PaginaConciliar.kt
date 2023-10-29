package ui.conciliar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.conciliar.tabs.TabsConciliar

@Composable
fun PaginaConciliar(estado: PaginaConciliarEstado) {
    var indexActual by remember { mutableStateOf(estado.indexActual.value) }

    Row {
        Divider(
            modifier = Modifier.fillMaxHeight().width(1.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        )

        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    modifier = Modifier.width(200.dp).clip(RoundedCornerShape(topEnd = 16.dp)),
                    drawerContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                ) {
                    Impuestos.entries.forEachIndexed { index, operacion ->
                        NavigationDrawerItem(
                            label = { Text(text = operacion.titulo, fontSize = 14.sp) },
                            selected = index == indexActual,
                            onClick = {
                                indexActual = index
                            },
                            modifier = Modifier.padding(8.dp),
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                unselectedContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface
                            ),
                        )
                    }
                }
            }
        ) {
            Surface {
                TabsConciliar(estado.procesos[indexActual], estado.titulo)
            }
        }
    }
}
