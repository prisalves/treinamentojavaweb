angular.module("emiolo").filter('unsafe', function($sce) {
    return function(val) {
        return $sce.trustAsHtml(val);
    };
});