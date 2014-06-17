@geotownApp.controller('RoutesController', ($scope, $modal, geotown, $state, $rootScope) ->
  $scope.routes = []

  $scope.fetchRoutes = ->
    $scope.routes = geotown.getMyRoutes().then (routes) ->
      if routes?
        $scope.routes = routes
      console.log $scope.routes

  $scope.showCreateRouteModal = ->
    modalInstance = $modal.open {
      templateUrl: 'createRouteModal.html'
      controller: 'CreateRouteModalCtrl'
    }

    modalInstance.result.then((route) ->
      geotown.createRoute(route).then (resp) ->
        $scope.routes.push resp
        $state.go('routes.detail', {id: resp.id})

    )

  $rootScope.$on 'user:login', $scope.fetchRoutes
)