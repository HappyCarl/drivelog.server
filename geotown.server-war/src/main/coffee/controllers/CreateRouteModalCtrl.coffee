@geotownApp.controller('CreateRouteModalCtrl', ($scope, $modalInstance, geotown) ->
  $scope.data = {
    name: "",
    latitude: 52,
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
  }
  $scope.creationPromise = null

  $scope.ok = ->
    $scope.creationPromise = geotown.createRoute($scope.data).then (resp) ->
      $modalInstance.close(resp)


  $scope.cancel = ->
    $modalInstance.dismiss('cancel')

)