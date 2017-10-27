angular.module('myApp.controller', ['myApp.services'])
.controller('storeCtrl', function($scope, $locale, EnviarMensajeServices,ActualizarUltimoMensajeServices,BuscarMensajesServices) {
    
    $locale.NUMBER_FORMATS.GROUP_SEP = '.';

    
    $scope.usuario = {};
    $scope.mensaje = "";
    $scope.mensajes = [];
    $scope.mensajesNuevos = 0;

    

  



    $scope.asignarUsuario = function(){
        $("#modal").fadeOut();
        $scope.connection = new WebSocket('ws://localhost:8080/socket');

        // When the connection is open, send some data to the server
        $scope.connection.onopen = function () {
        };

        // Log errors
        $scope.connection.onerror = function (error) {
          console.log('WebSocket Error ' + error);
        };

        // Log messages from the server
        $scope.connection.onmessage = function (e) {
          $scope.$apply(function(){$scope.mensajes.push(JSON.parse(e.data));});     
          console.log('Server: ' + e.data);
         };

 
    };

    $scope.enviarMensaje = function(){
        //$scope.mensajes.push({origen:$scope.usuario.nombre,fecha:'hoy',texto:$scope.mensaje});
        var json = JSON.stringify({
            "content":$scope.mensaje
        });

        $scope.connection.send(json);
        $scope.mensaje = "";

    };


    
    });



    
        
    

    
    