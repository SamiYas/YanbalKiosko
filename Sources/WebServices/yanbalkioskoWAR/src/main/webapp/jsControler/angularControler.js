 angular.module("myapp", [])
.controller("HelloController", function($scope,$http) {
	 	$http.get('http://localhost:8081/yanbalkioskov2/paises').
	 		success(function(data) {            
	 			$scope.algo = data;
	 		});
 		});
         
       