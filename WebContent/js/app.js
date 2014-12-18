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
	
// Factories (data model for todo datasource)  
todosApp.factory('TodosService', function () { 
		var todosService = { 
				todos:[],  
				index:0, 
				offset:5, 
		}; 
		return todosService;  
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
	todosApp.controller('TodosController', function($scope, $http, $rootScope,TodosService) {     
		$scope.todos = TodosService.todos;    
		// $rootScope.index = 0; 
		// $rootScope.offset = 5; 
		$scope.getTodos = function(item, event) {    
			var responsePromise = $http.get("http://192.168.1.103:8080/todo-rest/rest/getFilteredNotes/" + TodosService.index + "/" + TodosService.offset); 
            responsePromise.success(function(data, status, headers, config) { 
                for (var i=0; i<data.length;i++) { 
                	var todo = data[i];  
                	var found = false; 
                	for (var j=0;j<TodosService.todos.length;j++) { 
                		var currentTodo = TodosService.todos[j]; 
                		if (currentTodo.id === todo.id) found = true; 
                	} 
                	if (!found) {
                		TodosService.todos.push(data[i]); 
                	} 
                }  
            	console.log(TodosService.todos);
            	TodosService.index = TodosService.todos.length+1; 
            }); 
            responsePromise.error(function(data, status, headers, config) { 
                alert("AJAX failed!"); 
            }); 
		}; 
		$scope.addTodo = function(item, event, title, desc) {
			var responsePromise = $http.post("http://192.168.1.103:8080/todo-rest/rest/addNote/" + title + "/" + desc); 
            responsePromise.success(function(data, status, headers, config) { 
                // After succes inserted into db, add to scope 
            	console.log(data); 
            	if (data.result != 0) {
            		var obj = {"id":data.result,"title":title,"desc":desc};
            		TodosService.todos.push(obj); 
            	} 
            	console.log(TodosService.todos);    
            }); 
            responsePromise.error(function(data, status, headers, config) {
                alert("AJAX failed!");
            }); 
		};   
		$scope.resetInput = function(item, event) { 
			inputVal =$("#searchBox").val();  
			$("#searchBox").val((inputVal=="Search..."?"":(inputVal.length==0?"Search...":inputVal)));    
		}; 
		if ($scope.todos.length<1) $scope.getTodos(); 
	});  
	
	