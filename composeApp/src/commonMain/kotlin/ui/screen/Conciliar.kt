package ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.Navigator
import ui.components.ImpuestosDrawer
import ui.components.TabsFilePathList
import viewmodel.RegimenViewModel

@Composable
fun Conciliar(vm: RegimenViewModel, navigator: Navigator) {
    ImpuestosDrawer(vm, navigator) {
        Column(modifier = Modifier.padding(10.dp)) {
            val currentImpuesto = vm.currentImpuesto.collectAsState().value
            Text("${currentImpuesto.regimen.name} - ${currentImpuesto.impuesto.name}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.padding(vertical = 5.dp))
            TabsFilePathList(currentImpuesto)
        }
    }
}
