@geotownApp.controller('CreateWaypointModalCtrl', ($scope, $modalInstance, geotown, route) ->
  $scope.data = {
    routeId: route.id
    answers: ["Peda", "Ulf"]
    question: "",
    latitude: 0,
    longitude: 0
  }

  $scope.creationPromise = null

  $scope.ok = ->
    $scope.data.latitude = parseFloat $scope.data.latitude
    $scope.data.longitude = parseFloat $scope.data.longitude
    $scope.creationPromise = geotown.createWaypoint($scope.data).then (resp) ->
      $modalInstance.close(resp)


  $scope.cancel = ->
    $modalInstance.dismiss('cancel')

)