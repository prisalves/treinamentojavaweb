angular.module("emiolo").controller("nasaController", function ($scope, emioloAPI, $location, $window) {
	
	$scope.classNasa = "active";
	
	$scope.nasa = {"dia":1,"mes":7,"ano":2016,"camera":"Todas as Câmeras"};
	
	$scope.buscarNasa = function (nasa) {
		
		$scope.loading = true;
		
		emioloAPI.getResultadoNasa(nasa).success(function (data) {
			
			$scope.loading = false;
			$scope.fotos = data.photos;	
			$scope.msg = '';
			
		}).error(function(err){
			
			$scope.loading = false;
			$scope.msg = '<div class="alert alert-danger alerta"><strong>Erro!</strong> Imagens não encontrada.</div> ';
			
        });
		
	};
	
	$scope.loading = false;
		
		
});

