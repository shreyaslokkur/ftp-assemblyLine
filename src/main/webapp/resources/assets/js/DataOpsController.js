reportApp.controller('DataOpsController', ['$scope', '$modal','$log', 'ReportService',
                            function ($scope, $modal,$log, ReportService) {
           
                                //Initial Load
                                var promise = ReportService.getAllRecords();
                                promise.then(
                                   function (payload) {
                                       $scope.docRecords = payload;
                                   },
                                   function (errorPayload) {
                                       $log.error('failure loading movie', errorPayload);
                                   });


                                //Refresh every Minute 
                                setInterval(function () {
                                    var promise = ReportService.getAllRecords();
                                    promise.then(
                                       function (payload) {
                                           $scope.docRecords = payload;
                                       },
                                       function (errorPayload) {
                                           $log.error('failure: Error while getAllRecords()', errorPayload);
                                       });

                                }, 100000)


                                $scope.getMydocuments = function() {

                                    var promise = ReportService.getMydocuments();
                                promise.then(
                                    function (payload) {
                                        $scope.MydocRecords = payload;
                                    },
                                    function (errorPayload) {
                                        $log.error('failure: Error while getMydocuments()', errorPayload);
                                    });
                            },


                                $scope.lockRecord = function (doc) {
                var promise = ReportService.lockRecord(doc);
                promise.then(
                   function (payload) {
                       angular.extend(doc, payload);
                       if (doc.locked != true) {
                           alert("Unable to Lock Record , this record has been locked by " + doc.userName)
                       }
                      
                   },
                   function (errorPayload) {
                       $log.error('failure: Error while Locking document', errorPayload);
                   }); 
            }


            $scope.ReScanRecord = function (doc) {
                var promise = ReportService.RescanRecord(doc);
                promise.then(
                   function (payload) {
                       angular.extend(doc, payload);
                      

                   },
                   function (errorPayload) {
                       $log.error('failure: Error while Re-Scanning loading document', errorPayload);
                   });
            }

            $scope.ViewRecord = function (doc) {
                var promise = ReportService.ViewRecord(doc);
                promise.then(
                    function (payload) {
                        angular.extend(doc, payload);


                    },
                    function (errorPayload) {
                        $log.error('failure: Unable to view the document', errorPayload);
                    });
            }
            
            $scope.CompleteRecord = function (doc) {
                var promise = ReportService.CompleteRecord(doc);
                promise.then(
                   function (payload) {
                       angular.extend(doc, payload);


                   },
                   function (errorPayload) {
                       $log.error('failure: Error while Complete document', errorPayload);
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





