@geotownApp.controller('CreateRouteModalCtrl', ($scope, $modalInstance, $timeout, geotown) ->
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
  $scope.showMap = false
  $scope.creationPromise = null

  $scope.ok = (routeForm) ->
    console.log routeForm
    if routeForm.$valid
      $scope.creationPromise = geotown.createRoute($scope.data).then (resp) ->
        $modalInstance.close(resp)
    else
      alert("The form data is invalid!")


  $scope.cancel = ->
    $modalInstance.dismiss('cancel')

  $timeout(() ->
    $scope.showMap = true
  , 10)
)