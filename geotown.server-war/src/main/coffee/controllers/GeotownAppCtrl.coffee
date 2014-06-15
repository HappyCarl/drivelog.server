@geotownApp.controller('GeotownAppCtrl', ($scope, $window, geotown) ->
  $window.init = ->
    $scope.$apply($scope.initApi)

  $scope.listRoutes = ->
    $scope.$apply()

  $scope.initApi = () ->
    geotown.init( ->
      $scope.is_backend_ready = true
      geotown.login ->
        $scope.listRoutes()
    )
)