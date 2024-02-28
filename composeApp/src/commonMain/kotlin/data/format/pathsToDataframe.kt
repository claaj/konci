package data.format

import data.error.df.InvalidFormatException
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.concat
import java.nio.file.Path
import kotlin.jvm.Throws

@Throws(InvalidFormatException::class)
fun pathsToDataframe(paths: List<Path>, dfProcessing: (Path) -> DataFrame<*>): DataFrame<*> {
    val dfs = mutableListOf<DataFrame<*>>()

    for (path in paths) {
        try {
            val dfProcessed = dfProcessing(path)
            dfs.add(dfProcessed)
        } catch (_: IllegalStateException) {
            throw InvalidFormatException(path.toString())
        }
    }
    return dfs.concat()
}
