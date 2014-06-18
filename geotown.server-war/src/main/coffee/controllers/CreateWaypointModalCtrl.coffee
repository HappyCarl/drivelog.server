@geotownApp.controller('CreateWaypointModalCtrl', ($scope, $modalInstance, geotown, route, $timeout) ->
  $scope.data = {
    routeId: route.id
    answers: ["Peda", "Ulf"]
    question: "",
    latitude: 42,
    longitude: 8
  }
  $scope.map = {
    zoom: 10
    options: {
      disableDefaultUI: true
      streetViewControl: false
      panControl: false
    }
    refresh: false
    control: {}
  }
  $scope.showMap = false
  $scope.creationPromise = null

  $scope.ok = ->
    $scope.creationPromise = geotown.createWaypoint($scope.data).then (resp) ->
      $modalInstance.close(resp)


  $scope.cancel = ->
    $modalInstance.dismiss('cancel')

  $timeout(() ->
    $scope.showMap = true
  , 10)

)