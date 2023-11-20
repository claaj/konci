package com.github.claaj.konci.ui.conciliar

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
import com.github.claaj.konci.ui.conciliar.tabs.TabsConciliar

@Composable
fun SeccionConciliar(estado: SeccionConciliarEstado) {
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
                    estado.impuestos.forEachIndexed { index, impuesto ->
                        NavigationDrawerItem(
                            label = { Text(text = impuesto.nombre, fontSize = 14.sp) },
                            selected = index == estado.indexActual,
                            onClick = {
                                estado.indexActual = index
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
                TabsConciliar(estado.impuestosConciliar[estado.indexActual])
            }
        }
    }
}
