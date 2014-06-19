package de.happycarl.geotown.server.models;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import de.happycarl.geotown.server.util.Deref;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhbruhn on 12.06.14.
 */
@Entity
@Cache
@Index
public class Route {
    @Id
    Long id;

    Ref<UserData> owner;

    String name;

    double latitude;
    double longitude;

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
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
        return this.getId() == (that.getId());
    }

}
