@geotownApp.config(['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) ->
  routes = {
    name: 'routes'
    url: '/routes'
    templateUrl: 'routes.html'
    controller: 'RoutesController'
  }
  routesDetail =  {
    name: 'routes.detail'
    templateUrl: 'routes.detail.html'
    controller: 'RouteDetailController'
    url: '/:id'
    parent: routes
  }
  $stateProvider.state(routes).state(routesDetail)

  $urlRouterProvider.when('', '/routes')
])