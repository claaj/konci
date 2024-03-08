package com.github.claaj.konci.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.claaj.konci.ui.components.AppLogo
import com.github.claaj.konci.ui.components.HyperlinkText

@Composable
fun About(version: String) {
    Box(
        modifier = Modifier.padding(128.dp).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 2.dp
        ) {
            AboutContent(version, 128.dp)
        }
    }
}

@Composable
private fun AboutContent(version: String, size: Dp) {
    Column(
        modifier = Modifier.padding(32.dp).wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogo(size)
        Text(
            text = "Konci",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(version)
        HyperlinkText(
            fullText = " Página web ",
            hyperLinks = mutableMapOf(
                "Página web" to "https://github.com/claaj/konci"
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Este programa viene SIN NINGUNA GARANTíA", fontSize = 12.sp)
        HyperlinkText(
            fullText = "Consulte la licencia MIT para obtener más detalles.",
            hyperLinks = mutableMapOf(
                "la licencia MIT" to "https://opensource.org/license/mit"
            ),
            fontSize = 12.sp
        )
    }
}
