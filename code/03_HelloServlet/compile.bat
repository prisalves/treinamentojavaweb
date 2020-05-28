@echo off
SET _JAVA_OPTIONS=

echo -----------------------
set app=app01
echo compilando %app% ...

set CATALINA_HOME "C:\WORK\treinamentojavaweb\code\apache-tomcat-9.0.35" /m 

javac -classpath "%CATALINA_HOME%\lib\servlet-api.jar" %app%\src\HelloServlet.java

echo criando estrutura de pasta ...
copy %app%\src\HelloServlet.class %app%\WEB-INF\classes
REM del %app%\src\HelloServlet.class


echo gerando arquivo war ...
cmd /c "cd %app% & jar cf ..\%app%.war *"
echo arquivo %app%.war gerado com sucesso!!
