package ui.conciliar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.conciliar.tabs.TabsConciliar

@Composable
fun PaginaConciliar(estado: PaginaConciliarEstado) {
    Row {
        Divider(
            modifier = Modifier.fillMaxHeight().width(1.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        )

        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    modifier = Modifier.width(200.dp).clip(RoundedCornerShape(topEnd = 20.dp)),
                    drawerContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                ) {
                    Impuestos.entries.forEachIndexed { index, operacion ->
                        NavigationDrawerItem(
                            label = { Text(text = operacion.titulo, fontSize = 14.sp) },
                            selected = index == estado.indexActual.value,
                            onClick = {
                                estado.indexActual.value = index
                            },
                            modifier = Modifier.padding(10.dp),
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
                TabsConciliar(
                    estado.impuestos[estado.indexActual.value].tabsConciliar,
                    estado.titulo,
                    estado.impuestos[estado.indexActual.value].titulo,
                    estado.impuestos[estado.indexActual.value].setupExternos,
                    estado.impuestos[estado.indexActual.value].setupLocales,
                )
            }
        }
    }
}