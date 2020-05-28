@echo off
SET _JAVA_OPTIONS=
REM ---- COMPILAR 
echo compilando ...

javac HelloJava.java

REM ---- RODAR java através da JVM Java Virtual Machine
echo executando aplicacao ...

java HelloJava %1
