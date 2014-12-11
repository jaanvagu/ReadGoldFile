
# ReadGoldFile
Script para evaluar archivo gold de un clasificador
## Requerimientos
- JDK 7+

## Instalación
```
mvn install
mvn package
```

## Uso
Se puede usar de dos maneras:
##### 1. Eligiendo el archivo gold usando JFileCHooser (Ejecutar jar sin parámetros)
- `java -jar ReadGoldFile.jar`

##### 2. Pasando la ruta del archivo como parámetro
- `java -jar ReadGoldFile.jar <path>`

**Nota**: Para la ejecución del jar no se necesita seguir los pasos de instalación.

## Ejemplo de Uso
`java -jar ReadGoldFile.jar /examples/gold_file.txt`
##### Formato (Separación por tab):

`<Etiqueta_Anotada>  <Etiqueta_Estimada> <Flag_Coinciden>  <Mensaje>`

**Nota**: En la carpeta examples se encuentra un archivo de ejemplo.
