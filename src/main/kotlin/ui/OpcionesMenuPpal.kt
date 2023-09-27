package ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RequestPage
import androidx.compose.ui.graphics.vector.ImageVector

enum class OpcionesMenuPpal(val titulo: String, val icono: ImageVector) {
    PERCEPCIONES("Percepciones", Icons.Filled.Receipt),
    RETENCIONES("Retenciones", Icons.Filled.RequestPage),

    // Dejar al final acerca de
    ACERCADE("Acerca de", Icons.Filled.Help)
}
