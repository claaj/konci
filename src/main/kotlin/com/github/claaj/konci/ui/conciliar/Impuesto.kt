package com.github.claaj.konci.ui.conciliar

enum class Impuesto(
    val nombre: String,
    val regimen: Regimen
) {
    GANANCIAS("Ganancias", Regimen.Retenciones),
    IIBB("IIBB", Regimen.Ambos),
    IVA("IVA", Regimen.Ambos),
    SUSS("SUSS", Regimen.Retenciones)
}
