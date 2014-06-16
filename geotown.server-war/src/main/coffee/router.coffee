@geotownApp.config(['$stateProvider', ($stateProvider) ->
  routes = {
    name: 'routes'
    url: '/routes'
    templateUrl: 'routes.html'
    controller: 'RoutesController'
  };
  routesDetail =  {
    name: 'routes.detail'
    templateUrl: 'routes.detail.html'
    controller: 'RouteDetailController'
    url: '/:id'
    parent: routes
  }
  $stateProvider.state(routes).state(routesDetail)
])