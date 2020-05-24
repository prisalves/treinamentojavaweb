
angular.module("emiolo").config(function ($routeProvider) {
	
	$routeProvider.when("/usuarios", {
		//cache: false,
		templateUrl: "usuarios.jsp",
		controller: "usuariosController",
		resolve: {
			autenticado: function ($window, $q, $location) {
				  if (!$window.sessionStorage["userInfo"]) {
					  $location.path("/login");
			      } 
			}
		}
	});
	
	$routeProvider.when("/nasa", {
		//cache: false,
		templateUrl: "nasa.jsp",
		controller: "nasaController",
		resolve: {
			autenticado: function ($window, $q, $location) {
				if (!$window.sessionStorage["userInfo"]) {
					  $location.path("/login");
			      } 
			}
		}
	});
	
	$routeProvider.when("/login", {
		//cache: false,
		templateUrl: "login.jsp",
		controller: "loginController",
		resolve: {
			autenticado: function ($window, $q, $location) {
				if ($window.sessionStorage["userInfo"]) {
					  $location.path("/usuarios");
			      } 
			}
		}
	});
	
	$routeProvider.otherwise({redirectTo: "/login"});
	
});


