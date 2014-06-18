package de.happycarl.geotown.server.util;

import com.googlecode.objectify.Ref;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhbruhn on 12.06.14.
 */
public class Deref {


    public static <T> T deref(Ref<T> ref) {
        return ref == null ? null : ref.get();
    }

    public static <T> List<T> deref(List<Ref<T>> reflist) {
        List<T> n = new ArrayList<T>(reflist.size());
        for(Ref<T> ref : reflist)
            n.add(deref(ref));
        return n;
    }
}