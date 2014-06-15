@geotownApp.controller('CreateRouteModalCtrl', ($scope, $modalInstance, geotown) ->
  $scope.data = {
    name: "",
    latitude: 0,
    longitude: 0
  }
  $scope.ok = ->

    $modalInstance.close($scope.data)

  $scope.cancel = ->
    $modalInstance.dismiss('cancel')

)