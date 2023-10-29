package ui.conciliar.tabs.procesar.errores

open class RutaGuardadoException(descripcion: String) : Exception("Error con la carpeta de guardado $descripcion")

class RutaGuardadoNoEscrituraException(ruta: String) :
    RutaGuardadoException("($ruta). El sistema no permite escribir en la misma.")

class RutaGuardadoNoExisteException(ruta: String) :
    RutaGuardadoException("($ruta). La ruta de guardado indicada no existe.")

class RutaGuardadoSinSeleccionarException : RutaGuardadoException("No se indico donde se desea guardar.")