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

    login: (cb) ->
      gapi.auth.authorize({client_id: @CLIENT_ID, scope: ["https://www.googleapis.com/auth/userinfo.email"], immediate: false}, cb)
  }
)