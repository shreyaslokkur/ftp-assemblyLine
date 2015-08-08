var reportApp = angular.module('reportApp', ['ui.bootstrap','ngReallyClickModule']);

reportApp.controller('ModalInstanceCtrl', ['$scope', '$modalInstance', 'doc', 'ReportService', function ($scope, $modalInstance, doc, ReportService) {
    /* copy rest of example code here */
    $scope.doc = doc;

    $scope.HoldRecord = function (doc) {
        var promise = ReportService.HoldRecord(doc);
        promise.then(
            function (payload) {
                angular.extend(doc, payload);
                $scope.cancel();
            },
            function (errorPayload) {
                $log.error('failure: Error While  Hold document', errorPayload);
            });
    }


    $scope.rejectRecord = function (doc) {
        var promise = ReportService.rejectRecord(doc);
        promise.then(
            function (payload) {
                angular.extend(doc, payload);

                $scope.cancel();


            },
            function (errorPayload) {
                $scope.OperationFailure = true;
                $log.error('failure: Error while Re-Scanning loading document', errorPayload);
            });
    }

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
}]);