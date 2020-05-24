angular.module("emiolo").factory("emioloAPI", function ($http, config) {
	
	var _getUsuarios = function () {
		return $http.get("usuarios");
	};
	
	var _adicionar = function (usuario) {
		return $http.post("usuarios", usuario);
	};
	
	var _delete = function (usuario) {
		return $http.post("delete_usuario", {idUsuario: usuario});
	};
	
	var _autenticar = function (usuario) {
		return $http.post("login", usuario);
	};
	
	var _getLogin = function () {
		return $http.get("login");
	};
	
	var Feeds = {};

    Feeds = {
        categories:"",
        posts:"",
        finishedloading:false
    };
    
    var _getResultadoNasa = function (nasa) {
    	var key = config.nasaAPI;
    	var url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";
    	var data = nasa.ano+"-"+nasa.mes+"-"+nasa.dia;
    	var camera = '';
		switch(nasa.camera) {
		  case"FHAZ - Front Hazard Avoidance Camera": camera = "&camera=FHAZ"; break;
		  case"RHAZ - Rear Hazard Avoidance Camera": camera = "&camera=RHAZ"; break;
		  case"NAVCAM - Navigation Camera": camera = "&camera=NAVCAM"; break;
		}
		return $http.post("nasa", {key:key,url:url,data:data,camera:camera});
	};

	return {
		getLogin: _getLogin,
		getUsuarios: _getUsuarios,
		autenticar: _autenticar,
		adicionar: _adicionar,
		delete: _delete,
		getResultadoNasa: _getResultadoNasa
	};
	
});