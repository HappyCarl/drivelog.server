package de.happycarl.geotown.server.models;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserData {
	@Id
	String email;

	public UserData(User user) {
		this.email = user.getEmail();
	}
}
