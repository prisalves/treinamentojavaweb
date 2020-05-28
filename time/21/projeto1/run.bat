@echo off
SET _JAVA_OPTIONS=
REM ---- COMPILAR 
echo compilando ...
javac HelloJavaMysql.java
REM ---- RODAR java através da JVM Java Virtual Machine
echo executando aplicacao ...
java -cp .;mysql-connector-java-8.0.19.jar HelloJavaMysql