package de.happycarl.geotown.server;

import com.google.api.server.spi.config.Api;
import com.google.appengine.api.users.User;

import de.happycarl.geotown.server.models.UserData;

@Api(name = "geotown", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
		Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
		Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE })
public class GeoTownEndpoints {

	public UserData getCurrentUserData(User user) {
		return getOrCreateUserData(user);
	}

	private static UserData getOrCreateUserData(User user) {
		UserData data = OfyService.ofy().load().type(UserData.class).id(user.getEmail())
				.now();

		if (data == null) {
			data = new UserData(user);
		}
		OfyService.ofy().save().entity(data).now();

		return data;
	}
}
