package data.error.savepath

open class SavePathFileException(description: String): Exception("Error con la carpeta de guardado. $description")
