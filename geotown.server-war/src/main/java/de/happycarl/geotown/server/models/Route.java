package de.happycarl.geotown.server.models;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import de.happycarl.geotown.server.util.Deref;

/**
 * Created by jhbruhn on 12.06.14.
 */
@Entity
@Cache
public class Route {
	@Id
	Long id;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
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

	public long getId() {
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

    @ApiResourceProperty(name = "waypoints")
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

    public void addWaypoint(Waypoint wP) {
        this.waypoints.add(Ref.create(wP));
    }

	public boolean equals(Route that) {
		return this.getId()== (that.getId());
	}

}
