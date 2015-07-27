reportApp.controller('FileUploadController', ['$scope', '$modal', 'ReportService',
                            function ($scope, $modal, ReportService) {

                                $scope.docRecords = ReportService.getAllRecords();
                                
                                $scope.uploadFile = function (doc) {
                                    var file = $scope.myFile;
                                    
                                    var uploadUrl = "/fileUpload";
                                    ReportService.uploadFileToUrl(file,doc, uploadUrl);
                                };

                                $scope.selectForRescan = function (doc) {
                                    $scope.doc = doc;
                                };

                            }]);