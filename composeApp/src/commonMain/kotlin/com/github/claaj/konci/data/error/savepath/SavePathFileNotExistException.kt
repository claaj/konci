package com.github.claaj.konci.data.error.savepath

import java.nio.file.Path

class SavePathFileNotExistException(path: Path):
    SavePathFileException("$path. La ruta de guardado indicada no existe.")
