<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<nav class="navbar navbar-inverse navbar-fixed-top navbar-custom">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">TESTE DE CONHECIMENTO - EMIOLO </a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li ng-class="classUsuario"><a href="#/usuarios">Usuários</a></li>
				<li ng-class="classNasa"><a href="#/nasa">Nasa</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li>
					<p class="navbar-btn" style="margin-right: 10px;"
						ng-controller="loginController">
						<button class="btn btn-danger" ng-click="logout()">Sair</button>
					</p>
				</li>
			</ul>
		</div>
	</div>
</nav>