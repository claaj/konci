package viewmodel

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import enums.DialogType
import enums.Impuestos
import enums.Regimen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import data.process.dfProcessing
import moe.tlaster.precompose.viewmodel.ViewModel
import java.nio.file.Path

class ImpuestoViewModel(val impuesto: Impuestos, val regimen: Regimen): ViewModel() {

    private var _tabIndex = MutableStateFlow(0)
    val tabIndex = _tabIndex.asStateFlow()
    fun setTabIndex(index: Int) {
        _tabIndex.value = index
    }

    var pathSaveFile: Path? = null

    val pathsExternal = mutableStateListOf<Path>()

    fun addExternalFile(path: Path) {
        addPathToList(path, pathsExternal, impuesto.getOuterFileExtensions())
    }

    fun removeExternalFile(path: Path) {
        removePathFromList(path, pathsExternal)
    }

    val pathsLocal = mutableStateListOf<Path>()

    fun addLocalFile(path: Path) {
        addPathToList(path, pathsLocal, impuesto.getLocalFileExtensions())
    }

    fun removeLocalFile(path: Path) {
        removePathFromList(path, pathsLocal)
    }

    private fun addPathToList(path: Path, paths: SnapshotStateList<Path>, allowedFileExtensions: List<String>) {
        if (!paths.contains(path) && checkFileExtension(path, allowedFileExtensions)) paths.add(path)
    }

    private fun removePathFromList(path: Path, paths: SnapshotStateList<Path>) {
        if (paths.contains(path)) paths.remove(path)
    }

    private fun checkFileExtension(path: Path, allowedFileExtensions: List<String>): Boolean {
        val pathAsFIle = path.toFile()
        return pathAsFIle.isFile && allowedFileExtensions.contains(pathAsFIle.extension)
    }

    fun processAndSaveDf(
        dialog: (DialogType, String) -> Unit,
        openProcessingDialog: () -> Unit,
        closeProcessingDialog: () -> Unit
    ) {
        dfProcessing(this, dialog, openProcessingDialog, closeProcessingDialog)
    }
}
