<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="emiolo">
<head>
	<meta charset="UTF-8" /> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
 
	<title>Teste de Conhecimento - Emiolo</title>
	
	<script src="webjars/jquery/${jquery.version}/jquery.min.js"></script>
	
	<link rel="stylesheet" href="webjars/bootstrap/${bootstrap.version}/css/bootstrap.min.css">
	<link rel="stylesheet" href="webjars/bootstrap/${bootstrap.version}/css/bootstrap-theme.min.css">
	<script src="webjars/bootstrap/${bootstrap.version}/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="webjars/font-awesome/${awesome.version}/css/font-awesome.css"/>
	
	<script src="webjars/angularjs/${angular.version}/angular.min.js"></script>
	<script src="webjars/angularjs/${angular.version}/angular-messages.min.js"></script>
	<script src="webjars/angularjs/${angular.version}/angular-route.min.js"></script>
	<script src="webjars/angularjs/${angular.version}/i18n/angular-locale_pt-br.js"></script>
	
	<link rel="stylesheet" href="webjars/datatables/${datatables.version}/css/jquery.dataTables.min.css">
	<script src="webjars/datatables/${datatables.version}/js/jquery.dataTables.min.js"></script>
	<script src="webjars/angular-datatables/${angular.datatables.version}/angular-datatables.min.js"></script>
	
	<script src="webjars/angular-ui-bootstrap/${angular.ui-bootstrap.version}/ui-bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="resources/css/bootstrap.css">
	<link rel="stylesheet" href="resources/css/app.css">
	<link rel="stylesheet" href="resources/css/style.css">
	
	
	<script src="resources/js/app.js"></script>
	<script src="resources/js/controllers/loginController.js"></script>
	<script src="resources/js/controllers/usuariosController.js"></script>
	<script src="resources/js/controllers/nasaController.js"></script>
	<script src="resources/js/services/eMioloAPIService.js"></script>
	<script src="resources/js/config/routeConfig.js"></script>
	<script src="resources/js/value/configValue.js"></script>
	<script src="resources/js/filters/html.js"></script>
	<script src="resources/js/interceptors/apiInterceptor.js"></script>
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	
</head>
<body>

		<div class="container-fluid" id="topo">
			<div ng-view></div>
		</div>
		
</body>
</html>