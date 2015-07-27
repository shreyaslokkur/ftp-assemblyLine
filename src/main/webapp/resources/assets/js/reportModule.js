var reportApp = angular.module('reportApp', ['ui.bootstrap']);

reportApp.controller('ModalInstanceCtrl', ['$scope', '$modalInstance', 'doc', 'ReportService', function ($scope, $modalInstance, doc, ReportService) {
    /* copy rest of example code here */
    $scope.doc = doc;

    $scope.HoldRecord = function (doc) {
        var promise = ReportService.HoldRecord(doc);
        promise.then(
           function (payload) {
               angular.extend(doc, payload);
           },
           function (errorPayload) {
               $log.error('failure: Error While  Hold document', errorPayload);
           });
    }

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
}]);