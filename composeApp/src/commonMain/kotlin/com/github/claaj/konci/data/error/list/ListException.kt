package com.github.claaj.konci.data.error.list

open class ListException(source: String, description: String) :
    Exception("Error al procesar planillas de $source. $description")
