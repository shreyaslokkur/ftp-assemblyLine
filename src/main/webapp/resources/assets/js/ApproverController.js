reportApp.controller('ApproverController', ['$scope', '$modal', 'ReportService',
                            function ($scope, $modal, ReportService) {


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


                                        },
                                        function (errorPayload) {
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

                                $scope.rejectRecord = function (doc) {
                                    var promise = ReportService.rejectRecord(doc);
                                    promise.then(
                                        function (payload) {
                                            angular.extend(doc, payload);


                                        },
                                        function (errorPayload) {
                                            $log.error('failure: Error while Re-Scanning loading document', errorPayload);
                                        });
                                }

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