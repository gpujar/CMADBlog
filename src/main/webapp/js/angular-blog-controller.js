(function(){
	var reqUrl = "http://173.36.55.178:8889/CMADBlog/rest/"; //"http://localhost:8080/CMADBlog/rest/";  
	var module = angular.module('BlogApp',[ 'ngRoute' ]);
	module.config(['$routeProvider',function($routeProvider){
		$routeProvider.when('/login', {
			templateUrl : 'login.html',
			controller : 'loginController'
		}).when('/register',{
			templateUrl : 'register.html',
			controller : 'registerController'
		}).when('/postBlog', {
			templateUrl :'create_blog.html',
			controller : 'blogPostController'
		}).when('/blogList', {
			templateUrl : 'blog_list.html',
			controller : 'blogListController'
		}).when('/contact', {
			templateUrl : 'contact.html',
			controller : 'contactController'
		}).when('/about', {
			templateUrl : 'about.html',
			controller : 'aboutUsController'
		}).when('/blogSyn', {
			templateUrl : 'blog.html',
			controller : 'blogSynopsisController'
		}).otherwise({
			redirectTo : '/login'
		});
	}]);
	
	
	module.controller('loginController', function($http, $log, $scope, $location){
		$scope.login =  function(user) {
			$scope.logging = true;
			$scope.error = false;
			$scope.success = false;
			 var postData = $.param({
		            	"email": $scope.user.username,
		                "password": $scope.user.password
		        });
				$http({
					"url" : reqUrl+'1/login',
					"headers": {'Content-Type': 'application/x-www-form-urlencoded'},
					"method" : "POST",
					"data" : postData			
				}).success( function(data, textStatus, jqXHR) {
					$scope.logging = false;
					$scope.success = true;
					$scope.error = false;
					if(typeof(Storage) !== "undefined") {
						sessionStorage.setItem('token', data.token);
						}
					$location.path('/blogList');
					$scope.$apply();
				}).error(function(data, textStatus, errorThrown) {
					$scope.logging = false;
					$scope.error = true;
				});
		};
	});
	
	
	module.controller('registerController', function($http, $scope, $log, $location) {
		$scope.register = function(newuser){
			$scope.error = false;
			$scope.singning = true;
		var postData = $.param({
         	'firstName':  $scope.newuser.firstName,
            'lastName': $scope.newuser.lastName,
            'email': $scope.newuser.email,
            'password': $scope.newuser.password
		});
		$http({
			"url" : reqUrl+'1/user',
			"headers": {'Content-Type': 'application/x-www-form-urlencoded'},
			"method" : 'POST',
			"data" : postData,
		}).success(function(data, textStatus, jqXHR) {
			var json = data; //JSON.parse(data);
			if (typeof (Storage) !== "undefined") {
				sessionStorage.token = json.token;
			}
			$scope.singning = false;
			$location.path('/blogList');
			$scope.$apply();
		}).error(function(jqXHR, textStatus, errorThrown) {
			$scope.singning = false;
			$scope.error = true;
		});
		$scope.newuser ='';
		};
	});
		
	module.service('messages', function() {
		var cacheMessage="";
		this.setText = function(inputText) {
			cacheMessage=inputText
		        }
		    this.getText = function() {
		            return cacheMessage;
		        }
		});
	
	module.controller('blogPostController', function($scope, $http, $log, $location, messages){
		$scope.blogPost = function(){	
			var dataToSend = $.param({
				"title": $scope.blog.title,
				"content": $scope.blog.content,
				"synopsis": $scope.blog.shortSyn
				});
			var token;
			if(sessionStorage.getItem('token') != null){
				token = "Basic " + sessionStorage.getItem('token');
			}
			$http({
		        "url" : reqUrl+"1/blog",
		        "method" : "POST",
		        "headers" : {"Content-Type" : "application/x-www-form-urlencoded",
		        	"Authorization" : token },
		        "data" : dataToSend	        
		    }).success(function(data, textStatus, jqXHR) {
            	$location.path('/blogList');
				$scope.$apply();
            }).error(function (data, textStatus, jqXHR){
	             console.log('Error');
	         });
		};				
		
		$scope.logout =  function(){
			var token;
			if(sessionStorage.getItem('token') != null){
				token = "Basic " + sessionStorage.getItem('token');
			}
			$http({
				"url" : reqUrl+'1/logout',
				"method" : 'POST',
				"headers" : {"Content-Type" : "application/x-www-form-urlencoded",
		        "Authorization" : token }}).success(function(data, textStatus, jqXHR) {
	            	$location.path('/login');
					$scope.$apply();
				}).success(function(data, textStatus, jqXHR){
					sessionStorage.removeItem('token');
					$location.path('/login');
					$scope.$apply();
				}).error(function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR.responseText);
				});
		};
	});
	
	
	module.controller('blogListController', function($scope, $http, $log, $location, messages){
		
		$http({
			"url" : reqUrl+'1/blog',		
			"headers" :{'Authorization' : "Basic " + sessionStorage.getItem('token')},
			"method" : 'GET'}).success(function(data, textStatus, jqXHR) {
				$scope.blogs =  data; //JSON.stringify(data); 
			}).error(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
				$location.path('/login');
				$scope.$apply();
			});
		
		$scope.search = function(){	
			var text = $scope.searchText;
			var url ;
			if(text == null || text == ""){
				url = reqUrl+'1/blog';
			}else{
				url = reqUrl+'1/blog/title/'+text;
			}
			var token;
			if(sessionStorage.getItem('token') != null){
				token = "Basic " + sessionStorage.getItem('token');
			}
			$http({
				"url" : url,
				"method": "GET",
				"headers":{"Content-Type" : "application/x-www-form-urlencoded",
						"Authorization" : token
						}		
			}).success(function(data, textStatus, jqXHR){
					$scope.blogs =  data; //JSON.stringify(data); 
				}).error(function(jqXHR, textStatus, errorThrown){
					console.log(jqXHR.responseText);
				});
		};
		
		$scope.logout = function(){
			var token;
			if(sessionStorage.getItem('token') != null){
				token = "Basic " + sessionStorage.getItem('token');
			}
			$http({
				"url" : reqUrl+'1/logout',
				"method" : 'POST',
				"headers" : {"Content-Type" : "application/x-www-form-urlencoded",
		        "Authorization" : token }
			}).success(function(data, textStatus, jqXHR) {
				$location.path('/login');
				$scope.$apply();
				sessionStorage.removeItem('token');
			}).error(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			});
		};
		
		$scope.cacheData = function(text){
			messages.setText(text);
		};
	});
	
	
	module.controller('blogSynopsisController',function($scope, $log, $http, $location, messages){
		var searchString = messages.getText();
		var url = reqUrl+'1/blog/synopsis/'+searchString;
		var token;
		if(sessionStorage.getItem('token') != null){
			token = "Basic " + sessionStorage.getItem('token');
		}
	$http({
		"url" : url,
		"method": "GET",
		"headers":{"Content-Type" : "application/x-www-form-urlencoded",
				"Authorization" : token
				}		
		}).success(function(data, textStatus, jqXHR){
			//data = JSON.stringify(data).replace(/\n/g,'<br />').replace(/\t/g,'&nbsp;&nbsp;&nbsp;');
			$scope.blog = data;
		}).error(function(jqXHR, textStatus, errorThrown){
			console.log(jqXHR.responseText);
			$location.path('/login');
			$scope.$apply();
		});
	
	$scope.logout =  function(){
		var token;
		if(sessionStorage.getItem('token') != null){
			token = "Basic " + sessionStorage.getItem('token');
		}
		$http({
			"url" : reqUrl+'1/logout',
			"method" : 'POST',
			"headers" : {"Content-Type" : "application/x-www-form-urlencoded",
	        "Authorization" : token }}).success(function(data, textStatus, jqXHR) {
            	$location.path('/login');
				$scope.$apply();
			}).success(function(data, textStatus, jqXHR){
				$location.path('/login');
				$scope.$apply();
				sessionStorage.removeItem('token');
			}).error(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			});
	};
	});
	
	module.controller('contactController', function($http, $scope, $location){
		$scope.logout =  function(){			
			var token;
			if(sessionStorage.getItem('token') != null){
				token = "Basic " + sessionStorage.getItem('token');
			}
			$http({
				"url" : reqUrl+'1/logout',
				"method" : 'POST',
				"headers" : {"Content-Type" : "application/x-www-form-urlencoded",
		        "Authorization" : token }}).success(function(data, textStatus, jqXHR) {
	            	$location.path('/login');
					$scope.$apply();
				}).success(function(data, textStatus, jqXHR){
					sessionStorage.removeItem('token');
					$location.path('/login');
					$scope.$apply();
				}).error(function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR.responseText);
				});
		};
	});
	
	module.controller("aboutUsController", function($http, $scope, $location){
		$scope.logout =  function(){
			var token;
			if(sessionStorage.getItem('token') != null){
				token = "Basic " + sessionStorage.getItem('token');
			}
			$http({
				"url" : reqUrl+'1/logout',
				"method" : 'POST',
				"headers" : {"Content-Type" : "application/x-www-form-urlencoded",
		        "Authorization" : token }}).success(function(data, textStatus, jqXHR) {
	            	$location.path('/login');
					$scope.$apply();
				}).success(function(data, textStatus, jqXHR){
					$location.path('/login');
					$scope.$apply();
					sessionStorage.removeItem('token');
				}).error(function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR.responseText);
				});
		};
	});
	
})();