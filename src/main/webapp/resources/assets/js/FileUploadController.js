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
                                        $log.error('failure loading movie', errorPayload);
                                    });


                                $scope.uploadFile = function (doc) {
                                    var file = $scope.myFile;
                                    
                                    var uploadUrl = "/scanner/upload";



                                    var promise = ReportService.uploadFileToUrl(file,doc, uploadUrl);

                                    promise.then(
                                        function (payload) {

                                            $scope.OperationSuccess = true;

                                            //remove scope data after upload file
                                            $scope.ClearScopeData(doc);
                                        },
                                        function (errorPayload) {
                                            $scope.OperationFailure = true;
                                            $log.error('failure: Error while Re-Scanning loading document', errorPayload);
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
                                }



                            }]);