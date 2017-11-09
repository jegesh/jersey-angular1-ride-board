(function () {

  'use strict';

  require('angular');
  require('angular-route');
  require('angular-animate');

  angular.module('rideBoard', ['ngRoute', 'ngAnimate'])

  .config([
    '$locationProvider',
    '$routeProvider',
    function($locationProvider, $routeProvider) {
      $locationProvider.hashPrefix('!');
      // routes
      $routeProvider
        .when("/", {
          templateUrl: "./partials/partial1.html",
          controller: "MainController"
        })
        .when("/page2", {
          templateUrl: "./partials/partial2.html",
          controller: "Page2Ctrl"
        })
        .otherwise({
           redirectTo: '/'
        });
    }
  ]);
  require('./controllers/mainctrl');
  require('./controllers/page2ctrl');  


}());



