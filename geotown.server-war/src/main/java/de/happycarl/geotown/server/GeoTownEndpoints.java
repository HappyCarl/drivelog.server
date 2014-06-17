package de.happycarl.geotown.server;

import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import de.happycarl.geotown.server.models.Route;
import de.happycarl.geotown.server.models.UserData;
import de.happycarl.geotown.server.models.Waypoint;

@Api(name = "geotown", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
		Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
		Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE })
public class GeoTownEndpoints {

	public UserData getCurrentUserData(User user) throws UnauthorizedException {
		return getOrCreateUserData(user);
	}

	public List<Route> getMyRoutes(User user) throws UnauthorizedException {
		UserData userData = getOrCreateUserData(user);

		return userData.getRoutes();
	}

	public Route createRoute(@Named("name") String name,
			@Named("latitude") double latitude,
			@Named("longitude") double longitude, User user)
			throws UnauthorizedException {
		UserData userData = getOrCreateUserData(user);

		Route route = new Route(userData, name);

		route.setLatitude(latitude);
		route.setLongitude(longitude);

		OfyService.ofy().save().entities(route).now();
        userData.addRoute(route);
        OfyService.ofy().save().entities(userData).now();

        return route;
	}

    public Route getRoute(@Named("routeId") Long routeId, User user) {
        return OfyService.ofy().load().type(Route.class).id(routeId)
                .safe();
    }

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
