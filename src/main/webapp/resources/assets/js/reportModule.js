var reportApp = angular.module('reportApp', ['ui.bootstrap','ngReallyClickModule']);
// Your function

reportApp.controller('ModalInstanceCtrl', ['$scope', '$modalInstance', 'doc', 'users' , 'ReportService', function ($scope, $modalInstance, doc,users, ReportService) {
    $scope.doc = doc;
    $scope.usersDo = users;

    $scope.save = function () {
        $modalInstance.close($scope.doc);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
}]);

