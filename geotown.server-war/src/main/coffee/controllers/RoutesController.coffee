@geotownApp.controller('RoutesController', ($scope, $modal, geotown, $state, $rootScope) ->
  $scope.routes = []

  $scope.fetchRoutes = ->
    geotown.getMyRoutes (routes) ->
      $scope.routes = routes
      $scope.$apply()

  $scope.showCreateRouteModal = ->
    modalInstance = $modal.open {
      templateUrl: 'createRouteModal.html'
      controller: 'CreateRouteModalCtrl'
    }

    modalInstance.result.then((route) ->
      geotown.createRoute(route, (resp) ->
        $scope.routes.push resp
        $state.go('routes.detail', {id: resp.id})
      )
    )

  $rootScope.$on 'user:login', $scope.fetchRoutes
)