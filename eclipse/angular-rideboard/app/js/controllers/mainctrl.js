angular.module("rideBoard").controller('MainController', ['$scope',  function($scope) {
  
  $scope.rides = [
	  {
		  destination: "Hadera",
		  driver: "Ofra",
		  departureHour: "12:00"
	  },
	  {
		  destination: "Tel Aviv",
		  driver: "Boris",
		  departureHour: "13:00"
	  },
	  {
		  destination: "Herzliya",
		  driver: "Yossi",
		  departureHour: "14:00"
	  },
	  {
		  destination: "Haifa",
		  driver: "Gil",
		  departureHour: "15:00"
	  }
  ];
}]);