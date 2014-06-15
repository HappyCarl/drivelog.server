@geotownApp.controller('GeotownAppCtrl', ($scope, $window, geotown) ->
  $window.init = ->
    $scope.$apply($scope.initApi)

  $scope.listRoutes = ->
    $scope.$apply()

  $scope.login = ->
    geotown.login false, (resp) ->
      alert "HI" if(!resp.code)

  $scope.initApi = () ->
    geotown.init( ->
      $scope.is_backend_ready = true
      geotown.login true, (resp) ->
        $scope.listRoutes() if !resp.code
    )
)