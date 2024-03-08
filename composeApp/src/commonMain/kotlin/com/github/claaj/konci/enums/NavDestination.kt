package com.github.claaj.konci.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RequestPage
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavDestination(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    Percepciones("/percepciones", "Percepciones", Icons.Filled.Receipt),
    Retenciones("/retenciones", "Retenciones", Icons.Filled.RequestPage),
    About("/about", "Acerca de", Icons.AutoMirrored.Filled.Help)
}
