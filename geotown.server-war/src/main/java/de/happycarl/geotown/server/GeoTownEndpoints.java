package de.happycarl.geotown.server;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.LocationCapableRepositorySearch;
import com.beoui.geocell.model.Point;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyService;
import de.happycarl.geotown.server.models.Route;
import de.happycarl.geotown.server.models.UserData;
import de.happycarl.geotown.server.models.Waypoint;
import de.happycarl.geotown.server.util.OfyEntityLocationCapableRepositorySearchImpl;

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

        return OfyService.ofy().load().type(Route.class).filter("owner", userData).list();
    }

    @ApiMethod(name = "routes.listNear", path = "routes.near")
    public List<Route> listNearRoutes(@Named("latitude") double latitude, @Named("longitude") double longitude, @Named("radius") double radius) {
        LocationCapableRepositorySearch<Route> ofySearch = new OfyEntityLocationCapableRepositorySearchImpl(OfyService.ofy());

        return GeocellManager.proximityFetch(new Point(latitude, longitude), 20, radius, ofySearch);
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
        userData.addRoute(route);
        OfyService.ofy().save().entities(userData).now();

        return route;
    }

    @ApiMethod(name = "routes.get", path = "routes.get")
    public Route getRoute(@Named("routeId") Long routeId, User user) {
        return OfyService.ofy().load().type(Route.class).id(routeId)
                .safe();
    }

    @ApiMethod(name = "routes.delete", path = "routes")
    public void deleteRoute(@Named("routeId") Long routeId, User user) throws ForbiddenException, UnauthorizedException {
        UserData userData = getOrCreateUserData(user);
        Route route = OfyService.ofy().load().type(Route.class).id(routeId).safe();

        if (!route.getOwner().equals(userData))
            throw new ForbiddenException(
                    "You're not allowed to delete this Route.");

        userData.removeRoute(route);

        OfyService.ofy().delete().entity(route).now();
        OfyService.ofy().save().entities(userData).now();
    }

    @ApiMethod(name = "waypoints.list", path = "waypoints")
    public List<Waypoint> listWaypoints(@Named("routeId") Long routeId, User user) {
        Route r = OfyService.ofy().load().type(Route.class).id(routeId).safe();

        return OfyService.ofy().load().type(Waypoint.class).filter("route", r).list();
    }

    @ApiMethod(name = "waypoints.insert", path = "waypoints")
    public Route createWaypoint(@Named("routeId") Long routeId,
                                @Named("latitude") double latitude, @Named("longitude") double longitude,
                                @Named("question") String question,
                                @Named("answers") List<String> answers, User user)
            throws UnauthorizedException, ForbiddenException {
        UserData userData = getOrCreateUserData(user);

        Route route = OfyService.ofy().load().type(Route.class).id(routeId)
                .safe();

        if (!route.getOwner().equals(userData))
            throw new ForbiddenException(
                    "You're not allowed to edit this route.");

        Waypoint w = new Waypoint(route, latitude, longitude);
        w.setQuestion(question);
        w.getAnswers().addAll(answers);

        OfyService.ofy().save().entities(w).now();

        route.addWaypoint(w);

        OfyService.ofy().save().entities(route).now();

        return route;
    }

    @ApiMethod(name = "waypoints.delete", path = "waypoints")
    public void deleteWaypoint(@Named("waypointId") Long waypointId, User user) throws ForbiddenException, UnauthorizedException {
        UserData userData = getOrCreateUserData(user);
        Waypoint waypoint = OfyService.ofy().load().type(Waypoint.class).id(waypointId).safe();

        if (!waypoint.getRoute().getOwner().equals(userData))
            throw new ForbiddenException(
                    "You're not allowed to delete this Waypoint.");

        OfyService.ofy().delete().entity(waypoint).now();
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
