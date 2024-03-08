package com.github.claaj.konci

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.github.claaj.konci.ui.theme.AppTheme
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import com.github.claaj.konci.ui.navigation.MainGraph
import com.github.claaj.konci.viewmodel.MainViewModel

@Composable
fun App() = PreComposeApp {
    AppTheme {
        val vm = MainViewModel()
        val navigator = Navigator()
        Surface(Modifier.fillMaxSize()) {
            MainGraph(vm, navigator)
        }
    }
}
