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

![Captura desde 2024-03-07 23-07-39](https://github.com/claaj/konci/assets/102485147/46f83fa3-c899-4915-b83c-96e9e7648e01)

![Captura desde 2024-03-07 23-08-29](https://github.com/claaj/konci/assets/102485147/6b460ab5-1154-4e52-bf5f-94223c609a33)

![Captura desde 2024-03-07 23-07-51](https://github.com/claaj/konci/assets/102485147/201827b2-d55d-4d3c-99c9-93d39afde1a1)

## ¿Cómo se utiliza el programa?
1. Una vez abierto el mismo, en la barra ubicada sobre el margen izquierdo seleccionar el regimen a conciliar.
2. Al hacer click sobre el regimen aperecerá otra barra con los impuestos disponibles para conciliar.
3. Seleccione el impuesto que desea conciliar, haciendo click encima del mismo.
4. En la pantalla principal habrá una barra con dos solapas. Una para los archivos "externos" (AFIP y Conv. Multilateral) y otra para los "locales" (Tango).
5. Cargue los archivos necesarios para procesar. Arrastre el/los archivo/s en la zona indicada en la pantalla. Asegurese de cargar los archivos externos y locales.
7. Una vez realizado lo anterior, puede pulsar el boton del margen inferior derecho que dice "Procesar" para conciliar los archivos.
8. Se abrirá un explorador que le permitirá elegir la carpeta de guardado para la planilla resultante del proceso.

Para las planilllas de IIBB es necesario utilizar los archivos de texto (`.txt`) de [Convenio Multilateral](https://www.ca.gob.ar/convenio-multilateral).

## ¿Qué tecnologías se utilizan?
- El programa está desarrollado en Kotlin.
- Para procesar las planillas se utiliza [dataframe](https://github.com/Kotlin/dataframe).
- Para interfaz gráfica se utiliza [Compose](https://www.jetbrains.com/lp/compose-multiplatform/).


> [!WARNING]
> Este programa todavía se encuentra en desarrollo no se recomienda la utilización en producción.
>  Además el mismo viene SIN NINGUNA GARANTÍA.
