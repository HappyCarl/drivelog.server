@geotownApp.controller('GeotownAppCtrl', ($scope, $window, geotown) ->
  $window.init = ->
    $scope.$apply($scope.initApi)

  $scope.listRoutes = ->
    $scope.$apply()

  $scope.login = ->
    geotown.login false, (respCode) ->
      alert "HI" if(!respCode)

  $scope.initApi = () ->
    geotown.init( ->
      $scope.is_backend_ready = true
      geotown.login true, ->
        $scope.listRoutes()
    )
)