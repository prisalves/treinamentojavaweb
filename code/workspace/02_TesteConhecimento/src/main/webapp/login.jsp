<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<link rel="stylesheet" href="resources/css/login.css">

<div ng-show="loading" class="loading">Aguarde...</div>

<div class="container">

  <form name="loginForm" class="form-horizontal form-signin" method="POST">
  
    <h2 class="form-signin-heading">Informe seu usuário</h2>
    
    <div ng-bind-html="msg | unsafe"></div>
    
    <label for="inputUsuario" class="sr-only">Usuário</label>
    <input type="text" id="inputUsuario" ng-model="usuario.login" ng-required="true" class="form-control" placeholder="Usuário" required autofocus>
    
    <label for="inputSenha" class="sr-only">Senha</label>
    <input type="password" id="inputSenha" ng-model="usuario.senha" ng-required="true" class="form-control" placeholder="Senha" required>
    
    <button class="btn btn-lg btn-primary btn-block" ng-click="autenticar(usuario)" ng-disabled="loginForm.$invalid">Enviar</button>
    
  </form>

</div> <!-- /container -->
