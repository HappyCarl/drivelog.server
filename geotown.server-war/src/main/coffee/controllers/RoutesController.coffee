@geotownApp.controller('RoutesController', ($scope, $modal, geotown, $state, $rootScope) ->
  $scope.routes = []

  $scope.routesPromise = null

  $scope.fetchRoutes = ->
    $scope.routesPromise = geotown.getMyRoutes().then (routes) ->
      if routes?
        $scope.routes = routes

  $scope.showCreateRouteModal = ->
    modalInstance = $modal.open {
      templateUrl: 'createRouteModal.html'
      controller: 'CreateRouteModalCtrl'
    }

    modalInstance.result.then((route) ->
      $state.go('routes.detail', {id: route.id})
      $scope.routes.push route
    )

  $rootScope.$on 'user:login', $scope.fetchRoutes
  $rootScope.$on 'routes:refresh', $scope.fetchRoutes
)