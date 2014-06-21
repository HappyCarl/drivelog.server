@initStuff = () ->
  window.init()

@geotownApp = angular.module('geotownApp', ['ui.router', 'cgBusy', 'cfp.loadingBar', 'google-maps', 'ui.bootstrap']);