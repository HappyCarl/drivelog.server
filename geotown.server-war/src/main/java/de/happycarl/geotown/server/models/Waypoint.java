package de.happycarl.geotown.server.models;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhbruhn on 12.06.14.
 */
@Entity
public class Waypoint {
    @Parent
    Ref<Route> route;

    double latitude;
    double longitude;

    String question;
    List<String> answers = new ArrayList<String>();

    public Waypoint(Route route, double latitude, double longitude) {
        this.route = Ref.create(route);
    }

    private Waypoint() {}
}
