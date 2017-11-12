(function () {

  'use strict';

  require('angular');
  require('angular-route');
  require('angular-animate');
  require('angular-touch');
  require('angular-ui-bootstrap');

  angular.module('rideBoard', ['ngRoute', 'ngAnimate', 'ngTouch', 'ui.bootstrap'])

  .config([
    '$routeProvider',
    function($routeProvider) {
      // routes
      $routeProvider
        .when("/home", {
          templateUrl: "./partials/partial1.html",
          controller: "MainController"
        })
        .when("/page2", {
          templateUrl: "./partials/partial2.html",
          controller: "Page2Ctrl"
        })
        .otherwise({
           redirectTo: '/home'
        });
    }
  ]);
  require('./controllers/mainctrl');
  require('./controllers/page2ctrl');  


}());



