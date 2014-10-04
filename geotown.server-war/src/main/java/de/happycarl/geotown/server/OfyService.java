package de.happycarl.geotown.server;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.opt.joda.JodaTimeTranslators;
import de.happycarl.geotown.server.models.Route;
import de.happycarl.geotown.server.models.UserData;
import de.happycarl.geotown.server.models.Waypoint;
import de.happycarl.geotown.server.models.Track;

public class OfyService {
    static {
        JodaTimeTranslators.add(ObjectifyService.factory());
        factory().register(UserData.class);
        factory().register(Route.class);
        factory().register(Waypoint.class);
        factory().register(Track.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
