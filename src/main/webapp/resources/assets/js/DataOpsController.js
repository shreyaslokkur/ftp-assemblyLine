reportApp.controller('DataOpsController', ['$scope', '$modal','$log', 'ReportService',
                            function ($scope, $modal,$log, ReportService) {

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
                                            $scope.FailureMsg = errorPayload;
                                            //$log.error('Error in GetAllUsers', errorPayload);
                                        });


                                },



                                $scope.getMydocuments = function() {

                                    var promise = ReportService.getMydocuments();
                                promise.then(
                                    function (payload) {
                                        $scope.MydocRecords = payload;
                                       // $scope.recordCount = $scope.MydocRecords.length;
                                    },
                                    function (errorPayload) {
                                        $scope.OperationFailure = true;
                                        $scope.FailureMsg = errorPayload;
                                        //$log.error('failure: Error while getMydocuments()', errorPayload);
                                    });
                            },


            $scope.lockRecord = function (doc) {
                var promise = ReportService.lockRecord(doc,'do');
                promise.then(
                   function (payload) {
                       angular.extend(doc, payload);
                       if (doc.locked == true && $scope.userName != doc.lockedBy) {
                           //alert("Unable to Lock Record , this record has been locked by " + doc.userName)
                           $scope.OpereationFailure = true;
                           $scope.FailureMsg = "This record is already locked by " + doc.lockedBy;
                       }
                      
                   },
                   function (errorPayload) {
                       $scope.OperationFailure = true;
                       $scope.FailureMsg = errorPayload;
                       //$log.error('failure: Error while Locking document', errorPayload);
                   }); 
            },


            $scope.ReScanRecord = function (doc) {
                var promise = ReportService.RescanRecord(doc);
                promise.then(
                   function (payload) {
                       angular.extend(doc, payload);
                       $scope.OperationSuccess = true;
                       $scope.successMsg ="Document Sent for Re-scanning";
                       if ($scope.IsAllDocuments) {
                           var index = $scope.docRecords.indexOf(doc);
                           $scope.docRecords.splice(index, 1);
                       }
                       else if(!$scope.IsAllDocuments)
                       {
                           var index = $scope.MydocRecords.indexOf(doc);
                           $scope.MydocRecords.splice(index, 1);
                       }
                   },
                   function (errorPayload) {
                       $scope.OperationFailure = true;
                       $scope.FailureMsg = errorPayload;
                       //$log.error('failure: Error while Re-Scanning loading document', errorPayload);
                   });
            },
            
            $scope.CompleteRecord = function (doc) {
                var promise = ReportService.CompleteRecord(doc);
                promise.then(
                   function (payload) {
                       angular.extend(doc, payload);
                       $scope.OperationSuccess = true;
                       $scope.successMsg ="Document sent to Approver";
                       if ($scope.IsAllDocuments) {
                           var index = $scope.docRecords.indexOf(doc);
                           $scope.docRecords.splice(index, 1);
                       }
                       else if(!$scope.IsAllDocuments)
                       {
                           var index = $scope.MydocRecords.indexOf(doc);
                           $scope.MydocRecords.splice(index, 1);
                       }

                   },
                   function (errorPayload) {
                       $scope.OperationFailure = true;
                       $scope.FailureMsg = errorPayload;
                       //$log.error('failure: Error while Complete document', errorPayload);
                   });
            }
            
           

            $scope.openCommentsPopupForHold = function (doc) {
                doc.ViewOnly = false;
                var modalInstance = $modal.open({
                    templateUrl: 'myModalContent.html',
                    controller: 'ModalInstanceCtrl',
                    resolve: {
                        doc: function () {
                            return doc;
                        },
                        users: function(){
                            return null;
                        }
                    }
                });

                modalInstance.result.then(function (doc) {
                    $scope.HoldRecord(doc);
                }, function () {
                    //$log.info('Modal dismissed at: ' + new Date());
                });



            },
            $scope.openCommentsPopupForRescan = function (doc) {
                doc.ViewOnly = false;
                var modalInstance = $modal.open({
                    templateUrl: 'myModalContent.html',
                    controller: 'ModalInstanceCtrl',
                    resolve: {
                        doc: function () {
                            return doc;
                        },
                        users: function(){
                            return null;
                        }
                    }
                });

                modalInstance.result.then(function (doc) {
                    $scope.ReScanRecord(doc);
                }, function () {
                    //$log.info('Modal dismissed at: ' + new Date());
                });



            },


            $scope.HoldRecord = function (doc) {
                var promise = ReportService.HoldRecord(doc);
                promise.then(
                    function (payload) {
                        angular.extend(doc, payload);
                        $scope.OperationSuccess = true;
                        $scope.successMsg ="Document is put on Hold";
                        if ($scope.IsAllDocuments) {
                            var index = $scope.docRecords.indexOf(doc);
                            $scope.docRecords.splice(index, 1);
                        }
                        else if(!$scope.IsAllDocuments)
                        {
                            var index = $scope.MydocRecords.indexOf(doc);
                            $scope.MydocRecords.splice(index, 1);
                        }

                    },
                    function (errorPayload) {
                        $scope.OperationFailure = true;
                        $scope.FailureMsg = errorPayload;

                        //$log.error('failure: Error while Complete document', errorPayload);
                    });
            },


            $scope.ShowComments = function (doc) {
                doc.ViewOnly = true;
                var modalInstance = $modal.open({
                    controller: "ModalInstanceCtrl",
                    templateUrl: 'myModalContent.html',
                    resolve: {
                        doc: function () {
                            return doc;
                        },
                        users: function(){
                            return null;
                        }
                    }
                });

            };

            var init = function () {
                $scope.OperationSuccess = false;
                $scope.OperationFailure = false;
                $scope.IsAllDocuments=true;
                $scope.getCurrentUser();
                //Initial Load
                var promise = ReportService.getAllRecords();
                promise.then(
                    function (payload) {
                        $scope.docRecords = payload;
                        $scope.getMydocuments();
                    },
                    function (errorPayload) {
                        $scope.OperationFailure = true;
                        $scope.FailureMsg = errorPayload;
                        //$log.error('failure: Error at getAllRecords', errorPayload);
                    });


                //Refresh every Minute
                setInterval(function () {
                    var promise = ReportService.getAllRecords();
                    promise.then(
                        function (payload) {
                            $scope.docRecords = payload;
                        },
                        function (errorPayload) {
                            $scope.OperationFailure = true;
                            $scope.FailureMsg = errorPayload;
                            //$log.error('failure: Error while getAllRecords()', errorPayload);
                        });

                }, 180 * 1000)
            };

            init();

}]);





