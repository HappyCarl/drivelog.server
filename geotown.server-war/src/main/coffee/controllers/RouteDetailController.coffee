@geotownApp.controller('RouteDetailController', ($rootScope, $scope, $state, geotown) ->
  $scope.route = null
  $scope.map = {
    center: {
      latitude: 45,
      longitude: -73
    },
    zoom: 8
  }
  $scope.fetchRoute = ->
    geotown.getRoute($state.params.id, (route) ->
      $scope.route = route
      $scope.map.center.latitude = route.latitude
      $scope.map.center.longitude = route.longitude
      $scope.$apply()
    )

  if(!$rootScope.loggedIn)
    $rootScope.$on 'user:login', $scope.fetchRoute
  else
    $scope.fetchRoute()
)
