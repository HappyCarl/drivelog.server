package de.happycarl.geotown.server.models;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

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

    public Ref<UserData> getOwner() {
        return owner;
    }

    public void setOwner(Ref<UserData> owner) {
        this.owner = owner;
    }

    public Ref<Route> getRoute() {
        return route;
    }

    public void setRoute(Ref<Route> route) {
        this.route = route;
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
}
