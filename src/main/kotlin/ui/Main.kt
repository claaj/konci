package ui

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.theme.AppTheme


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Konci",
        icon = painterResource("icon.svg"),
    ) {
        AppTheme {
            app()
        }
    }
}
