﻿var reportApp = angular.module('reportApp', ['ui.bootstrap','ngReallyClickModule','angular-loading-bar']);
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


reportApp.controller('ResetPasswordCtrl', ['$scope', '$modalInstance', 'user' , 'ReportService', function ($scope, $modalInstance,user, ReportService) {

    $scope.user = user;

    $scope.save = function () {
        $modalInstance.close($scope.user);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
}]);
