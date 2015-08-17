reportApp.controller('AdminController', ['$scope', '$modal', 'ReportService',
    function ($scope, $modal, ReportService) {


        $scope.getAllUsers = function()
        {

            var promise = ReportService.getAllUsers();
            promise.then(
                function (payload) {
                    $scope.Users = payload;
                },
                function (errorPayload) {
                    $scope.OperationFailureForUser = true;
                    $scope.FailureMsgForUser = "We are facing technical difficulties , Please contact ur system Administrator";
                    $log.error('Error in GetAllUsers', errorPayload);
                });


        },

            $scope.getAllRoles = function()
            {

                var promise = ReportService.getAllRoles();
                promise.then(
                    function (payload) {
                        $scope.Roles = payload;
                       // $scope.user = {userRole : $scope.Roles[0].roleName};
                    },
                    function (errorPayload) {
                        $scope.OperationFailureForUser = true;
                        $scope.FailureMsgForUser = "We are facing technical difficulties , Please contact ur system Administrator";
                        $log.error('Error in getAllRoles', errorPayload);
                    });


            },

            $scope.getAllBranches = function()
            {

                var promise = ReportService.getAllBranches();
                promise.then(
                    function (payload) {
                        $scope.Branches = payload;
                        //$scope.user = {branchCode : $scope.Branches[0].branchCode};
                    },
                    function (errorPayload) {
                        $scope.OperationFailureForBranch = true;
                        $scope.FailureMsgForBranch = "We are facing technical difficulties , Please contact ur system Administrator";
                        $log.error('Error in GetAllUsers', errorPayload);
                    });


            },

            $scope.getCurrentUser = function()
            {

                var promise = ReportService.getCurrentUser();
                promise.then(
                    function (payload) {
                        $scope.userName = payload.username;
                        //$scope.user = {branchCode : $scope.Branches[0].branchCode};
                    },
                    function (errorPayload) {
                        $scope.OperationFailureForUser = true;
                        $scope.FailureMsgForUser = "We are facing technical difficulties , Please contact ur system Administrator";
                        $log.error('Error in GetAllUsers', errorPayload);
                    });


            },

            $scope.createBranch = function(branch)
        {

            var promise = ReportService.createBranch(branch);
            promise.then(
                function (payload) {
                    if (parseInt(payload) > 0) {

                        var newbranch = angular.copy(branch)
                        $scope.Branches.push(newbranch);
                        $scope.OperationSuccessForBranch = true;
                        $scope.successMsgForBranch = "Branch Created Succssfully!";
                        $scope.ClearBranchForm();
                    }



                },
                function (errorPayload) {
                    $scope.OperationFailureForBranch = true;
                    $scope.FailureMsgForBranch = "We are facing technical difficulties , Please contact ur system Administrator";
                    $log.error('Error in createBranch', errorPayload);
                });


        },
            $scope.ClearBranchForm = function(){
                $scope.branch.branchCode  = '';
                $scope.branch.branchName = '';
                $scope.branch.zone = '';
                $scope.branch.region = '';
            },
            $scope.ClearUserForm = function(){
                $scope.user.userRole  = $scope.Roles[0].roleName;
                $scope.user.username = '';
                $scope.user.password = '';
                $scope.confirmPassword = '';
            },

            $scope.createUser = function(user)
            {
                //branchCode applicable only to Scanners
                if(user.userRole!="ROLE_SCANNER")
                    user.branchCode=0;

                var promise = ReportService.createUser(user);
                promise.then(
                    function (payload) {
                        if(parseInt(payload) > 0)
                        {
                            var NewUser = angular.copy(user);
                            $scope.Users.push(NewUser);
                            $scope.OperationSuccessForUser = true;
                            $scope.successMsgForUser = "User Created Succssfully!";
                            $scope.ClearUserForm();

                        }





                    },
                    function (errorPayload) {
                        $scope.OperationFailureForUser = true;
                        $scope.FailureMsgForUser = "We are facing technical difficulties , Please contact ur system Administrator";
                        $log.error('Error in createUser', errorPayload);
                    });


            },


            $scope.deleteUser = function(user)
            {

                var promise = ReportService.deleteUser(user);
                promise.then(
                    function (payload) {
                        if(payload==true){
                            var index = $scope.Users.indexOf(user);
                            $scope.Users.splice(index, 1);
                            $scope.OperationSuccessForUser = true;
                            $scope.OperationSuccessForUser = "User Deleted Successfully!"
                        }
                    },
                    function (errorPayload) {
                        $scope.OperationFailureForUser = true;
                        $scope.FailureMsgForUser = "We are facing technical difficulties , Please contact ur system Administrator";
                        $log.error('Error in deleteUser', errorPayload);
                    });


            },

            $scope.deleteBranch = function(branch)
            {

                var promise = ReportService.deleteBranch(branch);
                promise.then(
                    function (payload) {
                        if(payload==true){
                        var index = $scope.Branches.indexOf(branch);
                        $scope.Branches.splice(index, 1);
                        $scope.OperationSuccessForBranch = true;
                        $scope.successMsgForBranch = "Branch Deleted Successfully!"
                        }
                    },
                    function (errorPayload) {
                        $scope.OperationFailureForBranch = true;
                        $scope.FailureMsgForBranch = "We are facing technical difficulties , Please contact ur system Administrator";
                        $log.error('Error in deleteBranch', errorPayload);
                    });


            },



            $scope.getAllDocumentsForAdmin = function()
            {

                var promise = ReportService.getAllDocumentsForAdmin();
                promise.then(
                    function (payload) {
                        $scope.docRecords = payload;
                    },
                    function (errorPayload) {
                        $scope.OperationFailure = true;
                        $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                        $log.error('Error in GetAllUsers', errorPayload);
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
                        users: function () {
                            return null;
                        }
                    }
                });

            };

        var init = function () {
            $scope.OperationSuccess = false;
            $scope.OperationFailure = false;
            $scope.OperationSuccessForUser = false;
            $scope.OperationFailureForUser = false;
            $scope.OperationSuccessForBranch = false;
            $scope.OperationFailureForBranch = false;


            $scope.getCurrentUser();
            $scope.getAllUsers();
            $scope.getAllRoles();
            $scope.getAllBranches();

            $scope.getAllDocumentsForAdmin();
        };



        init();
    }]);