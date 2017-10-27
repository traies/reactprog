angular.module('myApp.services', [])
.factory('EnviarMensajeServices', function($http, $rootScope) {
    var self = this;
    self.store = function(json, callback) {
    	$rootScope.httpPost('../xml/EnviarMensaje/0', json, callback);
    };
    return self;
}).factory('BuscarMensajesServices', function($http, $rootScope) {
    var self = this;
    self.list = function(callback) {
    	$rootScope.httpGet('/chat', callback);
    };
    return self;
}).factory('ActualizarUltimoMensajeServices', function($http, $rootScope) {
	 var self = this;
	    self.store = function(json, callback) {
	    	$rootScope.httpPost('../xml/ActualizarUltimoMensaje/0', json, callback);
	    };
	    return self;
});