var reportApp = angular.module('reportApp', ['ui.bootstrap','ngReallyClickModule']);
// Your function

reportApp.controller('ModalInstanceCtrl', ['$scope', '$modalInstance', 'doc', 'ReportService', function ($scope, $modalInstance, doc, ReportService) {
    $scope.doc = doc;


    $scope.save = function () {
        $modalInstance.close($scope.doc);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
}]);

