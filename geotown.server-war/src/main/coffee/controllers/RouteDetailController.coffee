@geotownApp.controller('RouteDetailController', ($rootScope, $scope, $state, geotown, $modal) ->
  $scope.route = null
  $scope.routePromise = null
  $scope.waypoints = []
  $scope.waypointsPromise = null
  $scope.selectedWaypoint = {latitude: 0, longitude: 0, init: false}

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

    modalInstance.result.then($scope.fetch)

  $scope.onMarkerClicked = (marker) ->
    marker.showWindow = true
    $scope.selectedWaypoint = marker
    $scope.$apply()

  $scope.selectWaypoint = (w) ->
    $scope.selectedWaypoint = w

  $scope.fetchRoute = ->
    $scope.routePromise = geotown.getRoute($state.params.id).then (route) ->
      $scope.route = route
      if not $scope.selectedWaypoint.init
        $scope.map.center = $scope.route

  $scope.fetchWaypoints = ->
    $scope.waypointsPromise = geotown.listWaypoints($state.params.id).then (waypoints) ->
      $scope.waypoints = waypoints

  $scope.fetch = ->
    $scope.fetchWaypoints()
    $scope.fetchRoute()

  if(!$rootScope.loggedIn)
    $rootScope.$on 'user:login', $scope.fetch
  else
    $scope.fetch()
)
