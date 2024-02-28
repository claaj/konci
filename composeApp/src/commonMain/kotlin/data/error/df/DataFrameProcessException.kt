package data.error.df

open class DataFrameProcessException(descripcion: String) : Exception(descripcion)

class InvalidFormatException(pathString: String) :
    DataFrameProcessException(
        "La planilla presente en $pathString no cumple con el formato necesario para poder ser procesada."
    )