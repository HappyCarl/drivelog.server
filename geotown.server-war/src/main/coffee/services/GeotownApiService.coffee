@geotownApp.factory('geotown', ($q, $rootScope) ->
  {
    init: (cb) ->
      apisToLoad;
      loadCallback = () ->
        cb() if (--apisToLoad == 0)

      apisToLoad = 2
      apiRoot = '//' + window.location.host + '/_ah/api'
      gapi.client.load('geotown', 'v1', loadCallback, apiRoot)
      gapi.client.load('oauth2', 'v2', loadCallback)

    login: (mode) ->
      deferred = $q.defer()

      gapi.auth.authorize({
          client_id: window.CLIENT_ID,
          scope: "email",
          immediate: mode,
          response_type : 'token id_token'
        }, () ->
          gapi.client.oauth2.userinfo.get().execute((resp) ->
            if !resp.code?
              token = gapi.auth.getToken()
              token.access_token = token.id_token
              gapi.auth.setToken(token)
              $rootScope.$apply ->
                deferred.resolve resp
            else
              $rootScope.$apply ->
                deferred.reject resp
          )
      )

      deferred.promise

    getMyRoutes: () ->
      deferred = $q.defer()

      gapi.client.geotown.geoTownEndpoints.getMyRoutes().execute (resp) ->
        if resp.code?
          $rootScope.$apply ->
            deferred.reject resp
        else
          $rootScope.$apply ->
            deferred.resolve resp.items

      deferred.promise

    createRoute: (route) ->
      deferred = $q.defer()

      gapi.client.geotown.geoTownEndpoints.createRoute(route).execute (resp) ->
        if resp.code?
          $rootScope.$apply ->
            deferred.reject resp
        else
          $rootScope.$apply ->
            deferred.resolve resp

      deferred.promise

    getRoute: (id) ->
      deferred = $q.defer()

      gapi.client.geotown.geoTownEndpoints.getRoute({routeId: parseInt(id)}).execute (resp) ->
        if resp.code?
          $rootScope.$apply ->
            deferred.reject resp
        else
          $rootScope.$apply ->
            deferred.resolve resp

      deferred.promise
  }
)