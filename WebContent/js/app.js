var todosApp = angular.module('todosApp', ['ngRoute']);  

// Routing 

	todosApp.config(function($routeProvider) { 
		$routeProvider 
			.when('/player',{
				controller: 'RootController', 
				templateUrl: 'views/RootController.html' 
			}) 
			.when ('/todos', {
				controller:'TodosController',
				templateUrl: 'views/TodosController.html'
			}) 
			.otherwise({redirectTo:'/player'}); 
	}); 
	
// Controllers 
	
	todosApp.controller('RootController', function($scope, $http){ 
		$scope.startPlaying = function(item, event) { 
            var responsePromise = $http.get("http://192.168.1.103:8080/todo-rest/rest/play/flaixfm");
            responsePromise.success(function(data, status, headers, config) { 
                $scope.fromServer = data.title; 
            });
            responsePromise.error(function(data, status, headers, config) {
                alert("AJAX failed!");
            });
        };  
		$scope.stopPlaying = function(item, event) { 
            var responsePromise = $http.get("http://192.168.1.103:8080/todo-rest/rest/stop/flaixfm");
            responsePromise.success(function(data, status, headers, config) { 
                $scope.fromServer = data.title; 
            });
            responsePromise.error(function(data, status, headers, config) {
                alert("AJAX failed!");
            });
        }; 
		$scope.addVolume = function(item, event) { 
            var responsePromise = $http.get("http://192.168.1.103:8080/todo-rest/rest/volume/add");
            responsePromise.success(function(data, status, headers, config) { 
                $scope.fromServer = data.title; 
            });
            responsePromise.error(function(data, status, headers, config) {
                alert("AJAX failed!");
            });
        }; 
		$scope.delVolume = function(item, event) { 
            var responsePromise = $http.get("http://192.168.1.103:8080/todo-rest/rest/volume/del");
            responsePromise.success(function(data, status, headers, config) { 
                $scope.fromServer = data.title; 
            });
            responsePromise.error(function(data, status, headers, config) {
                alert("AJAX failed!");
            });
        }; 
	});  
	todosApp.controller('headerController', function($scope){ 
		// Defined in header.html {html level} 
		$scope.toggleMenu = function(item, event) { 
			if (window.innerWidth<="480") {
				$("#left-menu").toggle(); 
				$("#content").css('width',($("#left-menu").css('display')=="none"?'100%':'70%'));    	
			} 
		};  
	}); 
	todosApp.controller('menuController', function($scope){}); 
	todosApp.controller('TodosController', function($scope){
		$scope.resetInput = function(item, event) { 
			inputVal =$("#searchBox").val();  
			$("#searchBox").val((inputVal=="Search..."?"":(inputVal.length==0?"Search...":inputVal)));    
		}; 
	});  
	
	