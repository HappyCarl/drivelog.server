window.init = ->
  window.timeoutD = setTimeout(window.init, 500)

@initStuff = () ->
  window.init()

@geotownApp = angular.module('geotownApp', ['ui.router', 'cfp.loadingBar', 'google-maps', 'ui.bootstrap', 'angularFileUpload']);