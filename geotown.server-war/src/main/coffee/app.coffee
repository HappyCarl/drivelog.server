@initStuff = () ->
  window.init()

@geotownApp = angular.module('geotownApp', ['ui.router', 'cfp.loadingBar', 'google-maps', 'ui.bootstrap']);