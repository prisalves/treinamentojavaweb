<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
java.util.Date date = new java.util.Date();
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
%>
<html>
<head>
<title>Funcionários</title>
</head>
<body>
	<h2>Funcionário encontrado!</h2>

	<b>Nome:</b> ${nome}
	<br/>
	<b>Celular:</b> ${celular}

	<br/><br/><br/>
	Pesquisa realizada: <%=sdf.format(date)%>
	<br/> <a href="#" onclick="history.go(-1)">Nova Pesquisa</a>
	
	
</body>
</html>