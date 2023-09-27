package ui.conciliar.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.kotlinx.dataframe.DataFrame
import ui.conciliar.tabs.lista.TabEstadoLista
import ui.conciliar.tabs.lista.TabLista
import ui.conciliar.tabs.procesar.TabEstadoProcesar
import ui.conciliar.tabs.procesar.TabProcesar
import java.nio.file.Path

@Composable
fun TabsConciliar(
    estado: TabsConciliarEstado,
    nombreProceso: String,
    nombreImpuesto: String,
    setupExternos: (List<Path>) -> DataFrame<*>,
    setupLocales: (List<Path>) -> DataFrame<*>,
) {
    Column(
        modifier = Modifier.fillMaxHeight().padding(end = 20.dp, top = 10.dp, start = 20.dp),
    ) {
        Text(
            text = "$nombreProceso - $nombreImpuesto",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 15.dp),
            fontWeight = FontWeight.Bold
        )

        TabRow(
            selectedTabIndex = estado.indexAcutal.value,
            modifier = Modifier.clip(RoundedCornerShape(50)),
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            indicator = { _ ->
                Box {}
            },
            divider = { Box {} }
        ) {
            estado.items.forEachIndexed { index, pagina ->
                val seleccionado = estado.indexAcutal.value == index
                Tab(
                    selected = estado.indexAcutal.value == index,
                    onClick = { estado.indexAcutal.value = index },
                    modifier = if (seleccionado) Modifier.clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                    else Modifier.clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
                    text = {
                        if (seleccionado) Text(
                            text = pagina.nombre,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        else Text(text = pagina.nombre, color = MaterialTheme.colorScheme.onSurface)
                    },
                )
            }
        }
        when (estado.items[estado.indexAcutal.value].tipo) {
            TabTipo.PLANILLA -> TabLista(estado.items[estado.indexAcutal.value] as TabEstadoLista)
            TabTipo.PROCESAR -> TabProcesar(
                estado.items[estado.indexAcutal.value] as TabEstadoProcesar,
                nombreProceso,
                nombreImpuesto,
                setupExternos,
                setupLocales
            )
        }
    }
}
