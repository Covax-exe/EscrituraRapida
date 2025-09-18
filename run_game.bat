@echo off
REM ================================
REM Script para compilar y ejecutar
REM Juego "Escritura Rápida"
REM ================================

REM RUTA DEL SDK DE JAVAFX (cámbiala según donde lo tengas instalado)
set PATH_TO_FX=C:\javafx-sdk-21\lib

REM Crear carpeta de salida si no existe
if not exist out mkdir out

REM Generar lista de archivos Java
dir /s /b src\main\java\*.java > sources.txt

REM Compilar el código
echo Compilando...
javac -encoding UTF-8 --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -d out @sources.txt
if errorlevel 1 (
    echo Error en la compilación
    pause
    exit /b
)

REM Ejecutar el programa
echo Ejecutando el juego...
java --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -cp out app.Main

pause
