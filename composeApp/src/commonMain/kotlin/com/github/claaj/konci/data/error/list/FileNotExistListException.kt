package com.github.claaj.konci.data.error.list

import java.nio.file.Path

class FileNotExistListException(source: String, path: Path) :
    ListException(source, "El archivo ($path) no se encuentra disponible.")
