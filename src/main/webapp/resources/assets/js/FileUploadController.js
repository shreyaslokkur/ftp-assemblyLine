reportApp.controller('FileUploadController', ['$scope', '$modal','$filter' ,'ReportService',
                            function ($scope, $modal, $filter, ReportService) {

                                $scope.getCurrentUser = function () {

                                    var promise = ReportService.getCurrentUser();
                                    promise.then(
                                        function (payload) {
                                            $scope.userName = payload.username;
                                            $scope.doc.branchCode = payload.branchCode;
                                            $scope.getAllRecordsforRescan();
                                            setInterval(function () {
                                                $scope.getAllRecordsforRescan();
                                            }, 180 * 1000);
                                            //$scope.user = {branchCode : $scope.Branches[0].branchCode};
                                        },
                                        function (errorPayload) {
                                            $scope.OperationFailure = true;
                                            $scope.FailureMsg = errorPayload;
                                            //////$log.error('Error in GetAllUsers', errorPayload);
                                        });


                                },
                                $scope.getAllRecordsforRescan= function()
                                {
                                    var promise = ReportService.getAllRecordsforRescan( $scope.doc.branchCode);
                                promise.then(
                                    function (payload) {
                                        $scope.docRecords = payload;
                                    },
                                    function (errorPayload) {
                                        $scope.OperationFailure = true;
                                        $scope.FailureMsg = errorPayload;
                                       // //$log.error('failure loading movie', errorPayload);
                                    });
                            },
                                $scope.lockRecord = function (doc) {
                                    var promise = ReportService.lockRecord(doc,'scanner');
                                    promise.then(
                                        function (payload) {
                                            angular.extend(doc, payload);
                                            if (doc.locked == true && $scope.userName != doc.lockedBy) {
                                                //alert("Unable to Lock Record , this record has been locked by " + doc.userName)
                                                $scope.OperationFailure = true;
                                                $scope.FailureMsg = "this record is already locked by " + doc.lockedBy;
                                            }else{
                                                $scope.selectForRescan(doc);
                                            }

                                        },
                                        function (errorPayload) {
                                           // //$log.error('failure: Error while Locking document', errorPayload);
                                            $scope.OperationFailure = true;
                                            $scope.FailureMsg = errorPayload;
                                        });
                                },
                                    $scope.getAllBranches = function()
                                    {

                                        var promise = ReportService.getAllBranches();
                                        promise.then(
                                            function (payload) {
                                                $scope.Branches = payload;
                                                $scope.doc = {branchCode : $scope.Branches[0].branchCode};
                                            },
                                            function (errorPayload) {
                                                $scope.OperationFailure = true;
                                                $scope.FailureMsg = errorPayload;
                                                //$log.error('Error in GetAllUsers', errorPayload);
                                            });


                                    },


                                $scope.uploadFile = function (doc) {
                                    var file = $scope.myFile;
                                    
                                    var uploadUrl = "/scanner/upload";



                                    var promise = ReportService.uploadFileToUrl(file,doc, uploadUrl);

                                    promise.then(
                                        function (payload) {

                                            $scope.OperationSuccess = true;
                                            $scope.successMsg = "File Upload Successful";
                                            //remove scope data after upload file
                                            $scope.ClearScopeData(doc);

                                            var foundItem = $filter('filter')($scope.docRecords, { documentId: payload  }, true)[0];
                                            var index = $scope.docRecords.indexOf(foundItem );
                                            if(index >= 0)
                                                $scope.docRecords.splice(index, 1);
                                        },
                                        function (errorPayload) {
                                            $scope.OperationFailure = true;
                                            $scope.FailureMsg =errorPayload;
                                            //$log.error('failure: Error whileScanning loading document', errorPayload);
                                        });
                                };

                                $scope.selectForRescan = function (doc) {
                                    $scope.doc = doc;
                                };



                                $scope.ClearScopeData = function(doc) {
                                    doc.branchName = "";
                                    doc.bookletNo="";
                                    doc.applicationNo="";
                                    doc.placeOfMeeting="";
                                    doc.numOfCustomers="";
                                    $('#fileName').val("");
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
                                    $scope.getAllBranches();
                                    $scope.getCurrentUser();


                                };

                                init();

                            }]);