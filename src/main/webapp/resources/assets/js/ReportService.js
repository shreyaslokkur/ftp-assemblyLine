reportApp.factory('ReportService', ['$http', '$log', '$q','$filter', function ($http,$log, $q, $filter) {
   

        var reportService = {};

        reportService.data = reportService.data = [
                {
                    documentId: '0000',
                    state: '',
                    fileName: 'Sample File',
                    fileLocation: 'C:\\Users\\U400475\\Desktop\\Personal\\Learning\\Web_api.pdf',
                    createdBy: 'Shreyas lokkur',
                    branchName: 'jayanagar',//Nov/16/2014 5:30:PM
                    placeOfMeeting: 'jayanagar',
                    bookletNumber: '12345',
                    applicationNumber: '1234rd',
                    noOfCustomers: '100',
                    lockedBy: 'Mano',
                    completedBy: 'Test',
                    approvedBy: 'Approver',
                    assignedTo: 'UserName',
                    queryLevel: 3,
                    onHold: false,
                    locked: false,
                    approved: false,
                    recCreatedOn: '07/01/2015 12:10 AM',
                    recApprovedOn: '',
                    recCompletedOn: '07/01/2015 12:30 AM',
                    comments: [{ commentId: 1, documentId: '0000', comments: 'This is my first comment 1', commentedBy: 'UserName' },
                               { commentId: 1, documentId: '0000', comments: 'This is my second comment 2', commentedBy: 'UserName' },
                               { commentId: 1, documentId: '0000', comments: 'This is my third comment 3', commentedBy: 'UserName' }        
                              ]
                },
                  {
                      documentId: '0001',
                      state: '',
                      fileName: 'Sample File',
                      fileLocation: 'C:\\Users\\U400475\\Desktop\\Personal\\Learning\\Web_api.pdf',
                      createdBy: 'Shreyas lokkur',
                      branchName: 'jayanagar',//Nov/16/2014 5:30:PM
                      placeOfMeeting: 'jayanagar',
                      bookletNumber: '12345',
                      applicationNumber: '1234rd',
                      noOfCustomers: '100',
                      lockedBy: 'Mano',
                      completedBy: 'Test',
                      approvedBy: 'Approver',
                      assignedTo: 'UserName',
                      queryLevel: 4,
                      onHold: false,
                      locked: false,
                      approved: false,
                      recCreatedOn: '07/01/2015 12:03 AM',
                      recApprovedOn: '',
                      recCompletedOn: '07/01/2015 12:30 AM',
                      comments: []



                  },
                  {
                      documentId: '0002',
                      state: '',
                      fileName: 'Sample File',
                      fileLocation: 'C:\\Users\\U400475\\Desktop\\Personal\\Learning\\Web_api.pdf',
                      createdBy: 'Shreyas lokkur',
                      branchName: 'jayanagar',//Nov/16/2014 5:30:PM
                      placeOfMeeting: 'jayanagar',
                      bookletNumber: '12345',
                      applicationNumber: '1234rd',
                      noOfCustomers: '100',
                      lockedBy: 'Mano',
                      completedBy: 'Test',
                      approvedBy: 'Approver',
                      assignedTo: 'UserName',
                      queryLevel: 4,
                      onHold: false,
                      locked: false,
                      approved: false,
                      recCreatedOn: '07/01/2015 12:01 AM',
                      recApprovedOn: '',
                      recCompletedOn: '07/01/2015 12:30 AM',
                      comments: []



                  },
                    {
                        documentId: '0003',
                        state: '',
                        fileName: 'Sample File',
                        fileLocation: 'C:\Users\U400475\Desktop\Personal\Learning\Web_api.pdf',
                        createdBy: 'Shreyas lokkur',
                        branchName: 'jayanagar',//Nov/16/2014 5:30:PM
                        placeOfMeeting: 'jayanagar',
                        bookletNumber: '12345',
                        applicationNumber: '1234rd',
                        noOfCustomers: '100',
                        lockedBy: 'Mano',
                        completedBy: 'Test',
                        approvedBy: 'Approver',
                        assignedTo: 'UserName',
                        queryLevel: 3,
                        onHold: false,
                        locked: false,
                        approved: false,
                        recCreatedOn: '07/01/2015 11:30 AM',
                        recApprovedOn: '',
                        recCompletedOn: '07/01/2015 11:40 AM',
                        comments: []



                    },
                    {
                        documentId: '0004',
                        state: '',
                        fileName: 'Sample File',
                        fileLocation: 'C:\Users\U400475\Desktop\Personal\Learning\Web_api.pdf',
                        createdBy: 'Shreyas lokkur',
                        branchName: 'jayanagar',//Nov/16/2014 5:30:PM
                        placeOfMeeting: 'jayanagar',
                        bookletNumber: '12345',
                        applicationNumber: '1234rd',
                        noOfCustomers: '100',
                        lockedBy: 'Mano',
                        completedBy: 'Test',
                        approvedBy: 'Approver',
                        assignedTo: 'UserName',
                        queryLevel: 3,
                        onHold: false,
                        locked: false,
                        approved: false,
                        recCreatedOn: '07/01/2015 11:15 AM',
                        recApprovedOn: '',
                        recCompletedOn: '07/01/2015 11:30 AM',
                        comments: []



                    },
                     {
                         documentId: '0005',
                         state: '',
                         fileName: 'Sample File',
                         fileLocation: 'C:\Users\U400475\Desktop\Personal\Learning\Web_api.pdf',
                         createdBy: 'Shreyas lokkur',
                         branchName: 'jayanagar',//Nov/16/2014 5:30:PM
                         placeOfMeeting: 'jayanagar',
                         bookletNumber: '12345',
                         applicationNumber: '1234rd',
                         noOfCustomers: '100',
                         lockedBy: 'Mano',
                         completedBy: 'Test',
                         approvedBy: 'Approver',
                         assignedTo: 'UserName',
                         queryLevel: 3,
                         onHold: false,
                         locked: false,
                         approved: false,
                         recCreatedOn: '07/01/2015 11:10 AM',
                         recApprovedOn: '',
                         recCompletedOn: '07/01/2015 11:30 AM',
                         comments: [

                         ]



                     }



        ];

    reportService.getAllRecordsForApprover = function () {
        return reportService.data;
        },
    
        reportService.getAllRecords = function () {

            
            var deferred = $q.defer();
            $http.get('/do/getNewRecords')
              .success(function(data) { 
                  deferred.resolve(data);
              }).error(function(msg, code) {
                  deferred.reject(msg);
                  $log.error(msg, code);
              });
            return deferred.promise;

          
        };

           reportService.lockRecord = function (record) {

            var deferred = $q.defer();
                    $http.get('/do/lock', {
                        params: {
                            documentId: record.documentId
                        }
                    })
                   .success(function(data) { 
                        deferred.resolve(data);
                    }).error(function(msg, code) {
                        deferred.reject(msg);
                        $log.error(msg, code);
                    });
                    return deferred.promise;
            };

        //Rescan
        reportService.RescanRecord = function (record) {

            var deferred = $q.defer();
            $http.get('/do/rescan', {
                params: {
                    documentId: record.documentId
                }
            })
           .success(function (data) {
               deferred.resolve(data);
           }).error(function (msg, code) {
               deferred.reject(msg);
               $log.error(msg, code);
           });
            return deferred.promise;
        };

        //Complete and send to approver
        reportService.CompleteRecord = function (record) {

            var deferred = $q.defer();
            $http.get('/do/complete', {
                params: {
                    documentId: record.documentId
                }
            })
           .success(function (data) {
               deferred.resolve(data);
           }).error(function (msg, code) {
               deferred.reject(msg);
               $log.error(msg, code);
           });
            return deferred.promise;
        };

        reportService.HoldRecord = function (record) {

            var deferred = $q.defer();
            $http.get('/do/hold', {
                params: {
                    documentId: record.documentId
                }
            })
           .success(function (data) {
               deferred.resolve(data);
           }).error(function (msg, code) {
               deferred.reject(msg);
               $log.error(msg, code);
           });
            return deferred.promise;
        };

        reportService.uploadFileToUrl = function (file,doc, uploadUrl) {
            var fd = new FormData();
            fd.append('file', file);
            $http.post(uploadUrl, {file :fd ,Document : doc}, {
                transformRequest: angular.identity,
                headers: { 'Content-Type': undefined }
            })
            .success(function () {
            })
            .error(function () {
            });
        };



        return reportService;


   
}]);

