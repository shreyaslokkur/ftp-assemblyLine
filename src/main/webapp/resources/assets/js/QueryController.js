reportApp.controller('QueryController', ['$scope', '$modal', 'ReportService',
    function ($scope, $modal, ReportService) {

        $scope.OperationSuccess = false;
        $scope.OperationFailure = false;
        var promise = ReportService.getAllRecordsForQueryResolver();
        promise.then(
            function (payload) {
                $scope.docRecords = payload;
            },
            function (errorPayload) {
                $scope.OperationFailure = true;
                $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                $log.error('Error in getAllRecordsForApprover', errorPayload);
            });



            $scope.lockRecord = function (doc) {
                var promise = ReportService.lockRecord(doc,'resolver');
                promise.then(
                    function (payload) {
                        angular.extend(doc, payload);
                        if (doc.locked != true) {
                            //alert("Unable to Lock Record , this record has been locked by " + doc.userName)
                            $scope.OperationFailure = true;
                            $scope.FailureMsg = "This record is being processing by another person";

                        }

                    },
                    function (errorPayload) {
                        $log.error('failure: Error while Locking document', errorPayload);
                        $scope.OperationFailure = true;
                        $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                    });
            }




        $scope.resolveAndAssignRecord = function (doc) {
            var promise = ReportService.resolveAndAssignRecord(doc);
            promise.then(
                function (payload) {
                    angular.extend(doc, payload);
                    $scope.OperationSuccess = true;
                    $scope.successMsg = "Resolved and Sent to Assignee!"
                    var index = $scope.docRecords.indexOf(doc);
                    $scope.docRecords.splice(index, 1);

                },
                function (errorPayload) {
                    $scope.OperationFailure = true;
                    $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                    $log.error('failure: Error while Re-Scanning loading document', errorPayload);
                });
        },


        $scope.openCommentsPopupForResolve = function (doc) {
            doc.ViewOnly = false;
            var modalInstance = $modal.open({
                controller: "ModalInstanceCtrl",
                templateUrl: 'myModalContent.html',
                resolve: {
                    doc: function () {
                        return doc;
                    }
                }
            });

            modalInstance.result.then(function (doc) {
                $scope.resolveAndAssignRecord(doc);
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });

        };


        $scope.ShowComments = function (doc) {
            doc.ViewOnly = true;
            var modalInstance = $modal.open({
                controller: "ModalInstanceCtrl",
                templateUrl: 'myModalContent.html',
                resolve: {
                    doc: function () {
                        return doc;
                    }
                }
            });

        };


    }]);
