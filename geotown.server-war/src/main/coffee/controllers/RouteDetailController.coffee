@geotownApp.controller('RouteDetailController', ($rootScope, $scope, $state, geotown, $modal) ->
  $scope.route = null
  $scope.routePromise = null
  $scope.selectedWaypoint = {latitude: 0, longitude: 0}

  $scope.map = {
    center: {
      latitude: 0,
      longitude: 0
    },
    zoom: 12
  }

  $scope.$watch('selectedWaypoint', ->
    $scope.map.center.latitude = $scope.selectedWaypoint.latitude
    $scope.map.center.longitude = $scope.selectedWaypoint.longitude
  )

  $scope.showCreateWaypointModal = ->
    modalInstance = $modal.open {
      templateUrl: 'createWaypointModal.html'
      controller: 'CreateWaypointModalCtrl'
      resolve: {
        route: ->
          $scope.route
      }
    }

    modalInstance.result.then($scope.fetchRoute)

  $scope.onMarkerClicked = (marker) ->
    marker.showWindow = true
    $scope.selectedWaypoint = marker
    $scope.$apply()

  $scope.selectWaypoint = (w) ->
    $scope.selectedWaypoint = w

  $scope.fetchRoute = ->
    $scope.routePromise = geotown.getRoute($state.params.id).then (route) ->
      $scope.route = route
      if $scope.route.waypoints?
        $scope.selectedWaypoint = $scope.route.waypoints[0]
      else
        $scope.map.center = $scope.route

  if(!$rootScope.loggedIn)
    $rootScope.$on 'user:login', $scope.fetchRoute
  else
    $scope.fetchRoute()
)
