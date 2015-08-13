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
                    $scope.OperationFailure = true;
                    $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
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
                        $scope.OperationFailure = true;
                        $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
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
                        $scope.OperationFailure = true;
                        $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                        $log.error('Error in GetAllUsers', errorPayload);
                    });


            },


            $scope.createBranch = function(branch)
        {

            var promise = ReportService.createBranch(branch);
            promise.then(
                function (payload) {
                    if(parseInt(payload) > 0)
                         $scope.Branches.push(branch);
                },
                function (errorPayload) {
                    $scope.OperationFailure = true;
                    $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                    $log.error('Error in createBranch', errorPayload);
                });


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
                            $scope.Users.push(user);
                    },
                    function (errorPayload) {
                        $scope.OperationFailure = true;
                        $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                        $log.error('Error in createBranch', errorPayload);
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
                            $scope.OperationSuccess = true;
                            $scope.successMsg = "User Deleted Successfully!"
                        }
                    },
                    function (errorPayload) {
                        $scope.OperationFailure = true;
                        $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
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
                        $scope.OperationSuccess = true;
                        $scope.successMsg = "Branch Deleted Successfully!"
                        }
                    },
                    function (errorPayload) {
                        $scope.OperationFailure = true;
                        $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
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
                        }
                    }
                });

            };

        var init = function () {
            $scope.OperationSuccess = false;
            $scope.OperationFailure = false;



            $scope.getAllUsers();
            $scope.getAllRoles();
            $scope.getAllBranches();
            //$scope.getAllDocumentsForAdmin();
        };



        init();
    }]);