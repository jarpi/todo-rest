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
	
	todosApp.directive('whenScrolled', function() { 
	    return function(scope, elm, attr) {
	        var raw = elm[0]; 
	        elm.bind('scroll', function() { 
	            if ((raw.scrollTop + raw.offsetHeight)+1 >= raw.scrollHeight) {
	                scope.$apply(attr.whenScrolled); 
	            } 
	        }); 
	    }; 
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
		$scope.addTodo = function(item, event) { 
			var title = $("#todoTitle").val(); 
			var desc = $("#todoDesc").val(); 
			var id = $("#todoId").val(); 
			console.log("ID: " + id); 
			var responsePromise = (id && id!=""?$http.post("http://192.168.1.103:8080/todo-rest/rest/addNote/" + title + "/" + desc):$http.post("http://192.168.1.103:8080/todo-rest/rest/updateNote/"+id+"/" + title + "/" + desc)); 
            responsePromise.success(function(data, status, headers, config) { 
                // After succes inserted into db, add to scope 
            	if (data.result != 0) {
            		var obj = {"id":data.result,"title":title,"desc":desc};
            		TodosService.todos.push(obj); 
            		$scope.resetForm(); 
            	} 
            	console.log(TodosService.todos);    
            }); 
            responsePromise.error(function(data, status, headers, config) {
                alert("AJAX failed!");
            }); 
		}; 
		$scope.loadTodo = function(item, event, index) {
			var todo = TodosService.todos[index];  
			$("#todoTitle").val(todo.title);
			$("#todoDesc").val(todo.desc); 
			$("#todoId").text=todo.id; 
			$("#todoSubmit").text("Update"); 
		}; 
		$scope.resetForm = function() { 
			$("#todoTitle").val("");
			$("#todoDesc").val("");
			$("#todoId").text = ""; 
			$("#todoSubmit").text("Add"); 
		}
		$scope.resetInput = function(item, event) { 
			inputVal =$("#searchBox").val();  
			$("#searchBox").val((inputVal=="Search..."?"":(inputVal.length==0?"Search...":inputVal)));    
		}; 
		if ($scope.todos.length<1) $scope.getTodos(); 
	}); 
	 
	
	