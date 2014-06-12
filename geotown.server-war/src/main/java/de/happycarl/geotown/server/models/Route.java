package de.happycarl.geotown.server.models;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhbruhn on 12.06.14.
 */
@Entity
public class Route {
    @Id
    Long id;

    @Parent
    Ref<UserData> owner;

    String name;

    List<Ref<Waypoint>> waypoints = new ArrayList<Ref<Waypoint>>();

    public Route(UserData owner, String name) {
        this.owner = Ref.create(owner);
        this.name = name;
    }

    private Route() {}

}
