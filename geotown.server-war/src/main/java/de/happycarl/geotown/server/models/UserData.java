package de.happycarl.geotown.server.models;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import de.happycarl.geotown.server.util.Deref;

@Entity
@Cache
public class UserData {
	@Id
	String email;

	List<Ref<Route>> routes = new ArrayList<Ref<Route>>();

	public UserData(User user) {
		this.email = user.getEmail();
	}

	private UserData() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Route> getRoutes() {
		return Deref.deref(routes);
	}

    public void addRoute(Route route) {
        this.routes.add(Ref.create(route));
    }

	public boolean equals(UserData that) {
		return this.getEmail().equals(that.getEmail());
	}
}
