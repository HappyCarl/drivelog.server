@geotownApp.factory('geotown', () ->
  {
    init: (cb) ->
      apisToLoad;
      loadCallback = () ->
        cb() if (--apisToLoad == 0)

      apisToLoad = 2
      apiRoot = '//' + window.location.host + '/_ah/api'
      gapi.client.load('geotown', 'v1', loadCallback, apiRoot)
      gapi.client.load('oauth2', 'v2', loadCallback)

    login: (mode, cb) ->
      gapi.auth.authorize({
          client_id: window.CLIENT_ID,
          scope: "email",
          immediate: mode,
          response_type : 'token id_token'
        }, () ->
          gapi.client.oauth2.userinfo.get().execute((resp) ->
            if(!resp.code)
              token = gapi.auth.getToken()
              token.access_token = token.id_token;
              gapi.auth.setToken(token);
            cb(resp)
          )
      )

    getMyRoutes: (cb) ->
      gapi.client.geotown.geoTownEndpoints.getMyRoutes().execute (resp) ->
        cb resp.items

    createRoute: (route, cb) ->
      gapi.client.geotown.geoTownEndpoints.createRoute(route).execute (resp) ->
        cb resp

    getRoute: (id, cb) ->
      gapi.client.geotown.geoTownEndpoints.getRoute({routeId: parseInt(id)}).execute (resp) ->
        cb resp
  }
)