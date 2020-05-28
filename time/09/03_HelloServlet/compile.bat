@echo off
SET _JAVA_OPTIONS=
set CATALINA_HOME "C:\WORK\treinamentojavaweb\code\apache-tomcat-9.0.35" /m 

echo -----------------------
set app=app01
echo compilando %app% ...
javac -cp "C:\apache-tomcat-9.0.35\lib\servlet-api.jar" %app%\src\HelloServlet.java
echo criando estrutura de pasta ...
copy %app%\src\HelloServlet.class %app%\WEB-INF\classes
del %app%\src\HelloServlet.class
echo gerando arquivo war ...
cmd /c "cd %app% & jar cf ..\%app%.war *"
echo arquivo %app%.war gerado com sucesso!!


echo -----------------------
set app=app02
echo compilando %app% ...
javac -cp "C:\apache-tomcat-9.0.35\lib\servlet-api.jar" %app%\src\HelloServlet.java
echo criando estrutura de pasta ...
copy %app%\src\HelloServlet.class %app%\WEB-INF\classes
del %app%\src\HelloServlet.class
echo gerando arquivo war ...
cmd /c "cd %app% & jar cf ..\%app%.war *"
echo arquivo %app%.war gerado com sucesso!!
