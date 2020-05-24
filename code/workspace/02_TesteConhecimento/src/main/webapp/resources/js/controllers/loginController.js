angular.module("emiolo").controller("loginController", function ($scope, emioloAPI, $location, $window ) {
	
	$scope.autenticar = function (usuario) {
		
		$location.hash('topo');

		$scope.loading = true;
		
		emioloAPI.autenticar(usuario).success(function (data) {
			
			$scope.loading = false;
			if(data.msg=="SUCESSO"){
				$window.sessionStorage["userInfo"] = data.nome;
				delete $scope.usuario;
				$location.path("/usuarios");
			}else{
				$scope.msg = '<center><b>Usuário:</b> '+usuario.login+',<b>senha:</b> '+usuario.senha+'<center><br/><div class="alert alert-danger alerta"><strong>Erro!</strong> '+data.msg+'.</div> ';
			}				
			
		}).error(function(err){
			
			$scope.loading = false;
			$scope.msg = '<div class="alert alert-danger alerta"><strong>Erro!</strong>Servidor de autenticação.</div> ';
			
        });
	};
	
	$scope.logout = function () {
		$window.sessionStorage.removeItem("userInfo");
		//console.log("LOGOUT");
		$location.path("/login");
	}
	
});

