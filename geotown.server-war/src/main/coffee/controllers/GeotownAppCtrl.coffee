@geotownApp.controller('GeotownAppCtrl', ($scope, $rootScope, $window, geotown) ->
  $rootScope.loggedIn = false

  $window.init = ->
    $scope.$apply($scope.initApi)


  $rootScope.$on 'user:login', () ->
    $rootScope.loggedIn = true

  $scope.login = ->
    geotown.login (false).then ->
      $rootScope.$broadcast('user:login')

  $scope.initApi = () ->
    geotown.init( ->
      $scope.isBackendReady = true

      geotown.login(true).then ->
        $rootScope.$broadcast('user:login')
      , () ->
        $rootScope.loggedIn = false
    )
)