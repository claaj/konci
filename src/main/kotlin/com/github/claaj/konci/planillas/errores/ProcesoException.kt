package com.github.claaj.konci.planillas.errores

open class ProcesoException(descripcion: String) : Exception(descripcion)

class FormatoInvalidoException(ruta: String) :
    ProcesoException("La planilla presente en $ruta no cumple con el formato necesario para poder ser procesada.")