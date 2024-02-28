package data.process

import data.error.savepath.SavePathFileNotExistException
import data.error.savepath.SavePathFileNotIndicatedException
import data.error.savepath.SavePathFileWriteException
import enums.DialogType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import data.error.df.DataFrameProcessException
import data.error.list.EmptyListException
import data.error.list.FileNotExistListException
import data.error.list.ListException
import data.error.savepath.SavePathFileException
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.io.writeExcel
import viewmodel.ImpuestoViewModel
import java.io.File
import java.io.IOException
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

fun dfProcessing(
    vm: ImpuestoViewModel,
    dialog: (DialogType, String) -> Unit,
    openProcessingDialog: () -> Unit,
    closeProcessingDialog: () -> Unit
) {
    var dialogType = DialogType.Success
    var dialogDescription = "Se procesaron correctamente todas las planillas."
    openProcessingDialog()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            checkPreProcessing(vm)
            val df = conciliarFiles(vm.pathsExternal.toList(), vm.pathsLocal.toList(), vm.regimen, vm.impuesto)
            saveDataFrame(vm, df)
        } catch (listExc: ListException) {
            dialogType = DialogType.Warning
            dialogDescription = listExc.message?: ""
        } catch (dfExc: DataFrameProcessException) {
            dialogType = DialogType.Failure
            dialogDescription = dfExc.message?: ""
        } catch (pathExc: SavePathFileException) {
            dialogType = DialogType.Warning
            dialogDescription = pathExc.message?: ""
        } catch (_: IOException) {
            dialogType = DialogType.Failure
            dialogDescription = "Falló la escritura del archivo procesado."
        } finally {
            closeProcessingDialog()
            dialog(dialogType, dialogDescription)
        }
    }
}

private fun saveDataFrame(vm: ImpuestoViewModel, df: DataFrame<*>) {
    val date = getMostRecentDate(df)
    val dateString = localDateToStringYearMonth(date)
    val folderName = "${vm.regimen.name}-${vm.impuesto.name}"
    val savePathFile = Path(vm.pathSaveFile.toString(), folderName, dateString)
    Files.createDirectories(savePathFile)
    val savePathFileName = File(savePathFile.toString(), "$dateString-$folderName.xlsx")
    df.writeExcel(savePathFileName)
}

private fun checkPreProcessing(vm: ImpuestoViewModel) {
    val (sourceExternal, sourceLocal) = vm.impuesto.getSources()
    val pathSaveFile = vm.pathSaveFile ?: throw SavePathFileNotIndicatedException()

    if (!pathSaveFile.exists()) throw SavePathFileNotExistException(pathSaveFile)
    if (!pathSaveFile.toFile().canWrite()) throw SavePathFileWriteException(pathSaveFile)
    if (vm.pathsExternal.isEmpty()) throw EmptyListException(sourceExternal)
    if (vm.pathsLocal.isEmpty()) throw EmptyListException(sourceLocal)

    for (pathExternal in vm.pathsExternal) {
        if (!pathExternal.exists()) throw FileNotExistListException(sourceExternal, pathExternal)
    }

    for (pathLocal in vm.pathsLocal) {
        if (!pathLocal.exists()) throw FileNotExistListException(sourceLocal, pathLocal)
    }
}
