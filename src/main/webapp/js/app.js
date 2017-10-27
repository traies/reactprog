var app = angular.module('myApp', ['myApp.services', 'myApp.controller']);


base = 'http://localhost:8082';

app.run(function($http, $rootScope, $log) {
	
	$rootScope.httpGet = function(url, callback) {
		$http({
			url: base + url , 
			method: 'GET',
			 headers: {  'Access-Control-Allow-Origin': ''}
		}).then(function(data) {
			if (data && data.exception && data.exception.message && data.exception.message.__cdata) {
				alert(data.exception.message.__cdata);
			}
			alert(data);
			callback(data); 
	   	});
	
	}

	$rootScope.httpPost = function(url, json, callback) {
		$http({
			url: base + url ,
			data: json, method: 'POST'
		}).then(function(data) {
			if (data && data.exception && data.exception.message && data.exception.message.__cdata) {
				alert(data.exception.message.__cdata);
			}
			callback(data); 
		})/*.error(function (data, status) {
	//   		$log.error('$rootScope.httpPost | ' + navigator.userAgent + ' | ' + url + ' | ' + angular.fromJson(json) + ' | ' + status + ' | ' + data);
	   		alert('No se puede contactar al servidor. Si el problema persiste, por favor comuniquese con soporte.');
	   	})*/;
	}
	
});

