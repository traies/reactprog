angular.module('myApp.controller', ['myApp.services'])
.controller('storeCtrl', function($scope, $locale,$http, EnviarMensajeServices,ActualizarUltimoMensajeServices,BuscarMensajesServices) {
    
    $locale.NUMBER_FORMATS.GROUP_SEP = '.';

    
    $scope.usuario = {};
    $scope.mensaje = "";
    $scope.mensajes = [];
    $scope.mensajesNuevos = 0;
    $scope.canal = {};
    $scope.canal.id = "1";
    $scope.canal.nombre = "Canal 1";

    


    $scope.openConnection = function(){
        $scope.connection = new WebSocket('ws://localhost:8080/socket?chatId='+$scope.canal.id+'&user='+$scope.usuario.nombre);

        // When the connection is open, send some data to the server
        $scope.connection.onopen = function () {
        };

        // Log errors
        $scope.connection.onerror = function (error) {
          console.log('WebSocket Error ' + error);
        };

        // Log messages from the server
        $scope.connection.onmessage = function (e) {
           mensajeNuevo = JSON.parse(e.data);
           console.log(e.data);
          $scope.$apply(function(){$scope.mensajes.push(mensajeNuevo);});

         };
    };

    $scope.cambiarCanal = function(id,nombre){
        $scope.canal.id = id;
        $scope.canal.nombre = nombre;
        $scope.mensajes = [];
        $scope.connection.close();
        $scope.openConnection();
        $("li.selected").removeClass("selected");
        $("div#linksChannels ul li").eq(id-1).addClass("selected");

    };




    $scope.asignarUsuario = function(){
        $("#modal").fadeOut();
        $scope.openConnection();

    };

    $scope.enviarMensaje = function(){

        $http({
        			url:"http://localhost:8080/rest/sendmessage?chatid="+$scope.canal.id+"&user="+$scope.usuario.nombre+"&text="+$scope.mensaje ,
        			method: 'POST'
        		});


        $scope.mensaje = "";

    };


    
    });



    
        
    

    
    