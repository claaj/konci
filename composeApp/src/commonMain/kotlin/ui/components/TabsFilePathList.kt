package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import enums.DialogType
import ui.dialogs.Dialog
import ui.dialogs.ProcessingDialog
import viewmodel.ImpuestoViewModel
import java.io.File

@Composable
fun TabsFilePathList(vm: ImpuestoViewModel) {
    var showFilePicker by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var processing by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var dialogType by remember { mutableStateOf(DialogType.Warning) }
    val tabs = vm.impuesto.getSources()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showFilePicker = true },
                containerColor = MaterialTheme.colorScheme.primary,
                content = { Text("Procesar", color = MaterialTheme.colorScheme.onPrimary) },
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column {
            val tabIndex = vm.tabIndex.collectAsState().value
            TabRow(
                modifier = Modifier.clip(RoundedCornerShape(50)),
                selectedTabIndex = tabIndex,
                indicator = { Box {} },
                divider = { Box {} },
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    DecoratedTab(
                        title = title,
                        selected = tabIndex == index,
                        onClick = { vm.setTabIndex(index) }
                    )
                }
            }

            when(tabIndex) {
                0 -> FilePathList(
                    filePaths = vm.pathsExternal,
                    addPath = { vm.addExternalFile(it) },
                    removePath = { vm.removeExternalFile(it) }
                )
                1 -> FilePathList(
                    filePaths = vm.pathsLocal,
                    addPath = { vm.addLocalFile(it) },
                    removePath = { vm.removeLocalFile(it) }
                )
            }

            if (showDialog) Dialog(dialogType, description) { showDialog = false }

            if (processing) ProcessingDialog { processing = false }


            DirectoryPicker(showFilePicker) { path ->
                vm.pathSaveFile = if (path != null) File(path).toPath()
                else null

                vm.processAndSaveDf(
                    dialog = { type, desc ->
                        description = desc
                        dialogType = type
                        showDialog = true
                    },
                    openProcessingDialog = { processing = true },
                    closeProcessingDialog = { processing = false },
                )

                showFilePicker = false
            }
        }
    }
}

@Composable
private fun DecoratedTab(title: String, selected: Boolean, onClick: () -> Unit) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = if (selected) Modifier.clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.primaryContainer)
        else Modifier.clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
        text = {
            if (selected) Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            else Text(text = title, color = MaterialTheme.colorScheme.onSurface)
        },
    )
}
