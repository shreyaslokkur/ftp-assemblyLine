reportApp.controller('FileUploadController', ['$scope', '$modal', 'ReportService',
                            function ($scope, $modal, ReportService) {

                                $scope.OperationSuccess = false;
                                $scope.OperationFailure = false;

                                var promise = ReportService.getAllRecordsforRescan();
                                promise.then(
                                        function (payload) {
                                        $scope.docRecords = payload;
                                    },
                                    function (errorPayload) {
                                        $scope.OperationFailure = true;
                                        $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                                        $log.error('failure loading movie', errorPayload);
                                    });


                                $scope.lockRecord = function (doc) {
                                    var promise = ReportService.lockRecord(doc,'scanner');
                                    promise.then(
                                        function (payload) {
                                            angular.extend(doc, payload);
                                            if (doc.locked != true) {
                                                //alert("Unable to Lock Record , this record has been locked by " + doc.userName)
                                                $scope.OperationFailure = true;
                                                $scope.FailureMsg = "This record is being processing by another person";

                                            }else{
                                                $scope.selectForRescan(doc);
                                            }

                                        },
                                        function (errorPayload) {
                                            $log.error('failure: Error while Locking document', errorPayload);
                                            $scope.OperationFailure = true;
                                            $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                                        });
                                }


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
                                        },
                                        function (errorPayload) {
                                            $scope.OperationFailure = true;
                                            $scope.FailureMsg = "Error while Re-Scanning loading document";
                                            $log.error('failure: Error whileScanning loading document', errorPayload);
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
                                                }
                                            }
                                        });

                                    };



                            }]);