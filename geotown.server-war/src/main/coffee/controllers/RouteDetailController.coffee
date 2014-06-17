@geotownApp.controller('RouteDetailController', ($rootScope, $scope, $state, geotown) ->
  $scope.route = null
  $scope.routePromise = null

  $scope.map = {
    center: {
      latitude: 45,
      longitude: -73
    },
    zoom: 8
  }

  $scope.onMarkerClicked = (marker, waypoint) ->
    marker.showWindow = true

    $scope.$apply()

  $scope.fetchRoute = ->
    $scope.routePromise = geotown.getRoute($state.params.id).then (route) ->
      $scope.route = route
      $scope.map.center.latitude = route.latitude
      $scope.map.center.longitude = route.longitude


  if(!$rootScope.loggedIn)
    $rootScope.$on 'user:login', $scope.fetchRoute
  else
    $scope.fetchRoute()
)
