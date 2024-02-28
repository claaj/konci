import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.components.WindowAppLogo

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Konci",
        icon = WindowAppLogo()
    ) {
        App()
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}