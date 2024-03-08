package com.github.claaj.konci.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import com.github.claaj.konci.viewmodel.RegimenViewModel

@Composable
fun ImpuestosDrawer(vm: RegimenViewModel, navigator: Navigator, content: @Composable () -> Unit) {
    Row {
        HorizontalDivider(modifier = Modifier.fillMaxHeight().width(1.dp), color = MaterialTheme.colorScheme.surfaceVariant)
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    modifier = Modifier.width(200.dp).clip(RoundedCornerShape(topEnd = 16.dp)),
                    drawerContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                ) {
                    vm.impuestosList().forEach {
                        val selected = vm.currentImpuesto.collectAsState().value.impuesto.route == it.route
                        NavigationDrawerItem(
                            label = { Text(it.name, fontSize = 14.sp) },
                            selected = selected,
                            onClick = { if (!selected) navigator.navigate(it.route) },
                            modifier = Modifier.padding(8.dp),
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                unselectedContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            },
            content = content
        )
        NavHost(navigator, initialRoute = vm.impuestosList().first().route) {
            vm.vmImpuestos.forEach { vmImpuesto ->
                scene(vmImpuesto.impuesto.route) {
                    vm.setCurrentImpuesto(vmImpuesto)
                }
            }
        }
    }
}