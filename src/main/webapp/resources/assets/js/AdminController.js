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


        }

        $scope.getAllBranches = function()
        {

            var promise = ReportService.getAllBranches();
            promise.then(
                function (payload) {
                    $scope.Branches = payload;
                },
                function (errorPayload) {
                    $scope.OperationFailure = true;
                    $scope.FailureMsg = "We are facing technical difficulties , Please contact ur system Administrator";
                    $log.error('Error in GetAllUsers', errorPayload);
                });


        }

        var init = function () {
            $scope.OperationSuccess = false;
            $scope.OperationFailure = false;

            $scope.Roles = {

                data: [{Code: 'ROLE_DO', RoleDesc: 'Data Operator'},
                {Code: 'ROLE_SCANNER', RoleDesc: 'Scanner'},
                {Code: 'ROLE_RESOLVER', RoleDesc: 'Resolver'},
                {Code: 'ROLE_APPROVER', RoleDesc: 'Approver'},
                {Code: 'ROLE_ADMIN', RoleDesc: 'Admin'}

            ]};
            $scope.getAllBranches();
           //$scope.getAllUsers();
        };



        init();
    }]);