@geotownApp.controller('RouteDetailController', ($rootScope, $scope, $state, geotown) ->
  $scope.route = null
  $scope.routePromise = null
  $scope.selectedWaypoint = {latitude: 0, longitude: 0}

  $scope.map = {
    center: {
      latitude: 0,
      longitude: 0
    },
    zoom: 8
  }

  $scope.$watch('selectedWaypoint', ->
    $scope.map.center.latitude = $scope.selectedWaypoint.latitude
    $scope.map.center.longitude = $scope.selectedWaypoint.longitude
  )

  $scope.onMarkerClicked = (marker) ->
    marker.showWindow = true
    $scope.selectedWaypoint = marker
    $scope.$apply()

  $scope.selectWaypoint = (w) ->
    $scope.selectedWaypoint = w

  $scope.fetchRoute = ->
    $scope.routePromise = geotown.getRoute($state.params.id).then (route) ->
      $scope.route = route
      $scope.selectedWaypoint = $scope.route.waypoints[0] if $scope.route.waypoints?

  if(!$rootScope.loggedIn)
    $rootScope.$on 'user:login', $scope.fetchRoute
  else
    $scope.fetchRoute()
)
