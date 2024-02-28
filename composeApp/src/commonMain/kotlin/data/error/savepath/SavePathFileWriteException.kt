package data.error.savepath

import java.nio.file.Path

class SavePathFileWriteException(path: Path):
    SavePathFileException("$path. El sistema no permite escribir en esta ruta.")
