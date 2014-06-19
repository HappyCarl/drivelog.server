@initStuff = () ->
  window.init()

@geotownApp = angular.module('geotownApp', ['ui.router', 'cgBusy', 'google-maps', 'ui.bootstrap']);