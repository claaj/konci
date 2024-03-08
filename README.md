# Konci
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)

Konci es la reescritura en Kotlin de [Conci](https://github.com/claaj/conci), un ayudante para conciliar retenciones y
percepciones con planillas de AFIP, Convenio Multilateral y Tango.

Actualmente permite conciliar **percepciones** de:
- IIBB
- IVA

Y **retenciones** de:
- Ganancias
- IIBB
- IVA
- SUSS

Para las planilllas de IIBB es necesario utilizar los archivos de texto de [Convenio Multilateral](https://www.ca.gob.ar/convenio-multilateral).

## ¿Cómo se utiliza el programa?
1. Una vez abierto el mismo, en la barra ubicada sobre el margen izquierdo seleccionar el regimen a conciliar.
2. Al hacer click sobre el regimen aperecerá otra barra con los impuestos disponibles para conciliar.
3. Seleccione el impuesto que desea conciliar, haciendo click encima del mismo.
4. En la pantalla principal habrá una barra con dos solapas. Una para los archivos "externos" (AFIP y Conv. Multilateral) y otra para los "locales" (Tango).
5. Cargué los archivos necesarios para procesar. Arrastre el/los archivo/s en la zona indicada en la pantalla. Cargue los archivos externos y locales.
6. Una vez realizado lo anterior, puede pulsar el boton del margen inferior derecho que dice "Procesar" para conciliar los archivos.
7. Se abrirá un explorador que le permitirá elegir la carpeta de guardado del archivo procesado.

## ¿Qué tecnologías se utilizan?
- El programa está desarrollado en Kotlin.
- Para procesar las planillas se utiliza [dataframe](https://github.com/Kotlin/dataframe).
- Para interfaz gráfica se utiliza [Compose](https://www.jetbrains.com/lp/compose-multiplatform/).


> [!WARNING]
> Este programa todavía se encuentra en desarrollo no se recomienda la utilización en producción.
>  Además el mismo viene SIN NINGUNA GARANTÍA.
