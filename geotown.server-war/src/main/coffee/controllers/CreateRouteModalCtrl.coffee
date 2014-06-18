@geotownApp.controller('CreateRouteModalCtrl', ($scope, $modalInstance, geotown) ->
  $scope.data = {
    name: "",
    latitude: 0,
    longitude: 0
  }
  $scope.creationPromise = null

  $scope.ok = ->
    $scope.creationPromise = geotown.createRoute($scope.data).then (resp) ->
      $modalInstance.close(resp)


  $scope.cancel = ->
    $modalInstance.dismiss('cancel')

)