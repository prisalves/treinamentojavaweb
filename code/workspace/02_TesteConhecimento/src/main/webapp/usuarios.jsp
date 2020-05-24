<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div ng-show="loading" class="loading" >Aguarde...</div>

<div ng-include="'template/menu.jsp'"></div>
<div ng-include="'template/modal.jsp'"></div>


	<div class="jumbotron lista_usuarios" >
		<div class="container">
			<h3>
				LISTA DE USUARIOS - <span style="font-size: 15px;" ng-bind="msg_home">{{msg_home}} </span>
			</h3>

			<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addUsuario">
			  Adicionar novo usuário
			</button>
			
			<div id="result-modal"></div>

			<br />
			
			<div ng-controller="usuariosController as lista">
			    <table id="tableUsuarios" datatable="" dt-instance="lista.dtInstance" dt-options="lista.dtOptions" dt-columns="lista.dtColumns" class="table table-hover row-border hover"></table>
			</div>
			
		</div>
	</div>

