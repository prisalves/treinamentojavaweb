<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Hello Page</title>
</head>
<body>
	
	<form method="post" action="/HelloServletEclipse/server">
		<h2>Pesquisar funcionários:</h2>
		<input type="text" id="say-hello-text-input" name="nome" /> 
		<input type="submit" id="say-hello-button" required="required" value="Pesquisar" />
	</form>
	
	<br/>
	<a href="/HelloServletEclipse/funcionarios" >Funcionários</a>
	
</body>
</html>