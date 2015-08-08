reportApp.controller('ApproverController', ['$scope', '$modal', 'ReportService',
                            function ($scope, $modal, ReportService) {

                                $scope.OperationSuccess = false;
                                $scope.OperationFailure = false;
                                var promise = ReportService.getAllRecordsForApprover();
                                promise.then(
                                    function (payload) {
                                        $scope.docRecords = payload;
                                    },
                                    function (errorPayload) {
                                        $log.error('failure loading movie', errorPayload);
                                    });

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
                                            $log.error('failure: Error while Re-Scanning loading document', errorPayload);
                                        });
                                }




                                $scope.openCommentsPopup = function (doc) {
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