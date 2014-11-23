var todosApp = angular.module('todosApp', ['ngRoute']);  

// Routing 

	todosApp.config(function($routeProvider) {
		$routeProvider 
			.when('/',{
				controller: 'RootController', 
				templateUrl: 'views/RootController.html' 
			}) 
			.when ('/todos', {
				controller:'TodosController',
				templaerUrl: 'views/TodosController.html'
			}) 
			.otherwise({redirectTo:'/'});
	}); 
	
// Controllers 
	
	todosApp.controller('RootController', function($scope, $http){
		// ... 
		$scope.startPlaying = function(item, event) { 
            var responsePromise = $http.get("http://192.168.1.103:8080/todo-rest/rest/play/flaixfm");
            responsePromise.success(function(data, status, headers, config) { 
                $scope.fromServer = data.title; 
            });
            responsePromise.error(function(data, status, headers, config) {
                alert("AJAX failed!");
            });
        } 
		$scope.stopPlaying = function(item, event) { 
            var responsePromise = $http.get("http://192.168.1.103:8080/todo-rest/rest/stop/flaixfm");
            responsePromise.success(function(data, status, headers, config) { 
                $scope.fromServer = data.title; 
            });
            responsePromise.error(function(data, status, headers, config) {
                alert("AJAX failed!");
            });
        } 
		$scope.addVolume = function(item, event) { 
            var responsePromise = $http.get("http://192.168.1.103:8080/todo-rest/rest/volume/add");
            responsePromise.success(function(data, status, headers, config) { 
                $scope.fromServer = data.title; 
            });
            responsePromise.error(function(data, status, headers, config) {
                alert("AJAX failed!");
            });
        }
		$scope.delVolume = function(item, event) { 
            var responsePromise = $http.get("http://192.168.1.103:8080/todo-rest/rest/volume/del");
            responsePromise.success(function(data, status, headers, config) { 
                $scope.fromServer = data.title; 
            });
            responsePromise.error(function(data, status, headers, config) {
                alert("AJAX failed!");
            });
        }
	}); 
	todosApp.controller('TodosController', function($scope){
		// ... 
	}); 