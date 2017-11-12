angular.module("rideBoard").controller("Page2Ctrl", ['$scope', function($scope){
	$scope.text2 = "You've reached page 2. Powered by gulp watch!";
        $scope.destination = null;
        
        $scope.alertForm = function alertForm(){
            debugger;
            alert($scope.date);  
        };
}]);