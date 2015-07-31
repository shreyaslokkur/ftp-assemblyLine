reportApp.controller('FileUploadController', ['$scope', '$modal', 'ReportService',
                            function ($scope, $modal, ReportService) {

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
                                    ReportService.uploadFileToUrl(file,doc, uploadUrl);
                                };

                                $scope.selectForRescan = function (doc) {
                                    $scope.doc = doc;
                                };

                            }]);