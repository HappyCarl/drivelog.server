@geotownApp.controller('CreateWaypointModalCtrl', ($scope, $modalInstance, geotown, route, $timeout, $upload, cfpLoadingBar) ->
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
  $scope.fileBlobKey = ""


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


  $scope.onFileSelect = ($files) ->
    file = $files[0]

    geotown.getUploadUrl().then (resp) ->
      cfpLoadingBar.start()
      $scope.upload = $upload.upload(
        url: resp.uploadUrl

        file: file
        fileFormDataName: "image"
      ).progress((evt) ->
        cfpLoadingBar.set(evt.loaded / evt.total)
        console.log "percent: " + parseInt(100.0 * evt.loaded / evt.total)
      ).success((data, status, headers, config) ->
        cfpLoadingBar.complete()
        data = JSON.parse(data)
        console.log data
        $scope.fileBlobKey = data.blobkey
        $scope.$apply()
      )

  $timeout(() ->
    $scope.showMap = true
  , 10)

)