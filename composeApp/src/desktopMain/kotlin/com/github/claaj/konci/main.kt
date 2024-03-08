package com.github.claaj.konci

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import konci.composeapp.generated.resources.Res
import konci.composeapp.generated.resources.icon
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Konci",
        icon = painterResource(Res.drawable.icon)
    ) {
        App()
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}
