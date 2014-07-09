@geotownApp.controller('CreateWaypointModalCtrl', ($scope, $modalInstance, geotown, route, $timeout) ->
  $scope.data = {
    routeId: route.id
    wrongAnswers: []
    rightAnswer: ""
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



  $scope.addWrongAnswer = (answer) ->
    return if $scope.data.wrongAnswers.indexOf(answer) > -1

    $scope.data.wrongAnswers.push(answer)
    $scope.text = ""

  $scope.removeWrongAnswer = (index) ->
    $scope.data.wrongAnswers = $scope.data.wrongAnswers.splice(index, 1)

  $scope.ok = ->
    $scope.creationPromise = geotown.createWaypoint($scope.data).then (resp) ->
      $modalInstance.close(resp)


  $scope.cancel = ->
    $modalInstance.dismiss('cancel')

  $timeout(() ->
    $scope.showMap = true
  , 10)

)