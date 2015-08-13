reportApp.controller('ApproverController', ['$scope', '$modal', 'ReportService',
                            function ($scope, $modal, ReportService) {

                                $scope.getCurrentUser = function()
                                {

                                    var promise = ReportService.getCurrentUser();
                                    promise.then(
                                        function (payload) {
                                            $scope.userName = payload.username;
                                            //$scope.user = {branchCode : $scope.Branches[0].branchCode};
                                        },
                                        function (errorPayload) {
                                            $scope.OperationFailure = true;
                                            $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                                            $log.error('Error in GetAllUsers', errorPayload);
                                        });


                                },

                                $scope.approveRecord = function (doc) {
                                    var promise = ReportService.approveRecord(doc);
                                    promise.then(
                                        function (payload) {
                                            angular.extend(doc, payload);
                                            $scope.OperationSuccess = true;
                                            $scope.successMsg = "Processing Complete"
                                            var index = $scope.docRecords.indexOf(doc);
                                            $scope.docRecords.splice(index, 1);

                                        },
                                        function (errorPayload) {
                                            $scope.OperationFailure = true;
                                            $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                                            $log.error('failure: Error while Re-Scanning loading document', errorPayload);
                                        });
                                },

                                    $scope.lockRecord = function (doc) {

                                        var promise = ReportService.lockRecord(doc,'qa');
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


                                    $scope.rejectRecord = function (doc) {
                                        var promise = ReportService.rejectRecord(doc);
                                        promise.then(
                                            function (payload) {
                                                angular.extend(doc, payload);
                                                $scope.OperationSuccess = true;
                                                $scope.successMsg = "Record Rejected!"
                                                var index = $scope.docRecords.indexOf(doc);
                                                $scope.docRecords.splice(index, 1);

                                            },
                                            function (errorPayload) {
                                                $scope.OperationFailure = true;
                                                $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                                                $log.error('failure: Error while Re-Scanning loading document', errorPayload);
                                            });
                                    },

                                        $scope.getAllRecordsForApprover = function(){
                                            var promise = ReportService.getAllRecordsForApprover();
                                            promise.then(
                                                function (payload) {
                                                    $scope.docRecords = payload;
                                                },
                                                function (errorPayload) {
                                                    $scope.OperationFailure = true;
                                                    $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                                                    $log.error('Error in getAllRecordsForApprover', errorPayload);
                                                });
                                        },


                                $scope.openCommentsPopupForReject = function (doc) {
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
                                        $scope.rejectRecord(doc);
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


                                var init = function () {
                                    $scope.OperationSuccess = false;
                                    $scope.OperationFailure = false;
                                    $scope.getCurrentUser();
                                    $scope.getAllRecordsForApprover();

                                };

                                init();

                            }]);