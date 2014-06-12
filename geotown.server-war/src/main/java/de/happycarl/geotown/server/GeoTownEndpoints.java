package de.happycarl.geotown.server;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import com.googlecode.objectify.Ref;
import de.happycarl.geotown.server.models.Route;
import de.happycarl.geotown.server.models.UserData;

import java.util.ArrayList;
import java.util.List;

@Api(name = "geotown", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
		Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
		Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE })
public class GeoTownEndpoints {

	public UserData getCurrentUserData(User user) throws UnauthorizedException {
		return getOrCreateUserData(user);
	}

    public List<Route> getCurrentUsersRoutes(User user) throws UnauthorizedException {
        UserData userData = getOrCreateUserData(user);

        return userData.getRoutes();
    }

	private static UserData getOrCreateUserData(User user) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }

		UserData data = OfyService.ofy().load().type(UserData.class).id(user.getEmail())
				.now();

		if (data == null) {
			data = new UserData(user);
            OfyService.ofy().save().entity(data).now();
		}

		return data;
	}
}
