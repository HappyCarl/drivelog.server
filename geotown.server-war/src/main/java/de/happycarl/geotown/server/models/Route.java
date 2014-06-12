package de.happycarl.geotown.server.models;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

/**
 * Created by jhbruhn on 12.06.14.
 */
public class Route {
    @Id
    Long id;

    @Parent
    Ref<UserData> owner;

    String name;

    public Route(UserData owner, String name) {
        this.owner = Ref.create(owner);
        this.name = name;
    }

    private Route() {}

}
