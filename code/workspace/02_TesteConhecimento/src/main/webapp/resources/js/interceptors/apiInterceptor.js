angular.module("emiolo").factory("apiInterceptor", function ($q) {
	return {
		request: function (config) {
			return config;
		},
		responseError: function (rejection) {
			console.log(rejection.config.url);
			return $q.reject(rejection);
		}
	};
});
angular.module("emiolo").config(function ($httpProvider) {
	$httpProvider.interceptors.push("apiInterceptor");
});

/*angular.module("emiolo").run(["$rootScope", "$location", function($rootScope, $location) {
	$rootScope.$on("$routeChangeSuccess", function(userInfo) {
		console.log(userInfo);
	});
	$rootScope.$on("$routeChangeError", function(event, current, previous, eventObj) {
		if (eventObj.authenticated === false) {
			$location.path("/login");
		}
	});
}]);*/