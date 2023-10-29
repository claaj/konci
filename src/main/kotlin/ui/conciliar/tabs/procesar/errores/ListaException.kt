package ui.conciliar.tabs.procesar.errores

open class ListaException(origen: String, descripcion: String) :
    Exception("Error al procesar planillas de $origen. $descripcion")

class ListaVaciaException(origen: String) : ListaException(origen, "No se cargó ninguna planilla.")

class ListaArchivoNoExiste(origen: String, ruta: String) :
    ListaException(origen, "El archivo ($ruta) no se encuentra disponible.")