package de.happycarl.geotown.server.util;

import com.beoui.geocell.LocationCapableRepositorySearch;
import com.googlecode.objectify.Objectify;
import de.happycarl.geotown.server.models.Route;

import java.util.List;

/**
 * Created by jhbruhn on 20.06.14.
 */
public class OfyEntityLocationCapableRepositorySearchImpl implements
        LocationCapableRepositorySearch<Route> {

    private Objectify ofy;

    public OfyEntityLocationCapableRepositorySearchImpl(Objectify ofy) {
        this.ofy = ofy;
    }

    @Override
    public List<Route> search(List<String> geocells) {
        return ofy.load().type(Route.class)
                .filter("geocells in ", geocells).list();
    }
}
