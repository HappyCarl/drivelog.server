package de.happycarl.geotown.server.models;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserData {
	@Id
	String email;

    List<Ref<Route>> routes = new ArrayList<Ref<Route>>();

	public UserData(User user) {
		this.email = user.getEmail();
	}

    private UserData() {}
}
