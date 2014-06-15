@geotownApp.controller('GeotownAppCtrl', ($scope, $rootScope, $window, geotown) ->

  $scope.myRoutes = []

  $window.init = ->
    $scope.$apply($scope.initApi)

  $rootScope.$on 'user:login', () ->
    $scope.loggedIn = true
    $scope.$apply()

  $scope.login = ->
    geotown.login false, (resp) ->
      $rootScope.$broadcast('user:login') if(!resp.code)

  $scope.initApi = () ->
    geotown.init( ->
      $scope.isBackendReady = true
      geotown.login true, (resp) ->
        $rootScope.$broadcast('user:login') if(!resp.code)
    )
)