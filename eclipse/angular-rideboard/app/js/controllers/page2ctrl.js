angular.module("rideBoard").controller("Page2Ctrl", ['$scope', function($scope){
	$scope.text2 = "You've reached page 2. Powered by gulp watch!";
        $scope.ride = {
            freeSpace: 0
        };
        
        $scope.sendForm = function sendForm(){
            
            console.log($scope.ride);
            
        };
}]);