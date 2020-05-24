<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Modal ADD USUARIO -->
<div class="modal fade" id="addUsuario" tabindex="-1" role="dialog" aria-labelledby="Adicionar Usuário" >
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Adicionar Usuário</h4>
      </div>
      <form name="usuarioForm" >
      <div class="modal-body">
      
        {{usuario}}
      
        <label for="inputNome" class="sr-only">Nome</label>
    	<input type="text" id="inputNome" ng-model="usuario.nome" ng-required="true" class="form-control" placeholder="Nome" required autofocus>
        
        <label for="inputUsuario" class="sr-only">Usuário</label>
    	<input type="text" id="inputUsuario" ng-model="usuario.login" ng-required="true" class="form-control" placeholder="Usuário" required >
        
        <label for="inputSenha" class="sr-only">Senha</label>
    	<input type="text" id="inputSenha" ng-model="usuario.senha" ng-required="true" class="form-control" placeholder="Senha" required autofocus>
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
        <button type="submit" class="btn btn-primary" ng-click="adicionar(usuario)" ng-disabled="usuarioForm.$invalid">Cadastrar</button>
      </div>
      </form>
    </div>
  </div>
</div>


