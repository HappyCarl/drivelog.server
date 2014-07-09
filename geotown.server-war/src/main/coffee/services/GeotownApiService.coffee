@geotownApp.factory('geotown', ($q, $rootScope, cfpLoadingBar) ->
  {
    init: (cb) ->
      apisToLoad;
      loadCallback = () ->
        cb() if (--apisToLoad == 0)

      apisToLoad = 2

      host = window.location.host
      if host.indexOf("localhost") > -1
        host = "//" + host
      else if host.indexOf("beta") > -1
        host = "https://beta-dot-drive-log.appspot.com"
      else
        host = "https://drive-log.appspot.com"

      gapi.client.load('geotown', 'v1', loadCallback, host + '/_ah/api')
      gapi.client.load('oauth2', 'v2', loadCallback)

    login: (mode) ->
      cfpLoadingBar.start()
      deferred = $q.defer()

      gapi.auth.authorize({
          client_id: window.CLIENT_ID,
          scope: "email",
          immediate: mode,
          response_type : 'token id_token'
        }, () ->
          gapi.client.oauth2.userinfo.get().execute((resp) ->

            cfpLoadingBar.complete()

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
      cfpLoadingBar.start()
      deferred = $q.defer()

      gapi.client.geotown.routes.listMine().execute (resp) ->

        cfpLoadingBar.complete()

        if resp.code?
          $rootScope.$apply ->
            deferred.reject resp
        else
          $rootScope.$apply ->
            deferred.resolve resp.items

      deferred.promise

    createRoute: (route) ->
      cfpLoadingBar.start()
      deferred = $q.defer()

      gapi.client.geotown.routes.insert(route).execute (resp) ->

        cfpLoadingBar.complete()

        if resp.code?
          $rootScope.$apply ->
            deferred.reject resp
        else
          $rootScope.$apply ->
            deferred.resolve resp

      deferred.promise

    getRoute: (id) ->
      cfpLoadingBar.start()
      deferred = $q.defer()

      gapi.client.geotown.routes.get({routeId: parseInt(id)}).execute (resp) ->

        cfpLoadingBar.complete()

        if resp.code?
          $rootScope.$apply ->
            deferred.reject resp
        else
          $rootScope.$apply ->
            deferred.resolve resp

      deferred.promise

    deleteRoute: (id) ->
      cfpLoadingBar.start()
      deferred = $q.defer()

      gapi.client.geotown.routes.delete({routeId: parseInt(id)}).execute (resp) ->

        cfpLoadingBar.complete()
        $rootScope.$apply ->
          deferred.resolve resp

      deferred.promise

    createWaypoint: (waypoint) ->
      cfpLoadingBar.start()
      deferred = $q.defer()

      gapi.client.geotown.waypoints.insert(waypoint).execute (resp) ->

        cfpLoadingBar.complete()

        if resp.code?
          $rootScope.$apply ->
            deferred.reject resp
        else
          $rootScope.$apply ->
            deferred.resolve resp

      deferred.promise

    listWaypoints: (routeId) ->
      cfpLoadingBar.start()
      deferred = $q.defer()

      gapi.client.geotown.waypoints.list({routeId: routeId}).execute (resp) ->

        cfpLoadingBar.complete()

        if resp.code?
          $rootScope.$apply ->
            deferred.reject resp
        else
          $rootScope.$apply ->
            deferred.resolve resp.items

      deferred.promise

    deleteWaypoint: (id) ->
      cfpLoadingBar.start()
      deferred = $q.defer()

      gapi.client.geotown.waypoints.delete({waypointId: parseInt(id)}).execute (resp) ->

        cfpLoadingBar.complete()
        $rootScope.$apply ->
          deferred.resolve resp

      deferred.promise

    getUploadUrl: ->
      cfpLoadingBar.start()
      deferred = $q.defer()

      gapi.client.geotown.app.getBlobstoreUrl().execute (resp) ->

        cfpLoadingBar.complete()
        $rootScope.$apply ->
          deferred.resolve resp

      deferred.promise
  }
)