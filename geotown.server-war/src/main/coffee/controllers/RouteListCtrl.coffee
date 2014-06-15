@geotownApp.controller('RouteListCtrl', ($scope, $modal, geotown, $rootScope) ->
  $scope.routes = []

  $scope.fetchRoutes = ->
    geotown.getMyRoutes (routes) ->
      $scope.myRoutes = routes
      $scope.$apply()

  $scope.showCreateRouteModal = ->
    modalInstance = $modal.open {
      templateUrl: 'createRouteModal.html'
      controller: 'CreateRouteModalCtrl'
    }

    modalInstance.result.then((route) ->
      geotown.createRoute(route, (resp) ->
        console.log(resp)
        $scope.fetchRoutes()
      )
    )

  $rootScope.$on 'user:login', $scope.fetchRoutes
)