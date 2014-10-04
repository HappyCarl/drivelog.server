package de.happycarl.geotown.server.models;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

/**
 * Created by Jan-Henrik on 28.09.2014.
 */
@Entity
@Cache
@Index
public class Track {
    @Id
    Long id;

    Ref<UserData> owner;
    Ref<Route> route;

    DateTime creationTime;
    DateTime finishTime;

    String blobstoreTrackKey;

    public Track() {}

    public Track(UserData owner, Route route) {
        this.owner = Ref.create(owner);
        this.route = Ref.create(route);
        creationTime = new DateTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserData getOwner() {
        return owner.get();
    }

    public void setOwner(UserData owner) {
        this.owner = Ref.create(owner);
    }

    public Route getRoute() {
        return route.get();
    }

    public void setRoute(Route route) {
        this.route = Ref.create(route);
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }

    public DateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(DateTime finishTime) {
        this.finishTime = finishTime;
    }

    public String getBlobstoreTrackKey() {
        return blobstoreTrackKey;
    }

    public void setBlobstoreTrackKey(String blobstoreTrackKey) {
        this.blobstoreTrackKey = blobstoreTrackKey;
    }

    public String getGPXUrl() {
        if(blobstoreTrackKey == null || blobstoreTrackKey.isEmpty()) return "";

        String url;

        if (StringUtils.equals("Production", System.getProperty("com.google.appengine.runtime.environment"))) {
            String applicationId = System.getProperty("com.google.appengine.application.id");
            String version = System.getProperty("com.google.appengine.application.version");
            url = "http://"+version+"."+applicationId+".appspot.com";
        } else {
            url = "http://localhost:8888";
        }

        url += "/serveTrackGPX?key=" + blobstoreTrackKey;

        return url;
    }
}
