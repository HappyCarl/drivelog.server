@geotownApp.controller('GeotownAppCtrl', ($scope, $rootScope, $window, geotown) ->

  $window.init = ->
    $scope.$apply($scope.initApi)


  $rootScope.$on 'user:login', () ->
    $rootScope.loggedIn = true
    $scope.$apply()

  $scope.login = ->
    geotown.login false, (resp) ->
      $rootScope.$broadcast('user:login') if(!resp.code)

  $scope.initApi = () ->
    geotown.init( ->
      $scope.isBackendReady = true
      $scope.$apply()
      console.log "ulf"
      geotown.login true, (resp) ->
        $rootScope.$broadcast('user:login') if(!resp.code)
      , () ->
        $rootScope.loggedIn = false
    )
)