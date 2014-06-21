@initStuff = () ->
  window.init()

@geotownApp = angular.module('geotownApp', ['ui.router', 'cgBusy', 'angular-loading-bar', 'google-maps', 'ui.bootstrap']);