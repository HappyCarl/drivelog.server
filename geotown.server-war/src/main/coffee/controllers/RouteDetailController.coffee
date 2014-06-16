@geotownApp.controller('RouteDetailController', ($rootScope, $scope, $state, geotown) ->
  $scope.route = null

  $scope.fetchRoute = ->
    geotown.getRoute($state.params.id, (route) ->
      $scope.route = route
      $scope.$apply()
      console.log route
    )

  $rootScope.$on 'user:login', $scope.fetchRoute

)
