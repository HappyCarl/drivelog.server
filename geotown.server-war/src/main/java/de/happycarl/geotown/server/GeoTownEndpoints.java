package de.happycarl.geotown.server;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.LocationCapableRepositorySearch;
import com.beoui.geocell.model.Point;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.users.User;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import de.happycarl.geotown.server.models.Route;
import de.happycarl.geotown.server.models.Track;
import de.happycarl.geotown.server.models.UserData;
import de.happycarl.geotown.server.models.Waypoint;
import de.happycarl.geotown.server.util.OfyEntityLocationCapableRepositorySearchImpl;
import org.joda.time.DateTime;

import java.util.List;

@Api(name = "geotown", version = "v1", scopes = {Constants.EMAIL_SCOPE}, clientIds = {
        Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
        Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID}, audiences = {Constants.ANDROID_AUDIENCE})
public class GeoTownEndpoints {

    @ApiMethod(name = "userdata.get", path = "userdata")
    public UserData getCurrentUserData(User user) throws UnauthorizedException {
        return getOrCreateUserData(user);
    }

    @ApiMethod(name = "userdata.setUsername", path = "userdata.username")
    public UserData setUsername(@Named("username") String username, User user) throws UnauthorizedException {
        UserData userData = getOrCreateUserData(user);

        if (username.length() > 0)
            userData.setUsername(username);

        OfyService.ofy().save().entity(userData).now();

        return userData;
    }

    @ApiMethod(name = "routes.listMine", path = "routes")
    public List<Route> listMyRoutes(User user) throws UnauthorizedException {
        UserData userData = getOrCreateUserData(user);

        List<Route> routes = OfyService.ofy().load().type(Route.class).filter("owner", userData).list();
        if (routes == null) return Lists.newArrayList();
        return routes;
    }

    @ApiMethod(name = "routes.listNear", path = "routes.near")
    public List<Route> listNearRoutes(@Named("latitude") double latitude, @Named("longitude") double longitude, @Named("radius") double radius) {
        LocationCapableRepositorySearch<Route> ofySearch = new OfyEntityLocationCapableRepositorySearchImpl(OfyService.ofy());

        List<Route> routes = GeocellManager.proximityFetch(new Point(latitude, longitude), 20, radius, ofySearch);
        if (routes == null) return Lists.newArrayList();
        return routes;
    }

    @ApiMethod(name = "routes.insert", path = "routes")
    public Route createRoute(@Named("name") String name,
                             @Named("latitude") double latitude,
                             @Named("longitude") double longitude, User user)
            throws UnauthorizedException {
        UserData userData = getOrCreateUserData(user);

        Route route = new Route(userData, name);

        route.setLatitude(latitude);
        route.setLongitude(longitude);
        route.setGeocells(GeocellManager.generateGeoCell(new Point(latitude, longitude)));

        OfyService.ofy().save().entities(route).now();

        return route;
    }

    @ApiMethod(name = "routes.get", path = "routes.get")
    public Route getRoute(@Named("routeId") Long routeId, User user) throws NotFoundException {
        Route route = OfyService.ofy().load().type(Route.class).id(routeId)
                .now();
        if (route == null) throw new NotFoundException("The Route could not be found.");
        return route;
    }

    @ApiMethod(name = "routes.delete", path = "routes")
    public void deleteRoute(@Named("routeId") Long routeId, User user) throws ForbiddenException, UnauthorizedException, NotFoundException {
        UserData userData = getOrCreateUserData(user);
        Route route = OfyService.ofy().load().type(Route.class).id(routeId).now();

        if (route == null) throw new NotFoundException("The Route could not be found.");

        if (!route.getOwner().equals(userData))
            throw new ForbiddenException(
                    "You're not allowed to delete this Route.");

        OfyService.ofy().delete().entity(route).now();
    }

    @ApiMethod(name = "waypoints.list", path = "waypoints")
    public List<Waypoint> listWaypoints(@Named("routeId") Long routeId, User user) {
        List<Waypoint> waypoints = OfyService.ofy().load().type(Waypoint.class).filter("route", Key.create(Route.class, routeId)).list();

        if (waypoints == null) return Lists.newArrayList();
        return waypoints;
    }

    @ApiMethod(name = "waypoints.insert", path = "waypoints")
    public Route createWaypoint(@Named("routeId") Long routeId,
                                @Named("latitude") double latitude, @Named("longitude") double longitude,
                                @Named("question") String question,
                                @Named("wrongAnswers") List<String> wrongAnswers,
                                @Named("rightAnswer") String rightAnswer,
                                @Named("blobstoreImageKey") String blobstoreKey, User user)
            throws UnauthorizedException, ForbiddenException, NotFoundException {
        UserData userData = getOrCreateUserData(user);

        Route route = OfyService.ofy().load().type(Route.class).id(routeId)
                .now();

        if (route == null)
            throw new NotFoundException("The Route could not be found.");

        if (!route.getOwner().equals(userData))
            throw new ForbiddenException(
                    "You're not allowed to edit this route.");

        Waypoint w = new Waypoint(route, latitude, longitude);
        w.setQuestion(question);
        w.getWrongAnswers().addAll(wrongAnswers);
        w.setRightAnswer(rightAnswer);
        w.setBlobstoreImageKey(blobstoreKey);

        OfyService.ofy().save().entities(w).now();

        return route;
    }

    @ApiMethod(name = "waypoints.delete", path = "waypoints")
    public void deleteWaypoint(@Named("waypointId") Long waypointId, User user) throws ForbiddenException, UnauthorizedException, NotFoundException {
        UserData userData = getOrCreateUserData(user);
        Waypoint waypoint = OfyService.ofy().load().type(Waypoint.class).id(waypointId).now();

        if (waypoint == null) throw new NotFoundException("The Waypoint could not be found.");

        if (!waypoint.getRoute().getOwner().equals(userData))
            throw new ForbiddenException(
                    "You're not allowed to delete this Waypoint.");

        OfyService.ofy().delete().entity(waypoint).now();
    }

    @ApiMethod(name = "app.getImageBlobstoreUrl", path="app")
    public GetBlobstoreImageUploadUrlResponse getImageBlobstoreUrl(User user) throws UnauthorizedException {
        if(user == null) throw new UnauthorizedException("Authorization required");

        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

        GetBlobstoreImageUploadUrlResponse r = new GetBlobstoreImageUploadUrlResponse();
        r.uploadUrl = blobstoreService.createUploadUrl("/uploadImage");

        return r;
    }

    @ApiMethod(name = "tracks.startTrack", path = "tracks")
    public Track startTrack(@Named("routeId") Long routeId, User user) throws UnauthorizedException, NotFoundException {
        UserData userData = getOrCreateUserData(user);
        Route route = OfyService.ofy().load().type(Route.class).id(routeId).now();

        if (route == null) throw new NotFoundException("The Route could not be found.");

        Track track = new Track(userData, route);

        OfyService.ofy().save().entities(track).now();

        return track;
    }

    @ApiMethod(name = "tracks.finishTrack", path = "tracks", httpMethod = "PUT")
    public Track finishTrack(@Named("trackId") Long trackId, @Named("imageKey") String imageKey, User user) throws UnauthorizedException, NotFoundException, ForbiddenException {
        UserData userData = getOrCreateUserData(user);
        Track track = OfyService.ofy().load().type(Track.class).id(trackId).now();

        if(track == null) throw new NotFoundException("The Track could not be found.");

        if(!track.getOwner().equals(userData)) throw new ForbiddenException("You're not allowed to edit this Track!");

        track.setFinishTime(new DateTime());
        track.setBlobstoreTrackKey(imageKey);

        OfyService.ofy().save().entities(track).now();

        return track;
    }

    @ApiMethod(name = "tracks.getTrackGPXUploadURL", path = "tracks.uploadGpx")
    public GetBlobstoreTrackUploadUrlResponse getTrackUploadURL(User user) throws UnauthorizedException {
        UserData userData = getOrCreateUserData(user);

        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

        GetBlobstoreTrackUploadUrlResponse r = new GetBlobstoreTrackUploadUrlResponse();
        r.uploadUrl = blobstoreService.createUploadUrl("/uploadTrack");

        return r;
    }

    @ApiMethod(name = "tracks.getAllTracks", path = "tracks.all")
    public List<Track> getAllTracks(User user) throws UnauthorizedException {
        UserData userData = getOrCreateUserData(user);

        List<Track> tracks = OfyService.ofy().load().type(Track.class).list();

        return tracks;
    }

    private static UserData getOrCreateUserData(User user)
            throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }

        UserData data = OfyService.ofy().load().type(UserData.class)
                .id(user.getEmail()).now();

        if (data == null) {
            data = new UserData(user);
            OfyService.ofy().save().entity(data).now();
        }

        return data;
    }
}
