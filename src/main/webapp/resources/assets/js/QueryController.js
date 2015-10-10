reportApp.controller('QueryController', ['$scope', '$modal', 'ReportService',
    function ($scope, $modal, ReportService) {


            $scope.getAllRecordsForQueryResolver = function(pageNumber){
                    var promise = ReportService.getAllRecordsForQueryResolver(pageNumber);
                    promise.then(
                        function (payload) {
                            $scope.totalItems = payload.totalCount;
                            $scope.currentPage = $scope.currenPage;
                            $scope.docRecords = payload.documentList;
                        },
                        function (errorPayload) {
                            $scope.OperationFailure = true;
                             $scope.FailureMsg = errorPayload;
                            //$log.error('Error in getAllRecordsForApprover', errorPayload);
                        });
            },

                $scope.pageChanged = function(){
                    var promise = ReportService.getAllRecordsForQueryResolver($scope.currentPage);
                    promise.then(
                        function (payload) {
                            $scope.totalItems = payload.totalCount;
                            $scope.currentPage = $scope.currentPage;
                            $scope.docRecords = payload.documentList;
                            // $scope.getMydocuments();
                        },
                        function (errorPayload) {
                            $scope.OperationFailure = true;
                            $scope.FailureMsg = errorPayload;
                            //$log.error('failure: Error at getAllRecords', errorPayload);
                        });
                },

                $scope.getCurrentUser = function () {

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
            $scope.lockRecord = function (doc) {
                var promise = ReportService.lockRecord(doc,'resolver');
                promise.then(
                    function (payload) {
                        angular.extend(doc, payload);
                        if (doc.locked == true && $scope.userName != doc.lockedBy) {
                            //alert("Unable to Lock Record , this record has been locked by " + doc.userName)
                            $scope.OperationFailure = true;
                            $scope.FailureMsg = "this record is already locked by " + doc.lockedBy;
                        }

                    },
                    function (errorPayload) {
                        //$log.error('failure: Error while Locking document', errorPayload);
                        $scope.OperationFailure = true;
                        $scope.FailureMsg = errorPayload;
                    });
            }

        $scope.getAllUsersBasedOnRole = function(){
            var promise = ReportService.getAllUsersBasedOnRole();
            promise.then(
                function (payload) {
                   $scope.usersDo = payload;

                },
                function (errorPayload) {
                    //$log.error('failure: Error while Locking document', errorPayload);
                    $scope.OperationFailure = true;
                    $scope.FailureMsg = errorPayload;
                });
        },


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
                     $scope.FailureMsg = errorPayload;
                    //$log.error('failure: Error while Re-Scanning loading document', errorPayload);
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
                    },
                    users: function(){
                        return $scope.usersDo;
                    }
                }
            });

            modalInstance.result.then(function (doc) {
                $scope.resolveAndAssignRecord(doc);
            }, function () {
                //$log.info('Modal dismissed at: ' + new Date());
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
           $scope.getCurrentUser();
            $scope.getAllRecordsForQueryResolver(1);
            setInterval(function () {
                if($scope.currentPage==1){
                    $scope.getAllRecordsForQueryResolver(1);
                }
            }, 30 * 60 * 1000);
            $scope.getAllUsersBasedOnRole();

        };

        init();
    }]);
