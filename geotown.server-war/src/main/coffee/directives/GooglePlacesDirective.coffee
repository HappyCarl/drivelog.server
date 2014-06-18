@geotownApp.directive('googlePlaces', ->
   {
    restrict:'E',
    replace:true,
    scope: {location:'=', type:'@type'},
    template: '<input id="google_places_ac" name="google_places_ac" type="text" class="input-block-level"/>',
    link: ($scope) ->
      options = {}
      type = $scope.type.split(" ")[0]
      if((not type?) and type != "")
        options = {types: type, region: "EU"}
      autocomplete = new google.maps.places.Autocomplete($("#google_places_ac")[0], options)
      google.maps.event.addListener(autocomplete, 'place_changed', () ->
        place = autocomplete.getPlace()
        $scope.location.latitude = place.geometry.location.lat()
        $scope.location.longitude = place.geometry.location.lng()
        $scope.$apply()
    )
  }
)