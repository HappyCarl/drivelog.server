@geotownApp.controller('GeotownAppCtrl', ($scope, $window, geotown) ->

  $scope.myRoutes = []

  $window.init = ->
    $scope.$apply($scope.initApi)

  $scope.fetchRoutes = ->
    geotown.getMyRoutes (routes) ->
      $scope.myRoutes = routes
      $scope.$apply()

  $scope.login = ->
    geotown.login false, (resp) ->
      alert "HI" if(!resp.code)

  $scope.initApi = () ->
    geotown.init( ->
      $scope.is_backend_ready = true
      geotown.login true, (resp) ->
        $scope.fetchRoutes()
    )
)