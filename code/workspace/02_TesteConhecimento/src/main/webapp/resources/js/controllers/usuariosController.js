angular.module("emiolo").controller("usuariosController", function ($scope, $http, $location, $compile, $window, emioloAPI, DTOptionsBuilder, DTColumnBuilder, DTColumnDefBuilder, config) {
	
	$scope.loading = true;
	$scope.classUsuario = "active";
	
	$scope.msg_home = config.msg_home+$window.sessionStorage["userInfo"];
	
	var vm = this;
	vm.dtColumns = [
    	        DTColumnBuilder.newColumn('idUsuario').withTitle('ID'),
    	        DTColumnBuilder.newColumn('nome').withTitle('Nome'),
    	        DTColumnBuilder.newColumn('login').withTitle('Login'),
    	        DTColumnBuilder.newColumn('senha').withTitle('Senha'),
    	        DTColumnBuilder.newColumn(null).withTitle('Ações').notSortable().renderWith(actionsHtml)
    	    ];
	
	vm.dtOptions = DTOptionsBuilder.newOptions().withOption('ajax',{
		url:"usuarios",
		type:"get"
	})
	.withOption('fnDrawCallback', function (nRow, aData, iDataIndex , iDisplayIndex, iDisplayIndexFull) { 
		$compile(nRow)($scope);
	})
	.withOption('fnCreatedRow', function (nRow, aData, iDataIndex, iDisplayIndex, iDisplayIndexFull) { 
		$compile(nRow)($scope);
	})
	//.withOption('stateSave', true)
	.withOption('aaSorting', [0, 'desc'])
	.withPaginationType('full_numbers')
	.withDisplayLength(10)
	.withLanguage({
		"sEmptyTable": "Nenhum registro encontrado",
		"sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
		"sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
		"sInfoFiltered": "(Filtrados de _MAX_ registros)",
		"sInfoPostFix": "",
		"sInfoThousands": ".",
		"sLengthMenu": "_MENU_ resultados por página",
		"sLoadingRecords": "Carregando...",
		"sProcessing": "Processando...",
		"sZeroRecords": "Nenhum registro encontrado",
		"sSearch": "Pesquisar",
		"oPaginate": {
			"sNext": "Próximo",
			"sPrevious": "Anterior",
			"sFirst": "Primeiro",
			"sLast": "Último"
		},
		"oAria": {
			"sSortAscending": ": Ordenar colunas de forma ascendente",
			"sSortDescending": ": Ordenar colunas de forma descendente"
		}
    });
	vm.delete = deleteRow;
	vm.dtInstance = {};
	vm.usuarios = {};
	
	function actionsHtml(data, type, full, meta) {
        vm.usuarios[data.id] = data;
        return '<button class="btn btn-danger" ng-click="lista.delete(' + data.idUsuario + ')" >' +
            '   <i class="fa fa-trash-o"></i>' +
            '</button>';
    }
	
	function deleteRow(usuario) {

		$scope.loading = true;
		
		emioloAPI.delete(usuario).success(function (data) {
			
			$scope.loading = false;
			
			if(data.msg=="SUCESSO"){
				$('#tableUsuarios').DataTable().ajax.reload();
				$('#result-modal').html('<div class="alert alert-info alerta" ><strong>Sucesso!</strong> Usuário deletado com sucesso!</div>');
			}else{
				$('#result-modal').html('<div class="alert alert-danger alerta" ><strong>Erro!</strong> Usuário não foi deletado.</div> ');
			}				
			
		}).error(function(err){
			$scope.loading = false;
			$scope.msg = '<div class="alert alert-danger" class="alerta"><strong>Erro!</strong>Problemas no servidor.</div> ';
        });
	};	
	

	$scope.adicionar = function (usuario) {
		
		$location.hash('topo');

		$scope.loading = true;
		
		emioloAPI.adicionar(usuario).success(function (data) {
			
			$scope.loading = false;
			
			if(data.msg=="SUCESSO"){
				delete $scope.usuario;
				$('#tableUsuarios').DataTable().ajax.reload();
				$('#addUsuario').modal('hide');
				$('#result-modal').html('<div class="alert alert-success alerta" ><strong>Sucesso!</strong> Usuário cadastrado com sucesso!</div>');
			}else{
				$('#result-modal').html('<div class="alert alert-danger alerta" ><strong>Erro!</strong> Usuário não foi cadastrado.</div> ');
			}				

		}).error(function(err){
			$scope.loading = false;
			$scope.msg = '<div class="alert alert-danger" class="alerta"><strong>Erro!</strong>Servidor de autenticação.</div> ';
        });
	};
	
	
	
	$scope.loading = false;
		
		
});

