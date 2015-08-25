reportApp.factory('ReportService', ['$http', '$log', '$q','$filter', function ($http,$log, $q, $filter) {
   

        var reportService = {};

      reportService.data = [
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
        var deferred = $q.defer();
        $http.get('/qa/getRecordsWhichNeedApproval')
            .success(function(data) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;
        },

        reportService.getAllRecordsforRescan = function (branchCode) {
            var deferred = $q.defer();
            $http.get('/scanner/getRescanDocumentsForBranch', {
                params: { branchCode: branchCode}
            })
                .success(function(data) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;
        },
        reportService.getAllRecordsForQueryResolver = function () {

            var deferred = $q.defer();
            $http.get('/resolver/getRecordsWhichAreInHold')
                .success(function(data) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;
        },
    
        reportService.getAllRecords = function () {

            
            var deferred = $q.defer();
            $http.get('/do/getNewRecords')
              .success(function(data) { 
                  deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;

          
        };

    reportService.getMydocuments = function () {


        var deferred = $q.defer();
        $http.get('/do/getAssignedRecords')
            .success(function(data) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;


    };
    reportService.getAllDocumentsForAdmin = function () {


        var deferred = $q.defer();
        $http.get('/admin/getAllDocuments')
            .success(function(data) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;


    };


    reportService.lockRecord = function (record,ServiceType) {

            var deferred = $q.defer();
                    $http.get('/'+ServiceType+'/lock', {
                        params: {
                            documentId: record.documentId
                        }
                    })
                   .success(function(data) { 
                        deferred.resolve(data);
                        }).error(function (data) {
                            deferred.reject(data.statusText);
                            //$log.error(data.statusText, data.exceptionCode);
                        });
                    return deferred.promise;
            };


    reportService.unLockRecord = function (record,ServiceType) {

        var deferred = $q.defer();
        $http.get('/'+ServiceType+'/unlock', {
            params: {
                documentId: record.documentId
            }
        })
            .success(function(data) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;
    };
        //Rescan
        reportService.RescanRecord = function (record) {

            var deferred = $q.defer();
            $http.get('/do/rescan', {
                params: {
                    documentId: record.documentId,
                    comment: record.newComment,
                    assignTo:''
                }
            })
           .success(function (data) {
               deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
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
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;
        };

        reportService.HoldRecord = function (record) {

            var deferred = $q.defer();
            $http.get('/do/hold', {
                params: {
                    documentId: record.documentId,
                    comment: record.newComment
                }
            })
           .success(function (data) {
               deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;
        };

    reportService.rejectRecord = function (record) {

        var deferred = $q.defer();
        $http.get('/qa/reject', {
            params: {
                documentId: record.documentId,
                comment: record.newComment,
                assignTo:record.completedBy
            }
        })
            .success(function (data) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;
    };

    reportService.approveRecord = function (record) {

        var deferred = $q.defer();
        $http.get('/qa/approve', {
            params: {
                documentId: record.documentId
            }
        })
            .success(function (data) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;
    };
        reportService.uploadFileToUrl = function (file,doc, uploadUrl) {
            var fd = new FormData();
            fd.append('file', file);
            fd.append('branchCode',doc.branchCode);
            fd.append('bookletNo',doc.bookletNo);
            fd.append('applicationNo',doc.applicationNo);
            fd.append('placeOfMeeting',doc.placeOfMeeting);
            fd.append('numOfCustomers',doc.numOfCustomers);
            if(!isNaN(doc.documentId))
                fd.append('documentId', doc.documentId);
            var data = 'file=' + fd;
           /* formData.append("file",file);*/
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: uploadUrl,
                headers: {'Content-Type': undefined},
                data: fd,
                transformRequest: function(data, headersGetterFunction) {
                    return data;
                }
            })
                .success(function(data, status) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;

        };

    reportService.resolveAndAssignRecord = function (record) {

        var deferred = $q.defer();
        $http.get('/resolver/resolve', {
            params: {
                documentId: record.documentId,
                comment: record.newComment,
                assignTo:record.putOnHoldBy
            }
        })
            .success(function (data) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;
    },




    reportService.getAllUsers= function(){

        var deferred = $q.defer();
        $http.get('/admin/getAllUsers', {

        })
            .success(function (data) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;

    }



    reportService.getCurrentUser= function(){

        var deferred = $q.defer();
        $http.get('/admin/getCurrentUser', {

        })
            .success(function (data) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;

    }

    reportService.createUser = function(user){

        var deferred = $q.defer();
        $http({
            method: 'POST',
            url: '/admin/createnewuser',
            data: JSON.stringify(user),
            headers: {'Content-Type': 'application/json'}


        })
            .success(function(data, status) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;

    },

        reportService.resetPassword= function(user){

            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/admin/editPassword',
                data: user

            })
                .success(function(data, status) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;

        },

        reportService.deleteUser= function(user){
            var deferred = $q.defer();
            $http.get('/admin/deleteUser', {
                params: { username: user.username}
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;

        },
        reportService.getAllUsersBasedOnRole= function(){

            var deferred = $q.defer();
            $http.get('/all/getAllUsersForRole', {
                params: { role: 'ROLE_DO'}

            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;

        },

        reportService.getAllBranches= function(){

        var deferred = $q.defer();
        $http.get('/all/getAllBranches', {

        })
            .success(function (data) {
                deferred.resolve(data);
            }).error(function (data) {
                deferred.reject(data.statusText);
                //$log.error(data.statusText, data.exceptionCode);
            });
        return deferred.promise;

    },
        reportService.getAllRoles= function(){

            var deferred = $q.defer();
            $http.get('/admin/getAllRoles', {

            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;

        },

        reportService.getCurrentUser= function(){

            var deferred = $q.defer();
            $http.get('/all/getCurrentUser', {

            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;

        },

        reportService.createBranch= function(branch){

            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/admin/createnewbranch',
                data: JSON.stringify(branch),
                headers: {'Content-Type': 'application/json'}

            })
                .success(function(data, status) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;

        },

        reportService.editBranch= function(branch){

            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: '/admin/editBranch',
                data: branch

            })
                .success(function(data, status) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;

        },

        reportService.deleteBranch = function(branch){

            var deferred = $q.defer();
            $http.get('/admin/deleteBranch', {
                params: { branchCode: branch.branchCode}
            })
                .success(function (data) {
                    deferred.resolve(data);
                }).error(function (data) {
                    deferred.reject(data.statusText);
                    //$log.error(data.statusText, data.exceptionCode);
                });
            return deferred.promise;

        }
    return reportService;


   
}]);

