package de.happycarl.geotown.server.models;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import de.happycarl.geotown.server.util.Deref;

/**
 * Created by jhbruhn on 12.06.14.
 */
@Entity
@Cache
public class Route {
	@Id
	Long id;

	@Parent
	Ref<UserData> owner;

	String name;

	double latitude;
	double longitude;

	List<Ref<Waypoint>> waypoints = new ArrayList<Ref<Waypoint>>();

	public Route(UserData owner, String name) {
		this.owner = Ref.create(owner);
		this.name = name;
	}

	private Route() {
	}

	public Long getId() {
		return id;
	}

	public UserData getOwner() {
		return Deref.deref(owner);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Waypoint> getWaypoints() {
		return Deref.deref(waypoints);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean equals(Route that) {
		return this.getId().equals(that.getId());
	}

}
