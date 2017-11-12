angular.module("rideBoard").controller("Page2Ctrl", ['$scope', function($scope){
	$scope.text2 = "You've reached page 2. Powered by gulp watch!";
        $scope.destination = null;
        $scope.departureDate = null;
        $scope.freeSpace = 0;
        
        $scope.sendForm = function sendForm(){
            
            var form = {};
            form.destination = $scope.destination;
            form.date = $scope.departureDate;
            form.space = $scope.freeSpace;
            console.log(form);
        };
}]);