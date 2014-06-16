@geotownApp.controller('RouteDetailController', ($rootScope, $scope, $state, geotown) ->
  $scope.route = null

  $scope.fetchRoute = ->
    geotown.getRoute($state.params.id, (route) ->
      $scope.route = route
      $scope.$apply()
      console.log route
    )

  if(!$rootScope.loggedIn)
    $rootScope.$on 'user:login', $scope.fetchRoute
  else
    $scope.fetchRoute()
)
