@echo off
SET _JAVA_OPTIONS=

echo -----------------------
set app=app01
echo compilando %app% ...
javac -classpath "C:\WORK\treinamentojavaweb\code\workspace\servidor\apache-tomcat-9.0.35\lib\servlet-api.jar" %app%\src\HelloServlet.java
echo criando estrutura de pasta ...
mkdir %app%\WEB-INF\classes
copy %app%\src\HelloServlet.class %app%\WEB-INF\classes
del %app%\src\HelloServlet.class
echo gerando arquivo war ...
cmd /c "cd %app% & jar cf ..\%app%.war *"
echo arquivo %app%.war gerado com sucesso!!


REM echo -----------------------
REM set app=app02
REM echo compilando %app% ...
REM javac -classpath "C:\WORK\treinamentojavaweb\code\workspace\servidor\apache-tomcat-9.0.35\lib\servlet-api.jar" %app%\src\HelloServlet.java
REM echo criando estrutura de pasta ...
REM copy %app%\src\HelloServlet.class %app%\WEB-INF\classes
REM del %app%\src\HelloServlet.class
REM echo gerando arquivo war ...
REM cmd /c "cd %app% & jar cf ..\%app%.war *"
REM echo arquivo %app%.war gerado com sucesso!!
